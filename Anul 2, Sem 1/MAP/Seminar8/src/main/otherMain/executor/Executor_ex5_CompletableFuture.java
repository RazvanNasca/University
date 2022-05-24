//package map.seminar13.executor;
//
//import java.util.concurrent.CompletableFuture;
//import java.util.concurrent.ExecutionException;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//public class Executor_ex5_CompletableFuture {
//    public static void main(String[] args) {
//        CompletableFuture<String> completableFuture
//                = CompletableFuture.supplyAsync(() -> {
//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println("0");
//            return "Hello";
//
//        });
//        System.out.println("1");
//
//        CompletableFuture<String> future = completableFuture
//                .thenApplyAsync(s -> {
//                    try {
//                        Thread.sleep(3000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    System.out.println(2);
//                    return s + " World";});
//
//        System.out.println("3");
//
//        try {
//            assertEquals("Hello World", future.get());
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println("4");
//        // write your code here
//    }
//}
