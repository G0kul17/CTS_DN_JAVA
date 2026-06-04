/**
 * Exercise 37: Bytecode Inspection with javap
 * Objective: Explore compiled .class files.
 *
 * STEPS:
 *   1. Compile this file:  javac Ex37_BytecodeInspection.java
 *   2. Inspect bytecode:   javap -c  Ex37_BytecodeInspection
 *      Verbose output:     javap -v  Ex37_BytecodeInspection
 *      Constants only:     javap -p  Ex37_BytecodeInspection
 *
 * javap output explanation:
 *   - 'Code:' block shows JVM opcodes (e.g., getstatic, ldc, invokevirtual)
 *   - 'aload_0' = push 'this' reference onto operand stack
 *   - 'invokevirtual' = call an instance method
 *   - 'return'/'ireturn' = return from method
 *
 * Run:  java Ex37_BytecodeInspection  (then inspect with javap)
 */
public class Ex37_BytecodeInspection {

    // Fields that will appear in constant pool
    static final String GREETING = "Hello from bytecode!";
    private int counter = 0;

    /** Simple method — inspect its bytecode with javap -c */
    public int add(int a, int b) {
        return a + b;
    }

    /** Method with a loop — generates branch instructions in bytecode */
    public int sumUpTo(int n) {
        int sum = 0;
        for (int i = 1; i <= n; i++) {
            sum += i;
        }
        return sum;
    }

    /** String concatenation — uses StringBuilder in bytecode */
    public String greet(String name) {
        return "Hello, " + name + "!";
    }

    public static void main(String[] args) {
        Ex37_BytecodeInspection obj = new Ex37_BytecodeInspection();
        System.out.println(GREETING);
        System.out.println("add(3, 4)   = " + obj.add(3, 4));
        System.out.println("sumUpTo(10) = " + obj.sumUpTo(10));
        System.out.println(obj.greet("World"));
        System.out.println("\nNow run: javap -c Ex37_BytecodeInspection");
    }
}
