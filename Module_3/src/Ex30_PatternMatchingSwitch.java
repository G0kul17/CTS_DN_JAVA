/**
 * Exercise 30: Pattern Matching for switch (Java 21)
 * Objective: Simplify type-based conditional logic.
 *
 * Compile: javac Ex30_PatternMatchingSwitch.java
 * Run:     java  Ex30_PatternMatchingSwitch
 */
public class Ex30_PatternMatchingSwitch {

    /** Describe any Object using a pattern-matching switch expression */
    static String describe(Object obj) {
        return switch (obj) {
            case null              -> "null value";
            case Integer i         -> "Integer: " + i + "  (squared = " + (i * i) + ")";
            case Long l            -> "Long: " + l;
            case Double d          -> String.format("Double: %.4f", d);
            case String s when s.isEmpty() -> "Empty String";
            case String s          -> "String[" + s.length() + "]: \"" + s + "\"";
            case int[]  arr        -> "int[] of length " + arr.length;
            case Boolean b         -> "Boolean: " + b;
            default                -> "Unknown type: " + obj.getClass().getSimpleName();
        };
    }

    public static void main(String[] args) {
        System.out.println("=== Pattern Matching Switch (Java 21) ===\n");

        Object[] samples = {
            42, 3.14159, "Hello, World!", "", true, 100L, new int[]{1,2,3}, null
        };

        for (Object s : samples) {
            System.out.println(describe(s));
        }
    }
}
