package UI;

import java.util.concurrent.locks.ReentrantLock;

public class ProgressBarCounter {
    private static final int API_CALLS = 181;
    private volatile int counter=0;
    private final ReentrantLock reentrantlock = new ReentrantLock(true);
    private ReportUI reportUI = new ReportUI();

    public void incrementCounter(){
        reentrantlock.lock();
        try {
            reportUI.getProgressBar().setProgress((counter+=1)/API_CALLS);
        }finally {
            reentrantlock.unlock();
        }
    }

    public ProgressBarCounter() {
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
}
