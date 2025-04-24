package edu.luc.etl.cs313.android.simplestopwatch.model.state;

import edu.luc.etl.cs313.android.simplestopwatch.R;
import edu.luc.etl.cs313.android.simplestopwatch.model.container.BoundedContainer;
import edu.luc.etl.cs313.android.simplestopwatch.model.container.DefaultTimerContainer;

public class IncrementingState implements StopwatchState {
    private final StopwatchSMStateView sm;

    public IncrementingState(final StopwatchSMStateView sm) {
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
        // resets the counter in state machine
        sm.resetWaitCounter();
    }

    @Override
    public void onTick() {
    }
}
