package edu.luc.etl.cs313.android.simplestopwatch.test.model;

import android.content.Context;
import org.junit.After;
import org.junit.Before;

import edu.luc.etl.cs313.android.simplestopwatch.model.state.DefaultStopwatchStateMachine;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

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
        Context c = null;
        setModel(new DefaultStopwatchStateMachine(getDependency(), getDependency(), c));
    }

    @After
    public void tearDown() {
        setModel(null);
        super.tearDown();
    }


    @Test
    public void testMaximumPress() {
        assertEquals(0, getDependency().getRuntime());

        //  Increment to 99
        for (int i = 0; i < 99; i++) {
            getModel().onStartStop();  // Start
            getModel().onTick();       // +1 sec
            getModel().onStartStop();  // Stop
        }

        assertEquals(99, getDependency().getRuntime());

        // Trigger countdown
        getModel().onStartStop(); // Stop -> StoppedState
        getModel().onStartStop(); // StoppedState -> CountdownState and clock starts

        // Tick in countdown
        getModel().onTick(); // now should decrement
        assertEquals(98, getDependency().getRuntime());

        getModel().onTick();
        assertEquals(97, getDependency().getRuntime());
    }

    @Test
    public void testTimerForcedReset() {
        // Ensure initial runtime is zero
        assertEquals(0, getDependency().getRuntime());

        // Start the timer
        getModel().onStartStop();

        // TODO: Simulate 3-second delay and transition to countdown when implemented

        // Simulate tick (currently increments)
        getModel().onTick();

        // Stop the timer mid-countdown
        getModel().onStartStop();

        // Reset the timer
        //getModel().onLapReset();  removed since there is no lapreset button in the final timer.

        // Verifies timer is cleared and not running
        assertEquals(0, getDependency().getRuntime());
        assertFalse(getDependency().isStarted());

        // TODO: Add assertions for countdown state and forced reset behavior when available
    }


    @Test
    public void testAlarmStop() {
        // Initial runtime should be zero
        assertEquals(0, getDependency().getRuntime());

        // Simulate user pressing the button once to start the timer
        getModel().onStartStop();

        // Simulate a 3-second wait
        // TODO: When countdown is implemented, we'll have to simulate the transition to countdown state here

        // Simulate a tick that would decrement time
        // TODO: In countdown mode, this would reduce runtime from a set value toward 0
        getModel().onTick(); // No effect in current implementation

        // Simulate the alarm sounding after hitting 0
        // TODO: When alarm functionality is added, we'll have to assert that the alarm is active

        // Simulate pressing the button to stop the alarm
        getModel().onStartStop();

        //  Assert current runtime remains 0 and stopwatch is no longer active
        assertEquals(0, getDependency().getRuntime());
        assertFalse(getDependency().isStarted());

        // TODO: Once alarm is implemented, we'll add:
        // assertFalse(getDependency().isAlarmOn());
    }

    @Test
    public void testTimeAdd() {
        // runningTime = 0
        assertTimeEquals(0);
        // start timer via button click
        getModel().onStartStop();
        // simulate one second
        getModel().onTick();
        // stop timer via button click
        getModel().onStartStop();
        // verify runningTime is 1
        assertTimeEquals(1);
    }

    @Test
    public void testDecrement() {
        // setting this method in place until we add new methods for decrementing
        // setting initial time to 5 seconds
        for (int i = 0; i < 5; i++) {
            getModel().onStartStop();
            getModel().onTick();
            getModel().onStartStop();
        }
        assertTimeEquals(5);

        // need to add methods to actually decrement
        getModel().onStartStop();
        getModel().onStartStop(); //To countdown
        // decrement by 1
        getModel().onTick();
        // verify time decremented by 1
        assertTimeEquals(4);
    }

    @Test
    public void testMaximumTimer() {
        // increment to 99
        for (int i = 0; i < 99; i++) {
            // start timer
            getModel().onStartStop();
            // adding seconds
            getModel().onTick();
            // stop timer
            getModel().onStartStop();
        }
        // verify that maximum time is reached
        assertEquals(99, getDependency().getTime());

        // attempt to increment past 99
        // again, start timer
        getModel().onStartStop();
        // attempt to increment
        getModel().onTick();
        // stop timer
        getModel().onStartStop();
        // verify runningTime is still 99
        assertTimeEquals(99);
        // could maybe do boolean to verify it cannot exceed 99?
        //assertTrue("Exceeds 99 seconds", getDependency().getTime() <= 99);
    }

    @Test
    public void testInitiialTimer(){
        //David: make sure timer starts at 00
        assertEquals(0, getDependency().getTime());

    }

    @Test
    public void testNoAlarmOnLoad(){
        //David: No alarm on load
        assertEquals(0, getDependency().getRuntime());
        assertFalse(getDependency().isStarted());

        //assertFalse(getDependency().isAlaarmOn()); //uncomment when alarm is implemented. prob change method name


    }

    @Test
    public void testAlarm(){
        // Set initial time to 1 second (simulate 1 press and tick)
        getModel().onStartStop();
        getModel().onTick();
        getModel().onStartStop();

        // Verify initial runtime
        assertTimeEquals(1);

        // Begin countdown
        getModel().onStartStop();
        getModel().onStartStop();
        getModel().onTick();

        // Simulate stop
        getModel().onStartStop();

        // Alarm should stop
        // assertFalse(getDependency().isAlarmOn());
    }

    @Test
    public void testConsistentTime() {
        // Add two seconds
        for (int i = 0; i < 2; i++){
            getModel().onStartStop();
            getModel().onTick();
            getModel().onStartStop();
        }

        // Ensure time = 2
        assertEquals(2, getDependency().getTime());
        assertEquals(2, getDependency().getRuntime());

        // Start countdown
        getModel().onStartStop();
        getModel().onStartStop();
        getModel().onTick();

        // Time should now be 1 and both runtime/display should match
        assertEquals(1, getDependency().getTime());
        assertEquals(1, getDependency().getRuntime());
    }

}
