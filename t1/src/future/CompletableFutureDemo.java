package future;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import static java.util.concurrent.TimeUnit.SECONDS;

public class CompletableFutureDemo {

    public static void main(String[] args) {
//        CompletableFutureDemo completableFutureDemo = new CompletableFutureDemo();
//        System.out.println(completableFutureDemo.getPrices());
//        t1();
//        thenApplyDemo();
//        thenAcceptDemo();
        thenRunDemo().join();
    }


    private Set<Integer> getPrices() {
        Set<Integer> prices = Collections.synchronizedSet(new HashSet<>());
        CompletableFuture<Void> task1 = CompletableFuture.runAsync(new Task(123, prices)); // 异步调用
        CompletableFuture<Void> task2 = CompletableFuture.runAsync(new Task(456, prices));
        CompletableFuture<Void> task3 = CompletableFuture.runAsync(new Task(789, prices));

        CompletableFuture<Void> allTasks = CompletableFuture.allOf(task1, task2, task3);
        try {
            allTasks.get(5, SECONDS); // 等待 3 秒会抛出 TimeoutException
        } catch (Exception e) {
            e.printStackTrace();
        }
        return prices;
    }

    class Task implements Runnable {

        Integer productId;
        Set<Integer> prices;

        public Task(Integer productId, Set<Integer> prices) {
            this.productId = productId;
            this.prices = prices;
        }

        @Override
        public void run() {
            int price = 0;
            try {
                Thread.sleep((long) (Math.random() * 4000));
                price = (int) (Math.random() * 4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            prices.add(price);
        }
    }

    public static void t1() {
        CompletableFuture<Void> f1 = CompletableFuture.runAsync(() -> {
            try {
                System.out.println("T1:洗水壶...");
                Thread.sleep(1000);
                System.out.println("T1:烧开水...");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        CompletableFuture<String> f2 = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println("T2:洗茶壶...");
                Thread.sleep(1000);
                System.out.println("T2:洗茶杯...");
                Thread.sleep(2000);
                System.out.println("T2:拿茶叶...");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "龙井";
        });

        CompletableFuture<String> f3 = f1.thenCombine(f2, (__, tf) -> {
            System.out.println("T1:拿茶叶:" + tf);
            System.out.println("T1:泡茶...");
            return "上茶:" + tf;
        });

        System.out.println(f3.join());
    }

    private static void thenApplyDemo() {
        CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> "hello world");

        CompletableFuture<String> f2 = f1.thenApply(v -> v + "world");
        System.out.println(f2.join());
    }

    private static void thenAcceptDemo() {
        CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> "hello world");

        f1.thenAccept(v -> System.out.println(v));
    }

    private static CompletableFuture<Void> thenRunDemo() {
        CompletableFuture<Void> f1 = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("hello world");
        });

        return f1.thenRun(() -> System.out.println("hello world2"));
    }
}


