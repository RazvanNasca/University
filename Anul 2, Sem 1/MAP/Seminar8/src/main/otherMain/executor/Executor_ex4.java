package map.seminar13.executor;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class Executor_ex4 {
    public static Callable<String> callable(String result, long sleepSeconds) {
        return () -> {
            System.out.println("Waiting "+sleepSeconds);
            TimeUnit.SECONDS.sleep(sleepSeconds);
            return result;
        };
    }
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        List<Callable<String>> callables = Arrays.asList(
                callable("task1", 2),
                callable("task2", 1),
                callable("task3", 3));
        String result = null;
        try {
            result = executor.invokeAny(callables);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println(result);
        executor.shutdown();
    }
}





