package threads;

public class BankAccount0 {
    private double balance;
    public BankAccount0(double bal) { balance = bal; }
    public BankAccount0() { this(0); }
    public double getBalance() { return balance; }
    public   void  deposit(double amt) {
        double temp = balance;
        temp = temp + amt;
        try {
            Thread.sleep(300); // simulate production time
        } catch (InterruptedException ie) {
            System.err.println(ie.getMessage());
        }
        balance = temp;
    }
    public  void withdraw(double amt) {
        if (balance < amt) {
            System.out.println("Insufficient funds!");
            return;
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
