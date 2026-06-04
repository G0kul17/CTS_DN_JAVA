import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Exercise 41: Executor Service and Callable
 * Objective: Use concurrency utilities.
 *
 * Demonstrates: Executors.newFixedThreadPool(), Callable, Future,
 * Future.get(), ExecutorService.shutdown().
 *
 * Compile: javac Ex41_ExecutorService.java
 * Run:     java  Ex41_ExecutorService
 */
public class Ex41_ExecutorService {

    /** Callable that simulates a computation and returns a result */
    static class FactorialCallable implements Callable<Long> {
        private final int n;
        FactorialCallable(int n) { this.n = n; }

        @Override
        public Long call() throws Exception {
            System.out.println("[" + Thread.currentThread().getName() + "] Computing " + n + "!");
            long result = 1;
            for (int i = 2; i <= n; i++) result *= i;
            Thread.sleep(200);  // Simulate I/O delay
            return result;
        }
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        System.out.println("=== ExecutorService & Callable Demo ===\n");

        // Create a fixed thread pool with 3 worker threads
        ExecutorService executor = Executors.newFixedThreadPool(3);

        // Submit 6 Callable tasks
        List<Future<Long>> futures = new ArrayList<>();
        int[] inputs = {5, 6, 7, 8, 9, 10};
        for (int n : inputs) {
            futures.add(executor.submit(new FactorialCallable(n)));
        }

        System.out.println("\n--- Results ---");
        for (int i = 0; i < inputs.length; i++) {
            // Future.get() blocks until result is ready
            long result = futures.get(i).get();
            System.out.println(inputs[i] + "! = " + result);
        }

        // Shutdown — no new tasks accepted; existing tasks finish
        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);
        System.out.println("\nExecutor shut down. All tasks complete.");

        // ── Bonus: ScheduledExecutorService ─────────────────
        System.out.println("\n--- ScheduledExecutorService (3 ticks) ---");
        ScheduledExecutorService sched = Executors.newSingleThreadScheduledExecutor();
        final int[] tick = {0};
        sched.scheduleAtFixedRate(() -> {
            tick[0]++;
            System.out.println("Tick #" + tick[0]);
            if (tick[0] >= 3) sched.shutdown();
        }, 0, 500, TimeUnit.MILLISECONDS);
        sched.awaitTermination(3, TimeUnit.SECONDS);
    }
}
