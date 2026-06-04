import java.util.Scanner;

/**
 * Exercise 3: Even or Odd Checker
 * Objective: Utilize conditional statements.
 *
 * Compile: javac Ex03_EvenOddChecker.java
 * Run:     java  Ex03_EvenOddChecker
 */
public class Ex03_EvenOddChecker {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter an integer: ");
        int number = sc.nextInt();

        // Modulus operator: remainder when divided by 2
        if (number % 2 == 0) {
            System.out.println(number + " is EVEN.");
        } else {
            System.out.println(number + " is ODD.");
        }
        sc.close();
    }
}
