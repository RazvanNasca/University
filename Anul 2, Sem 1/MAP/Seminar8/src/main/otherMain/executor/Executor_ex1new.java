package map.seminar13.executor;

import java.util.concurrent.*;

public class Executor_ex1new {
    public static void main(String[] args)  {
        Runnable runnableTask = () -> {
            try {
                System.out.println(Thread.currentThread().getName());
                TimeUnit.MILLISECONDS.sleep(300);
                System.out.println("Runnable task");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Runnable incerc = new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println(Thread.currentThread().getName());
                    TimeUnit.MILLISECONDS.sleep(300);
                    System.out.println("Runnable task 2");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Callable<Integer> callable = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println(Thread.currentThread().getName());
                System.out.println("Callable 1");
                return 3;
            }
        };

        ExecutorService executorService = Executors.newFixedThreadPool(5);
        executorService.execute(runnableTask);
        executorService.submit(incerc);
        Future<Integer> future = executorService.submit(callable);

        try {
            System.out.println(future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        executorService.shutdown();
    }
}
