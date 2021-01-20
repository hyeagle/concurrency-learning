package threadlocal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Demo2 {
    public static ExecutorService service = Executors.newFixedThreadPool(16);

    // 方式一
    public static ThreadLocal<SimpleDateFormat> dateFormat = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("mm:ss");
        }
    };

    public static ConcurrentHashMap<Integer, Integer> map = new ConcurrentHashMap<>();

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            int finalI = i;
            service.execute(new Runnable() {
                @Override
                public void run() {
                    // String date = new Demo2().date(finalI);
                    SimpleDateFormat dateFormat = Demo2.dateFormat.get();
                    String date = dateFormat.format(finalI * 1000);
                    System.out.println(date);
                    map.put(System.identityHashCode(dateFormat), map.getOrDefault(dateFormat.hashCode(), 0) + 1);
                }
            });
        }
        service.shutdown();
        Thread.sleep(5000);
        System.out.println(map.size());
    }

    public String date(int seconds) {
        Date date = new Date(1000 * seconds);
        SimpleDateFormat dateFormat = ThreadSafeFormatter.dateFormatThreadLocal.get();
        map.put(System.identityHashCode(dateFormat), map.getOrDefault(dateFormat.hashCode(), 0) + 1);
        return dateFormat.format(date);
    }
}

// 方式二
class ThreadSafeFormatter {
    public static ThreadLocal<SimpleDateFormat> dateFormatThreadLocal = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("mm:ss");
        }
    };
}
