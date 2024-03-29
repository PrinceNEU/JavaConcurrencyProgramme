import Locks.SleepUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class WaitNotify {

    static boolean flag = true;
    static Object lock = new Object();

    public static void main(String[] args) throws Exception{
        Thread waitThread  = new Thread(new Wait(),"waitThread");
        waitThread.start();
        TimeUnit.SECONDS.sleep(1);
        Thread notifyThread = new Thread(new Notify(),"notifyThread");
        notifyThread.start();
    }

    static class Wait implements Runnable{
        public void run(){

            synchronized (lock){

                while (flag){
                    try{
                        System.out.println(Thread.currentThread()+" flag is true. Wait at "+new SimpleDateFormat("HH:mm:ss").format(new Date()));
                        lock.wait();
                    }catch (InterruptedException e){

                    }
                }

                System.out.println(Thread.currentThread()+" flag is false. Running at "+new SimpleDateFormat("HH:mm:ss").format(new Date()));
            }

        }

    }

    static class Notify implements Runnable{
        public void run(){

            synchronized ((lock)){

                System.out.println(Thread.currentThread()+"hold lock. Notify at "+new SimpleDateFormat("HH:mm:ss").format(new Date()));
                lock.notifyAll();;
                flag = false;
                SleepUtils.second(5);

            }

            synchronized ((lock)){

                System.out.println(Thread.currentThread()+"hold lock again. Notify at "+new SimpleDateFormat("HH:mm:ss").format(new Date()));
                lock.notifyAll();;
                flag = false;
                SleepUtils.second(5);

            }

        }
    }
}
