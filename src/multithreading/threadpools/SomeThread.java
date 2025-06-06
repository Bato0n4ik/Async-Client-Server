package multithreading.threadpools;

import java.util.concurrent.Callable;

public class SomeThread implements Callable<Long> {

    private final long id;

    public SomeThread(long id) {
        this.id = id;
    }


    @Override
    public Long call() throws Exception {
        try{
            Thread.sleep(3500);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }

        System.out.println("Thread " + id + " is running");
        return id;
    }
}
