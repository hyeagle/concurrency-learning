package threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FixedThreadPool {
    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 100; i++) {
            service.execute(new MyThread());
        }
        System.out.println(Thread.currentThread());
    }
}

class MyThread implements Runnable {

    @Override
    public void run() {
        System.out.println("thread name = " + Thread.currentThread());
    }
}
