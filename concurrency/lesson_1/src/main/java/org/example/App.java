package org.example;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Hello world!
 *
 */
public class App {
    static final BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(100);

    public static void main( String[] args ) throws InterruptedException, ExecutionException {
        System.out.println( "Hello World!" );

        Runnable r = new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello World from Runnable!");
            }
        };
        Thread thread1 = new Thread(r);
        thread1.setDaemon(true);
        thread1.start();

        Thread thread2 = new Thread(() -> System.out.println("Runnable instance in work"));
        thread2.setDaemon(true);
        thread2.start();

        Callable<String> callable = () -> {
            System.out.println("Call from BOB thread");
            return "BOB";
        };

        //TODO: Use try with resources if needed:
        ExecutorService executorService = new ThreadPoolExecutor(5, 10, 500L, TimeUnit.MINUTES, workQueue);

        Future<String> result = executorService.submit(callable);

        boolean done;

        do {
            done = result.isDone();
            Thread.sleep(500L);
        } while (!done);

        String res = result.get();

        System.out.println("result of callable is " + res);

    }

}

class DaemonThread {
    public static void main(String[] args) {
        Thread thread2 = new Thread(() -> System.out.println("Runnable instance in work"));
        thread2.setDaemon(true);
        thread2.start();
    }
}

class ExampleOfExecutorService {
//    public static void main(String[] args) {
//        try(ThreadPoolExecutor executorService = new ThreadPoolExecutor(5, 10, 500L, TimeUnit.MINUTES, App.workQueue)){
//            executorService.submit(() -> {
//                System.out.println("Callable in ThreadPoolExecutor");
//                return "BOB snail";
//            });
//        }
//
//        try (ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(100)) {
//            scheduledThreadPoolExecutor.execute(() -> System.out.println("Runnable in scheduledThreadPoolExecutor"));
//        } catch (Exception ex) {
//            System.out.println("Catch block in work");
//        } finally {
//            System.out.println("Finally block in work");
//        }
//
//        try (ForkJoinPool fjp = new ForkJoinPool(50)) {
//            fjp.execute(() -> System.out.println("Some task in ForkJoinPool"));
//        }
//    }
}

class SynchronizedExplanation {

    private static final String LOCK = "LOCK";

    public static void main(String[] args) {
        synchronized (LOCK) {
            System.out.println("Acquire monitor of LOCK obj");
        }
    }

    synchronized void instanceMethod() {
        System.out.println("The instance itself (this) acts as a monitor");
    }

    static synchronized void staticMethod() {
        System.out.println("The monitor is the Class object");
    }
}

class Bank {
    private static final AtomicReference<BigDecimal> PRICE = new AtomicReference<>();

    public Bank() {
        PRICE.set(new BigDecimal("8883.19199"));
    }

    void getPrice(){
        String msg = "Price = " + PRICE.get().toEngineeringString();
        System.out.println(msg);
    }

    void addToPrice(final String value){
        BigDecimal oldPrice = PRICE.getAndUpdate(p -> p.subtract(new BigDecimal(value)));
        String msg = "Price = " + PRICE.get().toEngineeringString();
        System.out.println(msg);
        BigDecimal newPrice = PRICE.updateAndGet(p -> p.subtract(new BigDecimal(value)));

        String msg1 = "oldPrice = " + oldPrice.toEngineeringString();
        String msg2 = "newPrice = " + newPrice.toEngineeringString();

        System.out.println(msg1);
        System.out.println(msg2);
    }

    void divideBy(final String value, final String divisor){

        BigDecimal resultUp = new BigDecimal(value).divide(new BigDecimal(divisor), RoundingMode.UP);
        BigDecimal resultDown = new BigDecimal(value).divide(new BigDecimal(divisor), RoundingMode.DOWN);
        BigDecimal resultHalfUp = new BigDecimal(value).divide(new BigDecimal(divisor), RoundingMode.HALF_UP);
        BigDecimal resultHalfDown = new BigDecimal(value).divide(new BigDecimal(divisor), RoundingMode.HALF_DOWN);
        BigDecimal resultHalfEven = new BigDecimal(value).divide(new BigDecimal(divisor), RoundingMode.HALF_EVEN);

        System.out.println("resultUp = " + resultUp.toEngineeringString());
        System.out.println("resultDown = " + resultDown.toEngineeringString());
        System.out.println("resultHalfUp = " + resultHalfUp.toEngineeringString());
        System.out.println("resultHalfDown = " + resultHalfDown.toEngineeringString());
        System.out.println("resultHalfEven = " + resultHalfEven.toEngineeringString());
    }

    public static void main(String[] args) {
        Bank bank = new Bank();

        bank.getPrice();
        bank.addToPrice("81.743");
        bank.getPrice();
        bank.divideBy("1.99", "3");
    }

    // ❌ Needs synchronization for complex logic
    public void complexUpdate(String value) {
        BigDecimal current = PRICE.get();
        if (current.compareTo(new BigDecimal("50")) > 0) {
            // Another thread could change PRICE here!
            PRICE.updateAndGet(p -> p.subtract(new BigDecimal(value)));
        }
    }

    // ✅ Thread-safe version
    public void complexUpdateSafe(String value) {
        PRICE.updateAndGet(p -> {
            if (p.compareTo(new BigDecimal("50")) > 0) {
                return p.subtract(new BigDecimal(value));
            }
            return p; // No change
        });
    }
}
