import java.util.Scanner;

/**
 * Exercise 4: Leap Year Checker
 * Objective: Apply nested conditional logic.
 *
 * A year is a leap year if:
 *   Divisible by 4 AND (NOT divisible by 100 OR divisible by 400).
 *
 * Compile: javac Ex04_LeapYearChecker.java
 * Run:     java  Ex04_LeapYearChecker
 */
public class Ex04_LeapYearChecker {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a year: ");
        int year = sc.nextInt();

        boolean isLeap = (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);

        if (isLeap) {
            System.out.println(year + " IS a leap year.");
        } else {
            System.out.println(year + " is NOT a leap year.");
        }
        sc.close();
    }
}
