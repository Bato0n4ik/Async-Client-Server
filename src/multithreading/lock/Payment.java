package multithreading.lock;


public class Payment implements Runnable {

     private final  Account account1;
     private  final  Account account2;

     public Payment(Account account1, Account account2) {
         this.account1 = account1;
         this.account2 = account2;
     }

    @Override
    public void run() {
        //synchronized (account1){
            //synchronized (account2){
                try {
                    lockingCouple();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                for(int i = 1; i < 200; i++){
                    if(account1.tackOff(10)){
                        account2.add(10);
                    }
                }

                System.out.printf("Payment completed for thread %s\n", Thread.currentThread().getName());
                System.out.println("money in Account1: " + account1.getMoney());
                System.out.println("money in Account2: " + account2.getMoney());

                account1.lock.unlock();
                account2.lock.unlock();
            //}
        //}

    }

    private void lockingCouple() throws InterruptedException {
        while(true){
            boolean lock1 = account1.lock.tryLock();
            boolean lock2 = account2.lock.tryLock();

            if(lock1 && lock2){
                break;
            } else if (lock1) {
                account1.lock.unlock();
            } else if (lock2) {
                account2.lock.unlock();
            }

        }
    }
}
