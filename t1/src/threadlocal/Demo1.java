package threadlocal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Demo1 {
    public static ExecutorService service = Executors.newFixedThreadPool(16);

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            int finalI = i;
            service.submit(new Runnable() {
                @Override
                public void run() {
                    String date = new Demo1().date(finalI);
                    System.out.println(date);
                }
            });
        }
        service.shutdown();
    }

    public String date(int second) {
        Date date = new Date(1000 * second);
        // 缺点在于每次都要创建这样一个对象
        SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss");
        return dateFormat.format(date);
    }
}
