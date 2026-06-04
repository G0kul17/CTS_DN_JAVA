/**
 * Exercise 7: Type Casting Example
 * Objective: Practice explicit and implicit type casting.
 *
 * Compile: javac Ex07_TypeCasting.java
 * Run:     java  Ex07_TypeCasting
 */
public class Ex07_TypeCasting {
    public static void main(String[] args) {
        System.out.println("=== Type Casting in Java ===\n");

        // --- Narrowing (Explicit): double → int ---
        double d = 9.99;
        int    i = (int) d;   // Truncates decimal — NOT rounded
        System.out.println("Narrowing (double → int):");
        System.out.println("  double  9.99  → int " + i + "  (decimal truncated)");

        // --- Widening (Implicit): int → double ---
        int    x = 42;
        double y = x;          // No explicit cast needed
        System.out.println("\nWidening (int → double):");
        System.out.println("  int " + x + " → double " + y);

        // --- int → char ---
        int  ascii    = 65;
        char ch       = (char) ascii;
        System.out.println("\nint 65 → char: '" + ch + "'");
        System.out.println("char 'Z' → int: " + (int) 'Z');

        // --- long → int (possible data loss) ---
        long bigNum   = 1_000_000_000_000L;
        int  narrowed = (int) bigNum;
        System.out.println("\nNarrowing (long → int) — data loss:");
        System.out.println("  long " + bigNum + " → int " + narrowed);

        // --- double → float ---
        double precise = 3.141_592_653_589;
        float  approx  = (float) precise;
        System.out.println("\ndouble " + precise + " → float " + approx);
    }
}
