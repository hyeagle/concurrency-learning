package customQueue;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class CustomBlockingQueue<T> {

    private Queue<T> queue;
    private int size;
    private ReentrantLock lock;
    private Condition full;
    private Condition empty;

    public CustomBlockingQueue(int size) {
        this.queue = new LinkedList<T>();
        this.size = size;
        lock = new ReentrantLock();
        full = lock.newCondition();
        empty = lock.newCondition();
    }

    public void put(T t) throws InterruptedException {
        lock.lock();
        while (queue.size() == size) {
            full.await();
        }
        queue.add(t);
        empty.signalAll();
        lock.unlock();
    }

    public T take() throws InterruptedException {
        lock.lock();
        while (queue.size() == 0) {
            empty.await();
        }
        T t = queue.remove();
        full.signalAll();
        lock.unlock();
        return t;
    }
}