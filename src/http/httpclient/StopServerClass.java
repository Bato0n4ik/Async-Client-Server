package http.httpclient;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

public class StopServerClass implements Runnable {

    private final CountDownLatch latch;
    private final AtomicBoolean stopped;

    public StopServerClass(CountDownLatch latch, AtomicBoolean stopped) {
        this.latch = latch;
        this.stopped = stopped;
    }

    @Override
    public void run() {
        try {
            latch.await();
            stopped.set(true);
            System.out.println("Latch current count: " +latch.getCount());
        } catch (InterruptedException e) {
            System.out.println("Error  StopServerClass.class in run() method,  can't correctly stop server " + e.getMessage());
        }
        System.out.println("StopServerClass end");
    }
}
