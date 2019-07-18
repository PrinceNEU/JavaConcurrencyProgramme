package ThreadPool;

import java.sql.Connection;
import java.util.LinkedList;

public class ConnectPool {

    private LinkedList<Connection> pool = new LinkedList<Connection>();

    public ConnectPool(int initialSize){
        if(initialSize>0){
            for(int i=0;i<initialSize;i++){
                pool.addLast(ConnectionDriver.createConnection());
            }
        }
    }

    public void releaseConnection(Connection connection){
        if(connection!=null){
            synchronized (pool){
                pool.addLast(connection);
                pool.notifyAll();
            }
        }
    }

    public Connection fetchConnection(long millis) throws InterruptedException{
        synchronized (pool){

            if(millis<=0){
                while (pool.isEmpty()){
                    pool.wait();
                }
                return pool.removeFirst();
            }
            else {
                long future = System.currentTimeMillis()+millis;
                long remaining = millis;

                while (pool.isEmpty()&&remaining>0){
                    pool.wait(remaining);
                    remaining = future-System.currentTimeMillis();
                }

                Connection result = null;

                if(!pool.isEmpty()){
                    result = pool.removeFirst();
                }

                return result;

            }
        }
    }

}
