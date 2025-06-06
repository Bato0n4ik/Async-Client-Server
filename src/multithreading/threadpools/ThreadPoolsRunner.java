package multithreading.threadpools;

import java.util.concurrent.*;

public class ThreadPoolsRunner {

    public static void main(String[] args) {

        //Callable<Long> thread = new SomeThread(1L);

        ExecutorService threadPool = Executors.newWorkStealingPool(5);

        Future<?> result0 = threadPool.submit(() -> new SomeThread(1L).call());
        Future<?> result1 = threadPool.submit(() -> new SomeThread(2L).call());
        Future<?> result2 = threadPool.submit(() -> new SomeThread(3L).call());
        Future<?> result3 = threadPool.submit(() -> new SomeThread(4L).call());
        Future<?> result4 = threadPool.submit(() -> new SomeThread(5L).call());


        Future<?> result5 = threadPool.submit(() -> new SomeThread(6L).call());
        Future<?> result6 = threadPool.submit(() -> new SomeThread(7L).call());
        Future<?> result7 = threadPool.submit(() -> new SomeThread(8L).call());
        Future<?> result8 = threadPool.submit(() -> new SomeThread(9L).call());
        Future<?> result9 = threadPool.submit(() -> new SomeThread(10L).call());

        threadPool.shutdown();
        try{
            threadPool.awaitTermination(10, TimeUnit.SECONDS);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }







        System.out.println("Thread " + Thread.currentThread().getName().toUpperCase() + " start of main method!");

       Future<?>[] resultList = new Future[]{result0, result1, result2, result3, result4, result5, result6, result7, result8, result9};


       for (Future<?> future : resultList) {
           try{
               System.out.println("Future №-" + future.get() + " is dune: " + future.isDone());
           }
           catch (ExecutionException e) {
               e.printStackTrace(); 
           } catch (InterruptedException e) {
               throw new RuntimeException(e);
           }

       }

        System.out.println("Thread " + Thread.currentThread().getName().toUpperCase() + " end of main method!");



    }
}
