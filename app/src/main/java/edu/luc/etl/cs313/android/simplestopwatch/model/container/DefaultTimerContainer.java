package edu.luc.etl.cs313.android.simplestopwatch.model.container;

public class DefaultTimerContainer implements BoundedContainer{
    private final int min;
    private final int max = 99;
    private int timeValue;

    public DefaultTimerContainer(){this(0);}

    public DefaultTimerContainer(int min) {
        if (min >= max){
            throw new IllegalArgumentException("min >=99!");
        }
        this.min = min;
        this.timeValue = this.min;
    }
    @Override
    public void increment() {
        //could do an and depending on state.
        if (!isFull()){timeValue++;}
    }

    @Override
    public void decrement() {
        //could do an and depending on state.
        if (!isEmpty()){timeValue--;}
    }

    @Override
    public int get() {
        return this.timeValue;
    }

    @Override
    public boolean isFull() {

        return this.timeValue>=max;
    }

    @Override
    public boolean isEmpty() {
        return this.timeValue==0;
    }
}
