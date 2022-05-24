package map.seminar13.threads;

public class Ex_TestProducerConsumer {
    public static void test_deposit0() throws InterruptedException {
        BankAccount0 account = new BankAccount0(0);
        Thread t1 = new Thread(() -> account.deposit(50));
        Thread t2 = new Thread(() -> account.deposit(50));
        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("after deposit balance = $" + account.getBalance());
    }

    public static void test_deposit1() throws InterruptedException {
        BankAccount1 account = new BankAccount1(50);
        int slaveCount = 4;
        Thread[] slaves = new Thread[slaveCount];
        for (int i = 0; i < slaveCount; i++) {
            if (i == 0) {
                slaves[i] = new Thread(new Producer(account, 50));
            } else {
                slaves[i] = new Thread(new Consumer(account, 50));
            }
        }
        for (int i = 0; i < slaveCount; i++) {
            slaves[i].start();
        }

        for (int i = 0; i < slaveCount; i++) {
            try {
                slaves[i].join();
            } catch (InterruptedException ie) {
                System.err.println(ie.getMessage());
            } finally {
                System.out.println("slave " + i + " has died");
            }
        }
        System.out.print("Closing balance = ");
        System.out.println("$" + account.getBalance());
    }

    public static void main(String[] args) throws InterruptedException {
       // test_deposit0();
        test_deposit1();

    }
}
