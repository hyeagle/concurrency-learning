package threadcollaboration;

import java.util.LinkedHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountDownLatchDemo2 {
    // 多个线程等待一个线程
    public static void main(String[] args) throws InterruptedException {
        System.out.println("last 5 second wait");
        CountDownLatch latch = new CountDownLatch(1);
        ExecutorService service = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            final int no = i + 1;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    System.out.println(no + " ready");
                    try {
                        latch.await();
                        System.out.println(no + " is running");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            service.execute(runnable);
        }
        Thread.sleep(5000);
        System.out.println("5 second is finished");
        latch.countDown();
    }
}
