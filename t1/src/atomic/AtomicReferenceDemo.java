package atomic;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferenceDemo {
    public static void main(String[] args) {
        AtomicReference atomic1 = new AtomicReference();
        atomic1.set("aaa");
        atomic1.set(new StringBuffer("str"));
        Map map = new HashMap<>();
        map.put("name", "ross");
        atomic1.set(map);
        System.out.println(atomic1.get());

        String[] s = {"aa", "bb", "cc"};
        AtomicReference atomic2 = new AtomicReference(s);
        System.out.println(atomic2.get());
        atomic2.set(atomic1);
        System.out.println(atomic2.get());
    }
}
