import java.util.Random;
import java.util.Scanner;

/**
 * Exercise 10: Number Guessing Game
 * Objective: Implement loops and conditional logic.
 *
 * The program picks a random number 1-100; user guesses until correct.
 *
 * Compile: javac Ex10_NumberGuessingGame.java
 * Run:     java  Ex10_NumberGuessingGame
 */
public class Ex10_NumberGuessingGame {
    public static void main(String[] args) {
        Random  rng      = new Random();
        Scanner sc       = new Scanner(System.in);
        int     secret   = rng.nextInt(100) + 1;  // 1 to 100 inclusive
        int     attempts = 0;

        System.out.println("=== Number Guessing Game ===");
        System.out.println("I picked a number between 1 and 100. Can you guess it?");

        while (true) {
            System.out.print("Your guess: ");
            int guess = sc.nextInt();
            attempts++;

            if (guess < 1 || guess > 100) {
                System.out.println("Out of range! Guess between 1 and 100.");
            } else if (guess < secret) {
                System.out.println("Too LOW! Try higher.");
            } else if (guess > secret) {
                System.out.println("Too HIGH! Try lower.");
            } else {
                System.out.println("Correct! The number was " + secret + ".");
                System.out.println("You got it in " + attempts + " attempt(s)!");
                break;
            }
        }
        sc.close();
    }
}
