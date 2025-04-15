package edu.luc.etl.cs313.android.simplestopwatch.model.state;

import edu.luc.etl.cs313.android.simplestopwatch.R;
import edu.luc.etl.cs313.android.simplestopwatch.model.container.BoundedContainer;
import edu.luc.etl.cs313.android.simplestopwatch.model.container.DefaultTimerContainer;

public class IncrementingState implements StopwatchState{
    private final StopwatchSMStateView sm;
    private int waitTime = 3;
    private BoundedContainer incrementingContainer;
    public IncrementingState(final StopwatchSMStateView sm) {
        this.sm = sm;
        this.incrementingContainer = new DefaultTimerContainer(0);
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
        waitTime = 3;
    }


    @Override
    public void onTick() {
        waitTime--;
        if(waitTime<=0){
            sm.toRunningState();
        }
        else{sm.toIncrementingState();}
    }
}
