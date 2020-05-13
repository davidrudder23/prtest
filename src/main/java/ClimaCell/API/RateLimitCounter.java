package ClimaCell.API;

import java.util.concurrent.locks.ReentrantLock;

/*
This is a thread safe counter. It will be utilized to ensure that the program
does not go over the rate limit set by Clima Cell free accounts.
 */
public class RateLimitCounter {

    /*
    I need to ensure that this value is always the most current for
    every thread that accesses it. Every thread will first attempt to update it then read it.
     */
    private volatile int counter=0;
    /*
    ReentrantLock will ensure that no thread is resource starved. It keeps a
    fifo queue of all threads attempting to access it.
     */
    private final ReentrantLock reentrantlock = new ReentrantLock(true);

    public void incrementCounter(){
        reentrantlock.lock();
        try {
            counter+=1;
        }finally {
            reentrantlock.unlock();
        }
    }

    public RateLimitCounter() {
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
}
