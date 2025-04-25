package edu.luc.etl.cs313.android.simplestopwatch.model.state;

import edu.luc.etl.cs313.android.simplestopwatch.R;

public class AlarmingState implements StopwatchState{
    //state to handle alarm triggers.

    private final StopwatchSMStateView sm;
    public AlarmingState(final StopwatchSMStateView sm) {
        this.sm = sm;
    }
    @Override
    public void updateView() {
        sm.updateUIRuntime();
    }

    @Override
    public int getId() {
        return R.string.ALARMING;
    }

    @Override
    public void onStartStop() {
        sm.actionStop();
        sm.actionReset();
        sm.toStoppedState();
    }

    @Override
    public void onTick() {
        sm.playAlarm();//play alarm continuously
        sm.toAlarmingState();
    }
}
