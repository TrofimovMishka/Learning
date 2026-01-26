package org.example;

import java.util.concurrent.*;

/**
 * Hello world!
 *
 */
public class App 
{
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

        do{
            done = result.isDone();
            Thread.sleep(500L);
        } while (!done);

        String res = result.get();

        System.out.println("result of callable is "+ res);

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
    public static void main(String[] args) {
        try(ThreadPoolExecutor executorService = new ThreadPoolExecutor(5, 10, 500L, TimeUnit.MINUTES, App.workQueue)){
            executorService.submit(() -> {
                System.out.println("Callable in ThreadPoolExecutor");
                return "BOB snail";
            });
        }

        try (ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(100)) {
            scheduledThreadPoolExecutor.execute(() -> System.out.println("Runnable in scheduledThreadPoolExecutor"));
        } catch (Exception ex) {
            System.out.println("Catch block in work");
        } finally {
            System.out.println("Finally block in work");
        }

        try(ForkJoinPool fjp = new ForkJoinPool(50)){
            fjp.execute(() -> System.out.println("Some task in ForkJoinPool"));
        }
    }
}

class SynchronizedExplanation{

    private static final String LOCK = "LOCK";

    public static void main(String[] args) {
        synchronized(LOCK) {
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