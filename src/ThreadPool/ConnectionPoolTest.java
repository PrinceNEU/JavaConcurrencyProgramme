package ThreadPool;

import java.sql.Connection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ConnectionPoolTest {

    static ConnectPool pool = new ConnectPool(10);
    static CountDownLatch start = new CountDownLatch(1);
    static CountDownLatch end;

    //ReentrantReadWriteLock

    public static void main(String[] args) throws Exception{

        int threadCount = 100;
        end = new CountDownLatch(threadCount);
        int count = 20;
        AtomicInteger got = new AtomicInteger();
        AtomicInteger notgot = new AtomicInteger();

        for(int i=0;i<threadCount;i++){
            Thread thread = new Thread(new ConnectionRunner(count,got,notgot),"ConnectionRunnerThread");
            thread.start();
        }

        start.countDown();
        end.await();
        System.out.println("total invoke: "+(threadCount*count));
        System.out.println("get connection: "+got);
        System.out.println("not get connection: "+notgot);
    }


    static class ConnectionRunner implements Runnable{
        int count ;
        AtomicInteger got;
        AtomicInteger notgot;

        public ConnectionRunner(int count, AtomicInteger got, AtomicInteger notgot) {
            this.count = count;
            this.got = got;
            this.notgot = notgot;
        }

        @Override
        public void run() {
            try {
                start.await();
            }catch (Exception e){

            }

            while (count>0){
                try{
                    Connection connection = pool.fetchConnection(1000);
                    if(connection!=null){
                        try{
                            connection.createStatement();
                            connection.commit();
                        }finally {
                            pool.releaseConnection(connection);
                            got.incrementAndGet();
                        }
                    }
                    else {
                        notgot.incrementAndGet();
                    }
                }catch (Exception e){

                }finally {
                    count--;
                }
            }
            end.countDown();
        }
    }
}
