package blockqueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class BlockDemo {
    public static void main(String[] args) {
        BlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<>(10);
        Producer producer = new Producer(blockingQueue);
        Consumer consumer = new Consumer(blockingQueue);
        Thread t1 = new Thread(producer);
        Thread t2 = new Thread(producer);
        Thread t3 = new Thread(consumer);
        Thread t4 = new Thread(consumer);
        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }
}

class Producer implements Runnable {

    private final BlockingQueue<Integer> blockingQueue;
    private final AtomicInteger ai = new AtomicInteger(0);

    public Producer(BlockingQueue<Integer> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        while (ai.get() < 100) {
            try {
                blockingQueue.put(ai.getAndIncrement());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Consumer implements Runnable {

    private final BlockingQueue<Integer> blockingQueue;

    public Consumer(BlockingQueue<Integer> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Integer take = blockingQueue.take();
                System.out.println("consumer: " + take);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
