package edu.luc.etl.cs313.android.simplestopwatch.model.state;
import edu.luc.etl.cs313.android.simplestopwatch.R;


public class CountdownState implements StopwatchState {
    //doesn't RunningState essentially do this?
    private final StopwatchStateMachine sm;

    public CountdownState(final StopwatchStateMachine sm) {
        this.sm = sm;
    }

    @Override
    public void onStartStop() {
        sm.actionStop();
        sm.actionReset();
        sm.toStoppedState();
    }
//
//    @Override
//    public void onLapReset() {
//        sm.actionReset();
//        sm.toStoppedState();
//    }

    @Override
    public void onTick() {
        sm.actionDec();
        sm.updateUIRuntime();
        if (sm.getTime() == 0) {
            sm.toStoppedState();
        }
    }

    @Override
    public void updateView() {
        sm.updateUIRuntime();
    }

    @Override
    public int getId() {
        return R.string.COUNTDOWN;
    }
}
