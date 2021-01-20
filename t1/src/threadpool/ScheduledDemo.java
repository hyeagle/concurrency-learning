package threadpool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledDemo {
    public static void main(String[] args) {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);

        // 延迟 10 秒执行
        // scheduledExecutorService.schedule(new MyThread2(), 10, TimeUnit.SECONDS);

        // 延迟 10 秒执行，间隔 5 秒
        // scheduledExecutorService.scheduleAtFixedRate(new MyThread2(), 10, 2, TimeUnit.SECONDS);

        // 延迟 10 秒执行，上次结束后间隔 5 秒
        scheduledExecutorService.scheduleWithFixedDelay(new MyThread2(), 10, 5, TimeUnit.SECONDS);
    }
}

class MyThread2 implements Runnable {

    @Override
    public void run() {
        System.out.println("thread name = " + Thread.currentThread());
    }
}
