/**
 * Exercise 8: Operator Precedence
 * Objective: Explore how Java evaluates expressions.
 *
 * Java precedence order (high to low):
 *   ()  →  ++ --  →  * / %  →  + -  →  << >>  →  & ^ |  →  && ||  →  ?:
 *
 * Compile: javac Ex08_OperatorPrecedence.java
 * Run:     java  Ex08_OperatorPrecedence
 */
public class Ex08_OperatorPrecedence {
    public static void main(String[] args) {
        System.out.println("=== Operator Precedence in Java ===\n");

        // * before +
        int r1 = 10 + 5 * 2;
        System.out.println("10 + 5 * 2      = " + r1 + "  (* before +: 10 + 10)");

        // Parentheses override
        int r2 = (10 + 5) * 2;
        System.out.println("(10 + 5) * 2    = " + r2 + "  (parens first: 15*2)");

        // Mixed arithmetic
        int r3 = 100 / 5 + 3 * 4 - 2;
        System.out.println("100/5 + 3*4 - 2 = " + r3 + "  (20 + 12 - 2)");

        // Modulus
        int r4 = 17 % 5 + 2 * 3;
        System.out.println("17 % 5 + 2*3    = " + r4 + "  (2 + 6)");

        // Pre vs post increment
        int a = 5;
        int b = a++ + ++a;    // a++ uses 5 (a becomes 6), ++a makes a=7 uses 7
        System.out.println("\na=5; b = a++ + ++a  =>  b=" + b + ", a=" + a
                + "  (5 + 7 = 12)");

        // Left shift
        int r5 = 4 + 3 << 1;  // + first: (7)<<1 = 14
        System.out.println("4 + 3 << 1      = " + r5 + "  (7<<1 = 14)");

        // Ternary
        int max = (10 > 7) ? 10 : 7;
        System.out.println("(10 > 7) ? 10 : 7 = " + max);

        // Compound assignment
        int c = 10;
        c += 5 * 2;   // c = c + (5*2) = 20
        System.out.println("c=10; c += 5*2  =>  c=" + c);
    }
}
