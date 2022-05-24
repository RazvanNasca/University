package executor;

public class Thread_Daemon {
    public static void main(String[] args) {
        Thread tr=new Thread(()->{
            System.out.println(Thread.currentThread().getName());
        });
        tr.setDaemon(false);
        tr.start();

        System.out.println(Thread.currentThread().getName());
    }
}
