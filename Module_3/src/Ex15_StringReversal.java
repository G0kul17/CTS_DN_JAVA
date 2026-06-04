import java.util.Scanner;

/**
 * Exercise 15: String Reversal
 * Objective: Manipulate strings.
 *
 * Demonstrates two reversal methods: StringBuilder and char-swap loop.
 *
 * Compile: javac Ex15_StringReversal.java
 * Run:     java  Ex15_StringReversal
 */
public class Ex15_StringReversal {

    // Method 1: Using StringBuilder.reverse()
    static String reverseWithStringBuilder(String s) {
        return new StringBuilder(s).reverse().toString();
    }

    // Method 2: Two-pointer swap loop
    static String reverseWithLoop(String s) {
        char[] chars = s.toCharArray();
        int left = 0, right = chars.length - 1;
        while (left < right) {
            char tmp     = chars[left];
            chars[left]  = chars[right];
            chars[right] = tmp;
            left++; right--;
        }
        return new String(chars);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a string: ");
        String input = sc.nextLine();

        System.out.println("\nOriginal                : " + input);
        System.out.println("Reversed (StringBuilder): " + reverseWithStringBuilder(input));
        System.out.println("Reversed (Loop)         : " + reverseWithLoop(input));
        sc.close();
    }
}
