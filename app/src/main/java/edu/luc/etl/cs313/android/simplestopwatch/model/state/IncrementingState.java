package edu.luc.etl.cs313.android.simplestopwatch.model.state;

import edu.luc.etl.cs313.android.simplestopwatch.R;

public class IncrementingState implements StopwatchState {
    private final StopwatchStateMachine sm;

    public IncrementingState(final StopwatchStateMachine sm) {
        this.sm = sm;
    }

    @Override
    public void updateView() {
        sm.updateUIRuntime();
    }

    @Override
    public int getId() {
        return R.string.INCREMENTING;
    }

    @Override
    public void onStartStop() {
        sm.actionInc();
        // resets the waitTime in state machine
        sm.resetWaitTime();
    }

    @Override
    public void onTick() {
        sm.tickWaitTime();
        if (sm.getWaitTime() <= 0 || sm.isContainerFull()) {
            sm.playAlarm();//play alarm when timer starts ticking
            sm.toRunningState();
        }
    }
}
