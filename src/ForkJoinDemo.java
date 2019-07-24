import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

public class ForkJoinDemo extends RecursiveTask<Integer> {

    private static final int THRESHOLD = 2;
    private int start;
    private int end;

    public ForkJoinDemo(int start,int end){
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int sum = 0;

        boolean canCompute = (end-start) <= THRESHOLD;

        if(canCompute){
            for(int i=start; i<=end; i++ ){
                sum += i;
            }
        }
        else {
            int middle = (end + start) / 2;
            ForkJoinDemo leftTask = new ForkJoinDemo(start,middle);
            ForkJoinDemo rightTask = new ForkJoinDemo(middle+1,end);

            leftTask.fork();
            rightTask.fork();

            int leftResult = leftTask.join();
            int rightResult = rightTask.join();

            sum = leftResult + rightResult;
        }

        return sum;
    }

    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinDemo forkJoinDemo = new ForkJoinDemo(1,100);
        Future<Integer> result = forkJoinPool.submit(forkJoinDemo);

        try {
            System.out.println(result.get());
        }catch (Exception e){

        }
    }
}
