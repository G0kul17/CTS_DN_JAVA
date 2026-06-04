import java.util.Scanner;

/**
 * Exercise 20: Try-Catch Example
 * Objective: Handle exceptions gracefully.
 *
 * Demonstrates: ArithmeticException, NumberFormatException,
 * multi-catch, finally block.
 *
 * Compile: javac Ex20_TryCatchExample.java
 * Run:     java  Ex20_TryCatchExample
 */
public class Ex20_TryCatchExample {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("=== Exception Handling Demo ===\n");

        try {
            System.out.print("Enter numerator  : ");
            int numerator   = Integer.parseInt(sc.nextLine().trim());

            System.out.print("Enter denominator: ");
            int denominator = Integer.parseInt(sc.nextLine().trim());

            int result = numerator / denominator;  // ArithmeticException if denom=0
            System.out.println("Result: " + numerator + " / " + denominator + " = " + result);

        } catch (ArithmeticException e) {
            System.out.println("ArithmeticException: " + e.getMessage());
            System.out.println("You cannot divide by zero!");

        } catch (NumberFormatException e) {
            System.out.println("NumberFormatException: Please enter valid integers.");

        } finally {
            System.out.println("\n[finally] Always executes — cleanup here.");
            sc.close();
        }
    }
}
