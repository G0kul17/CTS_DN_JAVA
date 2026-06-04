/**
 * Exercise 26: Thread Creation
 * Objective: Implement multithreading.
 *
 * Demonstrates: extending Thread, implementing Runnable,
 * lambda thread, thread join.
 *
 * Compile: javac Ex26_ThreadCreation.java
 * Run:     java  Ex26_ThreadCreation
 */
public class Ex26_ThreadCreation {

    // Method 1: Extend Thread
    static class CounterThread extends Thread {
        private final String label;
        private final int    count;
        CounterThread(String label, int count) { this.label = label; this.count = count; }

        @Override
        public void run() {
            for (int i = 1; i <= count; i++) {
                System.out.println("[" + label + "] count = " + i);
                try { Thread.sleep(100); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            }
        }
    }

    // Method 2: Implement Runnable
    static class MessagePrinter implements Runnable {
        private final String msg;
        private final int    reps;
        MessagePrinter(String msg, int reps) { this.msg = msg; this.reps = reps; }

        @Override
        public void run() {
            for (int i = 0; i < reps; i++) {
                System.out.println("[Runnable] " + msg + " (" + (i + 1) + ")");
                try { Thread.sleep(150); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Multithreading Demo ===\n");

        CounterThread t1 = new CounterThread("Alpha", 4);
        CounterThread t2 = new CounterThread("Beta",  4);
        Thread        t3 = new Thread(new MessagePrinter("Hello from Runnable", 3));

        // Method 3: Lambda Runnable
        Thread t4 = new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                System.out.println("[Lambda] iteration " + (i + 1));
                try { Thread.sleep(120); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            }
        });

        t1.start(); t2.start(); t3.start(); t4.start();
        t1.join();  t2.join();  t3.join();  t4.join();
        System.out.println("\nAll threads completed.");
    }
}
