import java.util.Scanner;

/**
 * Exercise 21: Custom Exception
 * Objective: Create and use custom exceptions.
 *
 * Defines InvalidAgeException; throws it if age < 18.
 *
 * Compile: javac Ex21_CustomException.java
 * Run:     java  Ex21_CustomException
 */
public class Ex21_CustomException {

    // Custom checked exception
    static class InvalidAgeException extends Exception {
        private final int age;

        InvalidAgeException(int age) {
            super("Age " + age + " is invalid. Minimum required age is 18.");
            this.age = age;
        }

        int getAge() { return age; }
    }

    static void validateAge(int age) throws InvalidAgeException {
        if (age < 18) throw new InvalidAgeException(age);
        System.out.println("Age " + age + " is valid. Access granted!");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter your age: ");
        int age = sc.nextInt();

        try {
            validateAge(age);
        } catch (InvalidAgeException e) {
            System.out.println("Custom Exception caught!");
            System.out.println("Message    : " + e.getMessage());
            System.out.println("Age entered: " + e.getAge());
        } finally {
            sc.close();
        }
    }
}
