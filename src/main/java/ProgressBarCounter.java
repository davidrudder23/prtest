import java.util.concurrent.locks.ReentrantLock;

public class ProgressBarCounter {

    private volatile int counter=0;
    private final ReentrantLock reentrantlock = new ReentrantLock(true);

    public void incrementCounter(){
        reentrantlock.lock();
        try {
            counter+=1;
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
