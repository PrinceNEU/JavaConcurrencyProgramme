package Locks;

import java.util.concurrent.locks.Lock;

public class TwinsLocktest {


    public static void main(String[] args) {
        final Lock lock = new TwinsLock();

        class Work extends Thread{
            Work(int i){
                this.setName("thread"+i);
            }
            public void run(){
                while (true){
                    lock.lock();
                    try {
                        SleepUtils.second(1);
                        System.out.println(Thread.currentThread().getName());
                        SleepUtils.second(1);

                    }finally {
                        lock.unlock();
                    }
                }
            }
        }

        for(int i=0;i<10;i++){
            Work w = new Work(i);
            w.setDaemon(true);
            w.start();
        }

        for(int i=0;i<10;i++){
            SleepUtils.second(1);
            System.out.println();
        }
    }

}
