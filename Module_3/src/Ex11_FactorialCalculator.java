import java.util.Scanner;

/**
 * Exercise 11: Factorial Calculator
 * Objective: Use loops to perform repetitive calculations.
 *
 * Compile: javac Ex11_FactorialCalculator.java
 * Run:     java  Ex11_FactorialCalculator
 */
public class Ex11_FactorialCalculator {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a non-negative integer: ");
        int n = sc.nextInt();

        if (n < 0) {
            System.out.println("Factorial is undefined for negative numbers.");
            sc.close();
            return;
        }

        long factorial = 1;       // long supports up to 20!
        for (int i = 2; i <= n; i++) {
            factorial *= i;
        }

        System.out.println(n + "! = " + factorial);
        sc.close();
    }
}
