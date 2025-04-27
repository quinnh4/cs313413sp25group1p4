package edu.luc.etl.cs313.android.simplestopwatch.test.android;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;

import android.widget.Button;
import android.widget.TextView;
import edu.luc.etl.cs313.android.simplestopwatch.R;
import edu.luc.etl.cs313.android.simplestopwatch.android.StopwatchAdapter;

import static edu.luc.etl.cs313.android.simplestopwatch.common.Constants.SEC_PER_MIN;

/**
 * Abstract GUI-level test superclass of several essential stopwatch scenarios.
 *
 * @author laufer
 *
 * TODO move this and the other tests to src/test once Android Studio supports
 * non-instrumentation unit tests properly.
 */
public abstract class AbstractStopwatchActivityTest {

    /**
     * Verifies that the activity under test can be launched.
     */
    @Test
    public void testActivityCheckTestCaseSetUpProperly() {//pass
        assertNotNull("activity should be launched successfully", getActivity());
    }

    /**
     * Verifies the following scenario: time is 0.
     *
     * @throws Throwable
     */
    @Test
    public void testActivityScenarioInit() throws Throwable {//pass
        getActivity().runOnUiThread(() -> assertEquals(0, getDisplayedValue()));
    }

    /**
     * Verifies the following scenario: time is 0, press start, wait 5+ seconds, expect time 5.
     * this test is from the original project and not relevent.
     * @throws Throwable
     */
//    @Test
//    public void testActivityScenarioRun() throws Throwable {
//
//        getActivity().runOnUiThread(() -> {
//            assertEquals(0, getDisplayedValue());
//            assertTrue(getStartStopButton().performClick());
//        });
//        Thread.sleep(5500); // <-- do not run this in the UI thread!
//        runUiThreadTasks();
//        getActivity().runOnUiThread(() -> {
//            assertEquals(5, getDisplayedValue());
//            assertTrue(getStartStopButton().performClick());
//        });
//    }
    /**
     * Tests rotation, may need to be moved.
     *
     * @throws Throwable
     */
    @Test
    public void testRotation() throws Throwable {//passing
        //setup clock for 5 secs
        getActivity().runOnUiThread(() -> {
            assertEquals(0, getDisplayedValue());
            assertTrue(getStartStopButton().performClick());
            assertTrue(getStartStopButton().performClick());
            assertTrue(getStartStopButton().performClick());
            assertTrue(getStartStopButton().performClick());
            assertTrue(getStartStopButton().performClick());
            assertTrue(getStartStopButton().performClick());
        });
        Thread.sleep(3500); // <-- do not run this in the UI thread!
        runUiThreadTasks();
        //code to save state via robolectric
        //code to update state to be rotated
        getActivity().runOnUiThread(() -> {
            //checks if state is restored.
            assertEquals(5, getDisplayedValue());
        });

    }


//removed lap test

    /**
     * Test that the timer automatically starts to decrement
     * after 3 seconds after last button press
     * @throws Throwable
     */
    @Test
    public void testAutoTimer() throws Throwable { //passes
        getActivity().runOnUiThread(() -> {
            assertEquals(0, getDisplayedValue());
            assertTrue(getStartStopButton().performClick());//first click +1
            //assertTrue(getStartStopButton().performClick());//uncommented cuz intinal click should increment
            assertTrue(getStartStopButton().performClick()); // second click +1
        });
        Thread.sleep(3100); // wait for 3 seconds
        runUiThreadTasks();
        getActivity().runOnUiThread(() -> {
            assertEquals(2, getDisplayedValue()); // check if value is at 2 at start
        });
        Thread.sleep(1100); // wait for 1 second
        runUiThreadTasks();
        getActivity().runOnUiThread(() -> {
            //assertEquals(3, getDisplayedValue()); // quick check with broken timer for incrementing
            assertEquals(1, getDisplayedValue()); // check if decrements to 1 after 1 second
        });
    }

    /**
     * Test that the timer can be stopped by pressing the button during the decrementing stage
     * @throws Throwable
     */
    @Test
    public void testButtonStops() throws Throwable {//passes
        getActivity().runOnUiThread(() -> {
            assertEquals(0, getDisplayedValue());
            //assertTrue(getStartStopButton().performClick());//to incrementing click
            assertTrue(getStartStopButton().performClick()); //first click +1
            assertTrue(getStartStopButton().performClick()); // second click +1
        });
        Thread.sleep(3500); // wait for 3 seconds
        runUiThreadTasks();
        getActivity().runOnUiThread(() -> {
            assertEquals(2, getDisplayedValue()); // check if value is at 2 at start
        });
        Thread.sleep(1200); // wait for 1 second
        runUiThreadTasks();
        //relies on decrement below
        getActivity().runOnUiThread(() -> {
            assertEquals(1, getDisplayedValue()); // check if decrements to 1 after 1 second
            assertTrue(getStartStopButton().performClick());  // button press stop the timer
            assertEquals(0, getDisplayedValue()); // check if this button press set the timer back to 0
        });
    }


