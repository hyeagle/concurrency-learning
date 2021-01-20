package future;

import java.util.Random;
import java.util.concurrent.*;

public class OneFuture {
    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(10);
        Future<Integer> future = service.submit(new CallableTask());
        try {
            System.out.println(future.isDone()); // done 可能是成功结束，也可能是异常退出
            System.out.println(future.get());
            System.out.println(future.isDone());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        service.shutdown();

    }

}

class CallableTask implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        Thread.sleep(3000);
        return new Random().nextInt();
    }
}