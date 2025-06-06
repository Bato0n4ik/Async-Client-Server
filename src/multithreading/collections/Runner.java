package multithreading.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Runner {

    public static void main(String[] args) {
        BlockingQueue<CashBox> cashBoxes = new ArrayBlockingQueue<>(2, true, List.of(new CashBox(1), new CashBox(2)));

        List<Thread> threads = Stream.of(
                new Consumer(cashBoxes),
                new Consumer(cashBoxes),
                new Consumer(cashBoxes),
                new Consumer(cashBoxes),
                new Consumer(cashBoxes),
                new Consumer(cashBoxes),
                new Consumer(cashBoxes)
        ).map(Thread::new)
                .peek(Thread::start)
                .toList();
    }
}
