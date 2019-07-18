import Locks.SleepUtils;

public class ThreadState {

    static class TimeWaiting implements Runnable{
        public void run(){
            while (true){
                SleepUtils.second(100);
            }
        }
    }

    static class Waiting implements Runnable{
        public void run(){
            while (true){
                synchronized (Waiting.class){
                    try {
                        Waiting.class.wait();
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    static class Blocked implements Runnable{
        public void run(){
            synchronized (Blocked.class){
                while (true){
                    SleepUtils.second(100);
                }
            }
        }
    }

    public static void main(String[] args) {
        new Thread(new TimeWaiting(),"timeWaitingThread").start();
        new Thread(new Waiting(),"waitingThread").start();
        new Thread(new Blocked(),"blocdedThread-1").start();
        new Thread(new Blocked(),"blocdedThread-2").start();
    }

}
