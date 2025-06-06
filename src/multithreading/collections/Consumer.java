package multithreading.collections;

import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable {

    private final BlockingQueue<CashBox> cashBoxes;

    public Consumer(BlockingQueue<CashBox> cashBoxes) {
        this.cashBoxes = cashBoxes;
    }

    public void run() {
        try {
            CashBox cashBox =  cashBoxes.take();
            System.out.println("Consumer " + Thread.currentThread().getId() + " take a cash box №-" + cashBox.id());
            System.out.println("Consumer " + Thread.currentThread().getId() + " liberated a cash box №-" + cashBox.id());
            cashBoxes.put(cashBox);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
