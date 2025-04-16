package edu.luc.etl.cs313.android.simplestopwatch.model.state;

import edu.luc.etl.cs313.android.simplestopwatch.R;
import edu.luc.etl.cs313.android.simplestopwatch.model.container.BoundedContainer;
import edu.luc.etl.cs313.android.simplestopwatch.model.container.DefaultTimerContainer;

public class IncrementingState implements StopwatchState{
    private final StopwatchSMStateView sm;
    private int waitTime = 3000;

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
        waitTime = 3000;
    }


    @Override
    public void onTick() {
        waitTime-=1000;
        if(waitTime<=0500){
            sm.toRunningState();
        }
        else{sm.toIncrementingState();}
    }
}
