import java.util.Scanner;

/**
 * Exercise 16: Palindrome Checker
 * Objective: Combine string manipulation and conditional logic.
 *
 * A palindrome reads the same forwards and backwards (ignoring
 * non-alphanumeric characters and case).
 * Examples: "racecar", "A man a plan a canal Panama"
 *
 * Compile: javac Ex16_PalindromeChecker.java
 * Run:     java  Ex16_PalindromeChecker
 */
public class Ex16_PalindromeChecker {

    static boolean isPalindrome(String input) {
        // Strip non-alphanumeric, lowercase
        String cleaned  = input.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
        String reversed = new StringBuilder(cleaned).reverse().toString();
        return cleaned.equals(reversed);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a string: ");
        String input = sc.nextLine();

        if (isPalindrome(input)) {
            System.out.println("\"" + input + "\" IS a palindrome.");
        } else {
            System.out.println("\"" + input + "\" is NOT a palindrome.");
        }
        sc.close();
    }
}
