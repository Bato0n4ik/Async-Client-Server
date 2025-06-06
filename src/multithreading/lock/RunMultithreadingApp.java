package multithreading.lock;

public class RunMultithreadingApp {

    public static void main(String[] args) throws InterruptedException {
        Account account1 = new Account(2000);
        Account account2 = new Account(2000);

        Thread payment1 = new Thread(new Payment(account1, account2), "payment1");
        Thread payment2 = new Thread(new Payment(account2, account1), "payment2");

        payment1.start();
        payment2.start();

        payment1.join();
        payment2.join();

    }
}
