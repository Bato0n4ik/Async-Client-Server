package multithreading.latch;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class RocketDetails implements Runnable {

    //private final CountDownLatch countDownLatch;
    private final CyclicBarrier barrier;
    private final Details detail;

    //public RocketDetails(CountDownLatch countDownLatch , Details detail) {
    //    this.countDownLatch = countDownLatch;
    //    this.detail = detail;
    //}

    public RocketDetails(final Details detail, final CyclicBarrier barrier) {
        this.detail = detail;
        this.barrier = barrier;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(2000);
            System.out.println(detail + " is finished");
            barrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            throw new RuntimeException(e);
        }
        //countDownLatch.countDown();
    }
}
