import java.util.Scanner;

/**
 * Exercise 13: Recursive Fibonacci
 * Objective: Implement recursion.
 *
 * F(0)=0, F(1)=1, F(n)=F(n-1)+F(n-2)
 *
 * Compile: javac Ex13_RecursiveFibonacci.java
 * Run:     java  Ex13_RecursiveFibonacci
 */
public class Ex13_RecursiveFibonacci {

    /** Recursively compute the nth Fibonacci number */
    static long fibonacci(int n) {
        if (n < 0) throw new IllegalArgumentException("n must be >= 0");
        if (n == 0) return 0;
        if (n == 1) return 1;
        return fibonacci(n - 1) + fibonacci(n - 2);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter n (max 40 for speed): ");
        int n = sc.nextInt();

        if (n < 0) {
            System.out.println("Please enter a non-negative integer.");
            sc.close();
            return;
        }

        System.out.println("fibonacci(" + n + ") = " + fibonacci(n));

        System.out.print("Sequence (0 to " + n + "): ");
        for (int i = 0; i <= n; i++) {
            System.out.print(fibonacci(i) + (i < n ? ", " : "\n"));
        }
        sc.close();
    }
}
