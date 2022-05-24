package threads;

class Consumer implements Runnable {
    private BankAccount1 account;
    private double amount;
    public Consumer(BankAccount1 acct, double amt) { account = acct; amount=amt;}
    public void run() {
        account.withdraw(amount);
    }
}
