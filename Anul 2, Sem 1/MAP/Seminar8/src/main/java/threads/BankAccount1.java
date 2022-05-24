package threads;

public class BankAccount1 {
    private double balance;

    public BankAccount1(double bal) {
        balance = bal;
    }

    public BankAccount1() {
        this(0);
    }

    public double getBalance() {
        return balance;
    }

    public synchronized void deposit(double amt) {
        double temp = balance;
        temp = temp + amt;
        try {
            Thread.sleep(300); // simulate production time
        } catch (InterruptedException ie) {
            System.err.println(ie.getMessage());
        }
        balance = temp;
        System.out.println("after deposit balance = $" + balance);
        notifyAll();
    }

    public synchronized void withdraw(double amt) {
            while (balance < amt) {
                System.out.println("Insufficient funds! waiting ... ");
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            double temp = balance;
            temp = temp - amt;
            try {
                Thread.sleep(200); // simulate consumption time
            } catch (InterruptedException ie) {
                System.err.println(ie.getMessage());
            }
            balance = temp;
            System.out.println("after withdrawl balance = $" + balance);
    }
}
