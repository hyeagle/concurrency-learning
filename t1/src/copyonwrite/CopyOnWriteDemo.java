package copyonwrite;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteDemo {
    public static void main(String[] args) {
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
}
