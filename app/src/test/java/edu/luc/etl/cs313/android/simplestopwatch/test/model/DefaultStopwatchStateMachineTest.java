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
        Context c = null; //this fixes some of the testing.
        // A context is passed in the main program to play sound
        setModel(new DefaultStopwatchStateMachine(getDependency(), getDependency(), c));
    }

    @After
    public void tearDown() {
        setModel(null);
        super.tearDown();
    }


    @Test
    public void testMaximumPress() {//failing
        assertEquals(0, getDependency().getRuntime());

        // Click 99 times to add exactly 99 seconds
        for (int i = 0; i < 99; i++) {
            getModel().onStartStop();
        }
        assertEquals(99, getDependency().getRuntime());
        getModel().onTick(); // 99 → running state

        getModel().onTick(); //99 → 98 first tick of timer
        assertEquals(98, getDependency().getRuntime());

        // Second countdown tick
        getModel().onTick(); // 98 → 97
        assertEquals(97, getDependency().getRuntime());
    }

    @Test
    public void testTimerForcedReset() {//failing
        // Ensure initial runtime is zero
        assertEquals(0, getDependency().getRuntime());

        // Start the timer
        getModel().onStartStop(); //+1
        assertEquals(1, getDependency().getRuntime());
        // TODO: Simulate 3-second delay and transition to countdown when implemented

        // Simulate tick (currently increments)
        getModel().onTick();
        getModel().onTick();
        getModel().onTick();


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

        assertEquals(0, getDependency().getRuntime());


        getModel().onStartStop();
        assertEquals(1, getDependency().getRuntime());

        getModel().onTick();
        getModel().onTick();
        getModel().onTick();

        getModel().onTick();
        assertEquals(0, getDependency().getRuntime());

        //  Simulate pressing the button to stop the alarm
        getModel().onStartStop();

        // After stopping alarm, runtime should remain 0
        assertEquals(0, getDependency().getRuntime());

        //  Stopwatch should be fully stopped
        assertFalse(getDependency().isStarted());
        //alarm off
        assertFalse(getDependency().isAlarmOn());



        // Simulate user pressing the button once to start the timer
        //getModel().onStartStop();

        // Simulate a 3-second wait
        // TODO: When countdown is implemented, we'll have to simulate the transition to countdown state here

        // Simulate a tick that would decrement time
        // TODO: In countdown mode, this would reduce runtime from a set value toward 0
        //getModel().onTick(); // No effect in current implementation
       // getModel().onTick();
       // getModel().onTick();
        // Simulate the alarm sounding after hitting 0
        // TODO: When alarm functionality is added, we'll have to assert that the alarm is active
       // getModel().onTick();

        // Simulate pressing the button to stop the alarm
       // getModel().onStartStop();

        //  Assert current runtime remains 0 and stopwatch is no longer active
       // assertEquals(0, getDependency().getRuntime());
       // assertFalse(getDependency().isStarted());

        // TODO: Once alarm is implemented, we'll add:
        // assertFalse(getDependency().isAlarmOn());
    }

    @Test
    public void testTimeAdd() {
        // runningTime = 0
        assertTimeEquals(0);
        // start timer via button click
        //getModel().onStartStop();//uncomment if the first time increment doesnt increase the timer. else its passing.
        // simulate one second
        getModel().onTick();
        // stop timer via button click
        getModel().onStartStop();
        // verify runningTime is 1
        assertTimeEquals(1);
    }

    @Test
    public void testDecrement() {
        // setting initial time to 5 seconds
        for (int i = 0; i < 6; i++) {
            getModel().onStartStop();
        }
        assertTimeEquals(6);

        onTickRepeat(3);
        getModel().onTick();
        // verify time decremented by 1
        assertTimeEquals(5);
    }

    @Test
    public void testMaximumTimer() {
        // increment to 99
        for (int i = 0; i < 100; i++) {
            // start timer
            getModel().onStartStop();
        }
        // verify that maximum time is reached
        assertEquals(99, getDependency().getTime());

        // attempt to increment past 99
        // again, start timer
        getModel().onStartStop();
        // verify runningTime is still 99
        assertTimeEquals(99);
    }

    @Test
    public void testInitialTimer(){
        //David: make sure timer starts at 00
        assertEquals(0, getDependency().getTime());

    }

    @Test
    public void testNoAlarmOnLoad(){
        //David: No alarm on load
        assertEquals(0, getDependency().getRuntime());
        assertFalse(getDependency().isStarted());
        assertFalse(getDependency().isAlarmOn()); //uncomment when alarm is implemented. prob change method name


    }

    @Test
    public void testAlarm(){
        assertTimeEquals(0);
        // Set initial time to 1 second (simulate 1 press and tick)
        getModel().onStartStop();


        // Verify initial runtime
        assertTimeEquals(1);

        // Begin countdown
        getModel().onTick();
        getModel().onTick();
        getModel().onTick();
        getModel().onTick();
        // Alarm should stop
         assertFalse(getDependency().isAlarmOn());
    }

    @Test
    public void testConsistentTime() {
        // Add two seconds
        getModel().onStartStop();
        getModel().onStartStop();

        // Ensure time = 2
        assertEquals(2, getDependency().getTime());
        assertEquals(2, getDependency().getRuntime());

        // Start countdown
        getModel().onTick();
        getModel().onTick();
        getModel().onTick();

        getModel().onTick();

        // Time should now be 1 and both runtime/display should match
        assertEquals(1, getDependency().getTime());
        assertEquals(1, getDependency().getRuntime());
    }

}
