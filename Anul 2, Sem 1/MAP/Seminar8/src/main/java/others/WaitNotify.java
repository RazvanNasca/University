package others;

public class WaitNotify {
    public synchronized void produce() throws InterruptedException {
        System.out.println("Producer thread running ....");
        wait();
        System.out.println("Resumed.");
    }

    public synchronized void consume() throws InterruptedException {
        System.out.println("Consuming....");
        Thread.sleep(1000);
        notify();
        System.out.println("After waiting.");
    }

    public static void main(String[] args) throws InterruptedException {
        final WaitNotify processor = new WaitNotify();
        Thread t1 = new Thread(() -> {
            try {
                processor.produce();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread t2 = new Thread(() -> {
            try {
                processor.consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }
}