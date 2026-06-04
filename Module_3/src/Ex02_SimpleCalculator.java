import java.util.Scanner;

/**
 * Exercise 2: Simple Calculator
 * Objective: Practice arithmetic operations and user input.
 *
 * Compile: javac Ex02_SimpleCalculator.java
 * Run:     java  Ex02_SimpleCalculator
 */
public class Ex02_SimpleCalculator {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter first number : ");
        double num1 = sc.nextDouble();

        System.out.print("Enter second number: ");
        double num2 = sc.nextDouble();

        System.out.println("\nAvailable operations: +  -  *  /");
        System.out.print("Enter operator: ");
        char op = sc.next().charAt(0);

        double result;
        switch (op) {
            case '+' -> result = num1 + num2;
            case '-' -> result = num1 - num2;
            case '*' -> result = num1 * num2;
            case '/' -> {
                if (num2 == 0) {
                    System.out.println("Error: Division by zero is undefined.");
                    sc.close();
                    return;
                }
                result = num1 / num2;
            }
            default -> {
                System.out.println("Error: Unknown operator '" + op + "'.");
                sc.close();
                return;
            }
        }

        System.out.printf("Result: %.2f %c %.2f = %.2f%n", num1, op, num2, result);
        sc.close();
    }
}
