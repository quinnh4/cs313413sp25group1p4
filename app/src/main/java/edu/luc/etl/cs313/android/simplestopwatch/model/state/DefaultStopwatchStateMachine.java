package edu.luc.etl.cs313.android.simplestopwatch.model.state;

import edu.luc.etl.cs313.android.simplestopwatch.common.StopwatchModelListener;
import edu.luc.etl.cs313.android.simplestopwatch.model.clock.ClockModel;
import edu.luc.etl.cs313.android.simplestopwatch.model.container.BoundedContainer;
import edu.luc.etl.cs313.android.simplestopwatch.model.container.DefaultTimerContainer;
import edu.luc.etl.cs313.android.simplestopwatch.model.time.TimeModel;
import edu.luc.etl.cs313.android.simplestopwatch.R;

/**
 * An implementation of the state machine for the stopwatch.
 *
 * @author laufer
 */
public class DefaultStopwatchStateMachine implements StopwatchStateMachine {

    public DefaultStopwatchStateMachine(final TimeModel timeModel, final ClockModel clockModel) {
        this.timeModel = timeModel;
        this.clockModel = clockModel;
        this.incrementingContainer = new DefaultTimerContainer(0);
    }

    private int waitCounter = 0;
    private final TimeModel timeModel;
    private final ClockModel clockModel;
    private BoundedContainer incrementingContainer;
    private final StopwatchState COUNTDOWN = new CountdownState(this);

    /**
     * The internal state of this adapter component. Required for the State pattern.
     */
    private StopwatchState state;

    protected void setState(final StopwatchState state) {
        this.state = state;
        listener.onStateUpdate(state.getId());
    }

    @Override public void toCountdownState() { setState(COUNTDOWN); }

    private StopwatchModelListener listener;

    @Override
    public void setModelListener(final StopwatchModelListener listener) {
        this.listener = listener;
    }

    // forward event uiUpdateListener methods to the current state
    // these must be synchronized because events can come from the
    // UI thread or the timer thread
    @Override public synchronized void onStartStop() { state.onStartStop(); }
    @Override
    public synchronized void onTick() {
        state.onTick();
        // handles wait counter for IncrementingState
        if (state.getId() == R.string.INCREMENTING) {
            if (--waitCounter <= 0) {
                toRunningState();
            }
        }
    }

    @Override public void updateUIRuntime() { listener.onTimeUpdate(timeModel.getRuntime()); }
    @Override public void updateUILaptime() { listener.onTimeUpdate(timeModel.getLaptime()); }//TODO may need removing.

    // known states
    private final StopwatchState STOPPED     = new StoppedState(this);
    private final StopwatchState RUNNING     = new RunningState(this);
    private final StopwatchState INCREMENTING = new IncrementingState(this);
    private final StopwatchState ALARMING = new AlarmingState(this);

    // transitions
    @Override public void toRunningState()    { setState(RUNNING); }
    @Override public void toStoppedState()    { setState(STOPPED); }
    @Override
    public void toIncrementingState() {
        setState(INCREMENTING);
        // intialize to 3 seconds
        waitCounter = 3;
    }
    @Override
    public void resetWaitCounter() {
        // reset counter when button pressed in incrementing state
        waitCounter = 3;
    }
    @Override public void toAlarmingState() { setState(ALARMING); }


    // actions
    @Override public void actionInit()       { toStoppedState(); actionReset(); }
    @Override public void actionReset()      { timeModel.resetRuntime(); actionUpdateView(); }
    @Override public void actionStart()      { clockModel.start(); }
    @Override
    public void actionDec() {
        timeModel.decRuntime();
        actionUpdateView();
    }

    @Override
    public int getTime() {
        return timeModel.getRuntime();
    }

    @Override public void actionStop()       { clockModel.stop(); }
//  @Override public void actionLap()        { timeModel.setLaptime(); }
    @Override public void actionInc()        {
        //should interact with the boundedcontainer from click counter.
        //timeModel.incRuntime();
        incrementingContainer.increment();
        timeModel.setRuntime(incrementingContainer.get());
        actionUpdateView(); }
    @Override public void actionUpdateView() { state.updateView(); }
}