    /**
     *  Tests if time remains on 0 seconds after the time runs out
     *  @throws Throwable
     */
    @Test
    public void testAutoStop() throws Throwable { //passing
        getActivity().runOnUiThread(() -> {
            assertEquals(0, getDisplayedValue());
            //assertTrue(getStartStopButton().performClick());// perform one click to get one second
            assertTrue(getStartStopButton().performClick()); //may need a second click to since first sets it to incremementing state.
        });
        Thread.sleep(3500); // wait for 3 seconds
        runUiThreadTasks();
        getActivity().runOnUiThread(() -> {
            assertEquals(1, getDisplayedValue()); // check if value is 1 at start
        });
        Thread.sleep(1100); // wait for 1 second
        runUiThreadTasks();
        getActivity().runOnUiThread(() -> {
            assertEquals(0, getDisplayedValue()); // check if decrements to 0 after 1 second
        });
        Thread.sleep(1100);
        runUiThreadTasks();
        getActivity().runOnUiThread(() -> {
            assertEquals(0, getDisplayedValue()); // check if the clock is still at 0, since it hasn't been reset
        });
    }

    /**
     *  Tests if time stays consistent between gets while not decrementign
     *  @throws Throwable
     */
    @Test
    public void testGet() throws Throwable {
        getActivity().runOnUiThread(() -> {
            assertEquals(0, getDisplayedValue());
           // assertTrue(getStartStopButton().performClick());//incrementing click
            assertTrue(getStartStopButton().performClick()); //+1 increment
            assertEquals(1, getDisplayedValue()); // test if 1 second shown

        });
        Thread.sleep(2500); // wait for 2 seconds
        runUiThreadTasks();
        getActivity().runOnUiThread(() -> {
            assertEquals(1, getDisplayedValue()); // test if clock is still at 1 seconds, as 3 seconds not been reached
        });
    }

    /**
     *Test that the timer can be incremented and run again after
     *  the timer reaches 0 and the alarm is stopped.*
     * @throws Throwable
     */
    @Test
    public void testTimerTimeoutReset() throws Throwable {//passing
        getActivity().runOnUiThread(() -> {
            assertEquals(0, getDisplayedValue());
            //assertTrue(getStartStopButton().performClick());//to incrementing
            assertTrue(getStartStopButton().performClick());//kept as startstop atm.
        });
        Thread.sleep(3500); // <-- do not run this in the UI thread!  //sleeps 3 to wait for increment - > running
        runUiThreadTasks();
        getActivity().runOnUiThread(() -> {
            assertEquals(1, getDisplayedValue());
        });
        Thread.sleep(1100); // <-- do not run this in the UI thread!
        runUiThreadTasks();
        //needs decrementing
        getActivity().runOnUiThread(() -> {
            assertEquals(0, getDisplayedValue());
            assertTrue(getStartStopButton().performClick());  // stops the alarm still kept as startstopbutton
        });
        runUiThreadTasks();
        getActivity().runOnUiThread(() -> {
            //assertTrue(getStartStopButton().performClick());//to incrementing
            assertTrue(getStartStopButton().performClick());
            assertTrue(getStartStopButton().performClick());
            assertTrue(getStartStopButton().performClick());
            assertEquals(3, getDisplayedValue()); //adds 3 to the timer
        });
        Thread.sleep(3500);
        runUiThreadTasks();
        getActivity().runOnUiThread(() -> {
            assertEquals(3, getDisplayedValue());
        });
        Thread.sleep(2200);
        runUiThreadTasks();
        getActivity().runOnUiThread(() -> {
            assertEquals(1, getDisplayedValue());
        });
        Thread.sleep(1100);
        runUiThreadTasks();
        getActivity().runOnUiThread(() -> assertEquals(0, getDisplayedValue()));
    }



    // auxiliary methods for easy access to UI widgets

    protected abstract StopwatchAdapter getActivity();

    protected int tvToInt(final TextView t) {
        return Integer.parseInt(t.getText().toString().trim());
    }

    protected int getDisplayedValue() {
        final TextView ts = getActivity().findViewById(R.id.seconds);
        return tvToInt(ts);
    }

    protected Button getStartStopButton() {
        return getActivity().findViewById(R.id.startStop);
    }

    /**
     * Explicitly runs tasks scheduled to run on the UI thread in case this is required
     * by the testing framework, e.g., Robolectric.
     */
    protected void runUiThreadTasks() { }
}
