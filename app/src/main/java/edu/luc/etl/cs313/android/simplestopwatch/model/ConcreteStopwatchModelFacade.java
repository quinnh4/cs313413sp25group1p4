package edu.luc.etl.cs313.android.simplestopwatch.model;

import edu.luc.etl.cs313.android.simplestopwatch.common.StopwatchModelListener;
import edu.luc.etl.cs313.android.simplestopwatch.model.clock.ClockModel;
import edu.luc.etl.cs313.android.simplestopwatch.model.clock.DefaultClockModel;
import edu.luc.etl.cs313.android.simplestopwatch.model.container.BoundedContainer;
import edu.luc.etl.cs313.android.simplestopwatch.model.container.DefaultTimerContainer;
import edu.luc.etl.cs313.android.simplestopwatch.model.state.DefaultStopwatchStateMachine;
import edu.luc.etl.cs313.android.simplestopwatch.model.state.StopwatchStateMachine;
import edu.luc.etl.cs313.android.simplestopwatch.model.time.DefaultTimeModel;
import edu.luc.etl.cs313.android.simplestopwatch.model.time.TimeModel;
import android.content.Context;


/**
 * An implementation of the model facade.
 *
 * @author laufer
 */
public class ConcreteStopwatchModelFacade implements StopwatchModelFacade {

    private final StopwatchStateMachine stateMachine;
    private final ClockModel clockModel;

    private final TimeModel timeModel;


    public ConcreteStopwatchModelFacade(Context context) {
        timeModel = new DefaultTimeModel();
        clockModel = new DefaultClockModel();
        stateMachine = new DefaultStopwatchStateMachine(timeModel, clockModel, context);
        clockModel.setTickListener(stateMachine);

    }

    @Override
    public void start() {
        stateMachine.actionInit();
    }

    @Override
    public void setModelListener(final StopwatchModelListener listener) {
        stateMachine.setModelListener(listener);
    }

    @Override
    public void onStartStop() {
        stateMachine.onStartStop();

    }

    //


}
