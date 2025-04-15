package edu.luc.etl.cs313.android.simplestopwatch.model.container;

public interface BoundedContainer {
    void increment();
    void decrement();
    int get();
    boolean isFull();
    boolean isEmpty();
}
