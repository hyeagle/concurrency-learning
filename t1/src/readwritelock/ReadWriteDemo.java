package readwritelock;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteDemo {

    private static final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock(false);
    // private static final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);
    private static final ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();
    private static final ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();

    private static void read() {
        readLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " get read lock");
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + " release read lock");
            readLock.unlock();
        }
    }

    private static void write() {
        writeLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " get write lock");
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + " release write lock");
            writeLock.unlock();
        }
    }

    public static void main(String[] args) {
        new Thread(ReadWriteDemo::read).start();
        new Thread(ReadWriteDemo::read).start();
        new Thread(ReadWriteDemo::write).start();
        // new Thread(ReadWriteDemo::write).start();
        new Thread(ReadWriteDemo::read).start(); // 公平锁的条件下，会等待
    }
}
