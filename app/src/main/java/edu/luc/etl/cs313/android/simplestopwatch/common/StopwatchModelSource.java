package edu.luc.etl.cs313.android.simplestopwatch.common;

/**
 * A source of UI update events for the stopwatch.
 * This interface is typically implemented by the model.
 *
 * @author laufer
 */
public interface StopwatchModelSource {
    void setModelListener(StopwatchModelListener listener);
}
