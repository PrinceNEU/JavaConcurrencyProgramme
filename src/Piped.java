import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedReader;
import java.io.PipedWriter;

public class Piped {

    public static void main(String[] args) throws Exception{

        PipedWriter out = new PipedWriter();
        PipedReader in = new PipedReader();

        out.connect(in);

        Thread thread = new Thread(new Print(in), "PrintThread");
        thread.start();

        int recieve = 0;
        try {
            while ((recieve=System.in.read())!=-1){
                out.write(recieve);
            }
        }finally {
            out.close();
        }

    }

    static class Print implements Runnable{

        private PipedReader in;

        public void run(){
            int recieve = 0;
            try {
                while ((recieve=in.read())!=-1){
                    System.out.println((char)recieve);
                }
            }catch (IOException e){

            }
        }

        public Print(PipedReader in){
            this.in = in;
        }
    }
}
