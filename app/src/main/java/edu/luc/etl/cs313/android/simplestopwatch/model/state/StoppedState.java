package edu.luc.etl.cs313.android.simplestopwatch.model.state;

import edu.luc.etl.cs313.android.simplestopwatch.R;

public class StoppedState implements StopwatchState {

    public StoppedState(final StopwatchStateMachine sm) {
        this.sm = sm;
    }

    private final StopwatchStateMachine sm;

    @Override
    public void onStartStop() {
        if (sm.getTime() > 99) {
            sm.toCountdownState();
            sm.actionStart();
        } else {
            sm.actionStart();
            sm.toRunningState();
        }
    }
//
//    @Override
//    public void onLapReset() {
//        sm.actionReset();
//        sm.toStoppedState();
//    }

    @Override
    public void onTick() {

    }

    @Override
    public void updateView() {
        sm.updateUIRuntime();
    }

    @Override
    public int getId() {
        return R.string.STOPPED;
    }
}
