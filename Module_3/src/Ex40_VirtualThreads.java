import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Exercise 40: Virtual Threads (Java 21)
 * Objective: Use lightweight virtual threads for scalable concurrency.
 *
 * Virtual threads are managed by the JVM, not the OS.
 * They are extremely lightweight — millions can run concurrently.
 *
 * Compile: javac Ex40_VirtualThreads.java
 * Run:     java  Ex40_VirtualThreads
 */
public class Ex40_VirtualThreads {

    static final int THREAD_COUNT = 10_000;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Virtual Threads Demo (Java 21) ===\n");

        // ── 1. Launch THREAD_COUNT virtual threads ────────────
        System.out.println("Launching " + THREAD_COUNT + " virtual threads...");
        Instant start = Instant.now();

        List<Thread> threads = new ArrayList<>(THREAD_COUNT);
        for (int i = 0; i < THREAD_COUNT; i++) {
            final int id = i;
            Thread vt = Thread.ofVirtual().start(() -> {
                // Simulate a small amount of work / I/O
                try { Thread.sleep(10); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                if (id < 5) System.out.println("Virtual thread " + id + " done."); // Print only first 5
            });
            threads.add(vt);
        }

        // Wait for all virtual threads
        for (Thread t : threads) t.join();

        long ms = Duration.between(start, Instant.now()).toMillis();
        System.out.println("All " + THREAD_COUNT + " virtual threads completed in " + ms + " ms.\n");

        // ── 2. Compare: platform threads would be MUCH slower ─
        System.out.println("--- Virtual Thread Properties ---");
        Thread sample = Thread.ofVirtual().unstarted(() -> {});
        System.out.println("Is virtual?  : " + sample.isVirtual());
        System.out.println("Thread.currentThread().isVirtual() from inside: ");
        Thread.ofVirtual().start(() ->
            System.out.println("  " + Thread.currentThread().isVirtual())
        ).join();

        // ── 3. Alternative: Thread.startVirtualThread ─────────
        System.out.println("\n--- Thread.startVirtualThread API ---");
        Thread t = Thread.startVirtualThread(() ->
            System.out.println("Started via Thread.startVirtualThread: " +
                Thread.currentThread().isVirtual())
        );
        t.join();

        System.out.println("\nDone.");
    }
}
