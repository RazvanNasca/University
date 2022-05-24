package threads;

class Producer implements Runnable {
    private BankAccount1 account;
    private double amount;
    public Producer(BankAccount1 acct, double amt) { account = acct; amount=amt;}
    public void run() {
        account.deposit(amount);

    }
}
