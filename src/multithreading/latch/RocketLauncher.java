package multithreading.latch;

import java.util.concurrent.*;
import java.util.stream.Stream;

public class RocketLauncher {
    public static void main(String[] args) throws InterruptedException {

        ExecutorService threadPool = Executors.newCachedThreadPool();
        //CountDownLatch countDownLatch = new CountDownLatch(Details.values().length);

        CyclicBarrier barrier = new CyclicBarrier(Details.values().length, () -> System.out.println("Rocket started"));

        Stream.of(Details.values()).map(detail -> new RocketDetails(detail, barrier)).forEach(threadPool::submit);

        //Stream.of(Details.values()).map(detail -> new RocketDetails(countDownLatch, detail)).forEach(threadPool::execute);
        //new Thread(new Rocket(countDownLatch)).start();


        threadPool.shutdown();
        threadPool.awaitTermination(10, TimeUnit.MINUTES);

    }
}
