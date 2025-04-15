package edu.luc.etl.cs313.android.simplestopwatch.model.state;

public class IncrementingState implements StopwatchState{
    private final StopwatchSMStateView sm;
    public IncrementingState(final StopwatchSMStateView sm) {
        this.sm = sm;
    }
    @Override
    public void updateView() {

    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public void onStartStop() {

    }


    @Override
    public void onTick() {

    }
}
