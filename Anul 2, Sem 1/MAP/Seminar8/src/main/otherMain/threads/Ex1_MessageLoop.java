package map.seminar13.threads;


public class Ex1_MessageLoop {
    public static void main(String args[]) throws InterruptedException {

        long patience = 1000*3;
        threadMessage("Starting MessageLoop thread");
        long startTime = System.currentTimeMillis();

        Thread t = new Thread(new MessageLoop());
        t.start();

        threadMessage("Waiting for MessageLoop thread to finish");
        while (t.isAlive()) {
            threadMessage("Still waiting...");
            t.join(1000);
            if (((System.currentTimeMillis() - startTime) > patience) && t.isAlive()) {
                threadMessage("Tired of waiting!");
                t.interrupt();
                // Shouldn't be long now -- wait indefinitely
                t.join();
            }
        }
        threadMessage("Finally!");
    }

    private static class MessageLoop implements Runnable {
        public void run() {
            String importantInfo[] = {"A", "B", "C", "D"};
            try {
                for (int i = 0; i < importantInfo.length; i++) {

                    Thread.sleep(1000);

                    threadMessage(importantInfo[i]);
                }
            } catch (InterruptedException e) {
                threadMessage("I wasn't done!");
            }
        }
    }

    // Display a message, preceded by the name of the current thread
    static void threadMessage(String message) {
        String threadName = Thread.currentThread().getName();
        System.out.format("%s: %s%n", threadName, message);
    }
}
