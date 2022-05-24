package map.seminar13.executor;

import java.util.concurrent.*;

public class Executor_ex2 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executor =
                Executors.newFixedThreadPool(2);

        Future<String> future = executor.submit(new Callable<String>(){
            public String call() throws Exception {
                Thread.sleep(2000);
                System.out.println("Asynchronous Callable");
                return "Callable Result";
            }
        });

        executor.submit(()->{
            try {
                System.out.println(future.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });
        //System.out.println(future.get());
        System.out.println(Thread.currentThread().getName());
        executor.shutdown();
    }
}

