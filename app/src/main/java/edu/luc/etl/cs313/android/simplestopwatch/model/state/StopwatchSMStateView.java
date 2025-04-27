package edu.luc.etl.cs313.android.simplestopwatch.model.state;

/**
 * The restricted view states have of their surrounding state machine.
 * This is a client-specific interface in Peter Coad's terminology.
 *
 * @author laufer
 */
interface StopwatchSMStateView {
    // transitions
    void toStoppedState();
    void toIncrementingState();
    void toAlarmingState();
    void toRunningState();
    // actions
    void actionInit();
    void actionReset();
    void actionStart();
    void playAlarm();
    void actionStop();
    void actionInc();
    void actionDec();
    void actionUpdateView();
    void tickWaitTime();
    //bookkeeping of incrementing
    boolean isContainerFull();
    int getWaitTime();
    int getTime();
    // state-dependent UI updates
    void updateUIRuntime();
    void resetWaitTime();
    //method for testing alarm
    boolean isAlarmOn();
}
