import java.util.Scanner;

/**
 * Exercise 5: Multiplication Table
 * Objective: Implement loops.
 *
 * Compile: javac Ex05_MultiplicationTable.java
 * Run:     java  Ex05_MultiplicationTable
 */
public class Ex05_MultiplicationTable {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a number: ");
        int num = sc.nextInt();

        System.out.println("\nMultiplication Table for " + num + ":");
        System.out.println("-".repeat(25));
        for (int i = 1; i <= 10; i++) {
            System.out.printf("%3d x %2d = %4d%n", num, i, num * i);
        }
        sc.close();
    }
}
