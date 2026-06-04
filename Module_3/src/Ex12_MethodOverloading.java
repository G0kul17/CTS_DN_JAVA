/**
 * Exercise 12: Method Overloading
 * Objective: Understand method overloading in Java.
 *
 * Same method name, different parameter signatures.
 * Java resolves the correct version at compile time.
 *
 * Compile: javac Ex12_MethodOverloading.java
 * Run:     java  Ex12_MethodOverloading
 */
public class Ex12_MethodOverloading {

    /** Overload 1: two integers */
    static int add(int a, int b) {
        System.out.println("  → calling add(int, int)");
        return a + b;
    }

    /** Overload 2: two doubles */
    static double add(double a, double b) {
        System.out.println("  → calling add(double, double)");
        return a + b;
    }

    /** Overload 3: three integers */
    static int add(int a, int b, int c) {
        System.out.println("  → calling add(int, int, int)");
        return a + b + c;
    }

    public static void main(String[] args) {
        System.out.println("=== Method Overloading Demo ===\n");

        int    r1 = add(10, 20);
        System.out.println("add(10, 20)        = " + r1 + "\n");

        double r2 = add(3.14, 2.86);
        System.out.printf("add(3.14, 2.86)    = %.2f%n%n", r2);

        int    r3 = add(1, 2, 3);
        System.out.println("add(1, 2, 3)       = " + r3);
    }
}
