/**
 * Exercise 38: Decompile a Class File
 * Objective: Reverse engineer compiled Java bytecode.
 *
 * STEPS:
 *   1. Compile: javac Ex38_DecompileClass.java
 *   2. Decompile using one of:
 *      a) CFR (command-line): java -jar cfr.jar Ex38_DecompileClass.class
 *         Download: https://github.com/leibnitz27/cfr/releases
 *      b) Procyon: java -jar procyon-decompiler.jar Ex38_DecompileClass.class
 *      c) JD-GUI (GUI tool): Open the .class file in JD-GUI
 *         Download: https://github.com/java-decompiler/jd-gui/releases
 *      d) IntelliJ IDEA: Just open the .class file — auto-decompiles!
 *
 * WHAT TO OBSERVE in decompiled output:
 *   - String switch compiled as hashCode() + equals() calls
 *   - Enhanced for-loop becomes Iterator-based code
 *   - Lambda expressions may appear as anonymous classes or invokedynamic
 *   - Synthetic fields/methods added by compiler for inner classes
 *
 * Run: java Ex38_DecompileClass
 */
public class Ex38_DecompileClass {

    // Inner class — observe synthetic accessor in decompiled output
    private int secret = 42;

    static class Inner {
        void display(Ex38_DecompileClass outer) {
            System.out.println("Secret: " + outer.secret); // synthetic access
        }
    }

    /** Switch on String — decompiles to hashCode + equals chain */
    static String getDayType(String day) {
        return switch (day.toLowerCase()) {
            case "saturday", "sunday" -> "Weekend";
            case "monday", "tuesday", "wednesday", "thursday", "friday" -> "Weekday";
            default -> "Unknown";
        };
    }

    /** Enhanced for-loop — decompiles to iterator */
    static void printItems(String[] items) {
        for (String item : items) {
            System.out.println("  - " + item);
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Decompile Demo ===");
        System.out.println("Monday is a: " + getDayType("Monday"));
        System.out.println("Saturday is a: " + getDayType("Saturday"));
        printItems(new String[]{"Java", "Python", "C++"});
        new Inner().display(new Ex38_DecompileClass());
        System.out.println("\nCompile and decompile this class — see the instructions in the source.");
    }
}
