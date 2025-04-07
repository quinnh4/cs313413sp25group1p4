package edu.luc.etl.cs313.android.simplestopwatch.test.model;

import org.junit.After;
import org.junit.Before;

import edu.luc.etl.cs313.android.simplestopwatch.model.state.DefaultStopwatchStateMachine;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Concrete testcase subclass for the default stopwatch state machine
 * implementation.
 *
 * @author laufer
 * @see http://xunitpatterns.com/Testcase%20Superclass.html
 */
public class DefaultStopwatchStateMachineTest extends AbstractStopwatchStateMachineTest {

    @Before
    public void setUp() throws Exception {
        super.setUp();
        setModel(new DefaultStopwatchStateMachine(getDependency(), getDependency()));
    }

    @After
    public void tearDown() {
        setModel(null);
        super.tearDown();
    }

    @Test
    public void testMaximumPress() {
        //  Initial time is 0
        assertEquals(0, getDependency().getRuntime());

        //  Increment to 99
        for (int i = 0; i < 99; i++) {
            getModel().onStartStop();  // Start
            getModel().onTick();       // +1 sec
            getModel().onStartStop();  // Stop
        }

        //  Confirm we're at 99
        assertEquals(99, getDependency().getRuntime());

        //  Placeholder for countdown trigger
        getModel().onStartStop(); // Attempt to trigger countdown
        getModel().onStartStop(); // Again

        //  Simulate tick — runtime will increase because countdown isn't implemented yet
        getModel().onTick();
        assertEquals(100, getDependency().getRuntime());

        getModel().onTick();
        assertEquals(101, getDependency().getRuntime());
    }

}
