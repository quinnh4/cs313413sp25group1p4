package edu.luc.etl.cs313.android.simplestopwatch.model.state;

import edu.luc.etl.cs313.android.simplestopwatch.common.StopwatchModelListener;
import edu.luc.etl.cs313.android.simplestopwatch.model.clock.ClockModel;
import edu.luc.etl.cs313.android.simplestopwatch.model.container.BoundedContainer;
import edu.luc.etl.cs313.android.simplestopwatch.model.container.DefaultTimerContainer;
import edu.luc.etl.cs313.android.simplestopwatch.model.soundManager;
import edu.luc.etl.cs313.android.simplestopwatch.model.time.TimeModel;
import android.content.Context;

/**
 * An implementation of the state machine for the stopwatch.
 *
 * @author laufer
 */
public class DefaultStopwatchStateMachine implements StopwatchStateMachine {

    public DefaultStopwatchStateMachine(final TimeModel timeModel, final ClockModel clockModel, Context context) {
        this.timeModel = timeModel;
        this.clockModel = clockModel;
        this.incrementingContainer = new DefaultTimerContainer(0);
        this.alarmSound = new soundManager(context);
    }

    private int waitTime = 0;
    private final TimeModel timeModel;
    //manages sound
    private soundManager alarmSound;
    private final ClockModel clockModel;
    private BoundedContainer incrementingContainer;


    /**
     * The internal state of this adapter component. Required for the State pattern.
     */
    private StopwatchState state;

    protected void setState(final StopwatchState state) {
        this.state = state;
        listener.onStateUpdate(state.getId());
    }

    private StopwatchModelListener listener;

    @Override
    public void setModelListener(final StopwatchModelListener listener) {
        this.listener = listener;
    }

    // forward event uiUpdateListener methods to the current state
    // these must be synchronized because events can come from the
    // UI thread or the timer thread
    @Override public synchronized void onStartStop() {state.onStartStop();}

    @Override public synchronized void onTick() {state.onTick();}
    @Override public void updateUIRuntime() { listener.onTimeUpdate(timeModel.getRuntime()); }


    // known states
    private final StopwatchState STOPPED     = new StoppedState(this);
    private final StopwatchState RUNNING     = new RunningState(this);
    private final StopwatchState INCREMENTING = new IncrementingState(this);
    private final StopwatchState ALARMING = new AlarmingState(this);


    // transitions
    @Override public void toRunningState()    {
        setState(RUNNING); }
    @Override public void toStoppedState()    {
        setState(STOPPED);

    }
    @Override
    public void toIncrementingState() {
        setState(INCREMENTING);
        // initialize to 3 seconds
        waitTime = 3;
        timeModel.resetRuntime();
        actionUpdateView();
    }
    @Override
    public void resetWaitTime() {
        // resets waitTime when button pressed in incrementing state
        waitTime = 3;
    }
    //methods to manage incrementing waitTime
    @Override
    public void tickWaitTime(){--waitTime;}
    @Override
    public int getWaitTime(){return waitTime;}
    @Override public void toAlarmingState() { setState(ALARMING); }


    // actions
    @Override public void actionInit() { toStoppedState(); actionReset(); }
    @Override
    public void actionReset() {
        timeModel.resetRuntime();
        // resetting the increment container so when the timer is reset from running state
        // via button click it starts from 0
        incrementingContainer = new DefaultTimerContainer(0);
        actionUpdateView();
    }
    @Override public void actionStart() { clockModel.start(); }
    @Override
    public void actionDec() {
        timeModel.decRuntime();
        actionUpdateView();
    }
    @Override
    public void playAlarm(){
        alarmSound.play();
    }
    public boolean isAlarmOn() {
        return alarmSound.isAlarmOn();
    }


    @Override
    public int getTime() {
        return timeModel.getRuntime();
    }
    @Override
    public boolean isContainerFull(){return incrementingContainer.isFull();}
    @Override public void actionStop() { clockModel.stop(); }

    @Override public void actionInc() {
        //should interact with the boundedcontainer from click counter.
        incrementingContainer.increment();
        timeModel.setRuntime(incrementingContainer.get());
        actionUpdateView(); }
    @Override public void actionUpdateView() { state.updateView(); }
}
