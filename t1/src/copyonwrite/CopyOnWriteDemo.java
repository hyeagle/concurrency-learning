package copyonwrite;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteDemo {
    public static void main(String[] args) {
        t2();
    }

    private static void t1() {
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>(new Integer[]{1, 2, 3});
        System.out.println(list); // [1,2,3]

        Iterator<Integer> itr1 = list.iterator();

        list.add(4);
        System.out.println(list); // [1,2,3,4]

        Iterator<Integer> itr2 = list.iterator();

        System.out.println("===iterator 1===");
        itr1.forEachRemaining(System.out::println);// 1,2,3

        System.out.println("===iterator 2");
        itr2.forEachRemaining(System.out::println);// 1,2,3,4
    }

    private static void t2() {
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>(new Integer[]{1, 2, 3});
        System.out.println(list); // [1,2,3]

        T1 t1 = new T1(list);
        new Thread(t1).start();
        new Thread(t1).start();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Iterator<Integer> itr = list.iterator();
        itr.forEachRemaining(System.out::println);
    }

    static class T1 implements Runnable {
        CopyOnWriteArrayList<Integer> list;

        public T1(CopyOnWriteArrayList<Integer> list) {
            this.list = list;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
                list.add(10);
                Iterator<Integer> itr = list.iterator();
                Thread.sleep(1000);
                itr.forEachRemaining(System.out::println);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
