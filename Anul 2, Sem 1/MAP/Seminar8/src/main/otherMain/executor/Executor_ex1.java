package map.seminar13.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Executor_ex1 {
    public static void main(String[] args) {
        ExecutorService executorService =
                Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            public void run() {
                System.out.println("Asynchronous task");
            }
        });

        System.out.println(Thread.currentThread().getName());
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(() -> {
            String threadName = Thread.currentThread().getName();
            System.out.println("Hello " + threadName);
        });
        executor.execute(() -> {
            String threadName = Thread.currentThread().getName();
            System.out.println("Hello " + threadName);

        });
        System.out.println(Thread.currentThread().getName());
        executorService.shutdown();
        executor.shutdown();
    }
}
