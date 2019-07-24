import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class useCondition {
    Lock lock = new ReentrantLock();
    Condition codition = lock.newCondition();

    public void conditionWait() throws InterruptedException{
        lock.lock();
        try {
            System.out.println("awaiting");
            codition.await();
            System.out.println("await return");
        }finally {
            lock.unlock();
        }
    }

    public void conditionSignal() throws InterruptedException{
        lock.lock();
        try {
            System.out.println("signaling");
            codition.signal();
            System.out.println("sinaled");
        }finally {
            lock.unlock();
        }
    }

    public static void main(String[] args)throws Exception {
        useCondition useCondition = new useCondition();
        useCondition.conditionWait();
        useCondition.conditionSignal();

        //ArrayBlockingQueue
    }
}



