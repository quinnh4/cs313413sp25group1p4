package edu.luc.etl.cs313.android.simplestopwatch.model.time;

import static edu.luc.etl.cs313.android.simplestopwatch.common.Constants.*;

/**
 * An implementation of the stopwatch data model.
 */
public class DefaultTimeModel implements TimeModel {

    private int runningTime = 0;

    private int lapTime = -1;

    @Override
    public void resetRuntime() {
        runningTime = 0;
    }

    @Override
    public void incRuntime() {
        // capping off the runtime at 99 so it does not exceed
        runningTime = Math.min(runningTime + SEC_PER_TICK, 99);
        //this logic can be passed to the boundedcontainer from click counter.
    }

    @Override
    public void decRuntime(){
        runningTime = Math.max(runningTime - SEC_PER_TICK, 0);
    }

    @Override
    public int getRuntime() {
        return runningTime;
    }

    @Override
    public void setLaptime() {
        lapTime = runningTime;
    }

    @Override
    public int getLaptime() {
        return lapTime;
    }

    @Override
    public void setRuntime(int runtime) {
        runningTime = runtime;
    }
}