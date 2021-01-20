package longaddr;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LongAddrDemo {

    public static void main(String[] args) throws InterruptedException {

//        longAddrFunc();
        accumulator();

    }

    private static void accumulator() throws InterruptedException {
        LongAccumulator accumulator = new LongAccumulator(Math::max, 0);
        ExecutorService service = Executors.newFixedThreadPool(16);
        for (long i = 0; i < 100; i++) {
            long finalI = i;
            service.submit(() -> accumulator.accumulate(finalI));
        }
        Thread.sleep(2000);
        System.out.println(accumulator.getThenReset());
    }

    private static void longAddrFunc() throws InterruptedException {
        LongAdder counter = new LongAdder();
        ExecutorService service = Executors.newFixedThreadPool(16);
        for (int i = 0; i < 100; i++) {
            service.execute(new Task(counter));
        }

        Thread.sleep(2000);
        System.out.println(counter.sum());
    }

    static class Task implements Runnable {
        private final LongAdder counter;
        // 效率比 AtomicLong 高，AtomicLong 在高并发下，cas 经常失败
        // LongAddr 只提供 add、increment 等简单方法，适合统计求和计数等场景

        public Task(LongAdder counter) {
            this.counter = counter;
        }

        @Override
        public void run() {
            counter.increment();
        }
    }
}
