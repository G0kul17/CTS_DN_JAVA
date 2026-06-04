import java.lang.reflect.*;

/**
 * Exercise 39: Reflection in Java
 * Objective: Load a class and invoke methods dynamically.
 *
 * Demonstrates: Class.forName(), getDeclaredMethods(), getDeclaredFields(),
 * invoke(), newInstance(), setAccessible(true).
 *
 * Compile: javac Ex39_ReflectionAPI.java
 * Run:     java  Ex39_ReflectionAPI
 */
public class Ex39_ReflectionAPI {

    // Target class to reflect on (inner static class)
    static class Calculator {
        private int memory = 0;

        public int add(int a, int b)      { return a + b; }
        public int subtract(int a, int b) { return a - b; }
        public int multiply(int a, int b) { return a * b; }
        private double divide(int a, int b) { return (double) a / b; }

        public void store(int value) { this.memory = value; }
        public int  recall()         { return memory; }

        @Override public String toString() { return "Calculator[memory=" + memory + "]"; }
    }

    public static void main(String[] args) throws Exception {
        System.out.println("=== Java Reflection API Demo ===\n");

        // 1. Load the class via reflection
        Class<?> clazz = Calculator.class;
        System.out.println("Class name: " + clazz.getName());
        System.out.println("Simple name: " + clazz.getSimpleName());

        // 2. Inspect declared fields
        System.out.println("\n--- Fields ---");
        for (Field f : clazz.getDeclaredFields()) {
            System.out.println("  " + f.getType().getSimpleName() + " " + f.getName()
                + "  [" + Modifier.toString(f.getModifiers()) + "]");
        }

        // 3. Inspect declared methods
        System.out.println("\n--- Methods ---");
        for (Method m : clazz.getDeclaredMethods()) {
            System.out.println("  " + m.getReturnType().getSimpleName() + " "
                + m.getName() + "(" + getParamTypes(m) + ")  ["
                + Modifier.toString(m.getModifiers()) + "]");
        }

        // 4. Create instance reflectively
        Calculator calc = (Calculator) clazz.getDeclaredConstructor().newInstance();
        System.out.println("\nCreated: " + calc);

        // 5. Invoke public method
        Method addMethod = clazz.getMethod("add", int.class, int.class);
        int    result    = (int) addMethod.invoke(calc, 15, 7);
        System.out.println("add(15, 7) via reflection = " + result);

        // 6. Access and invoke private method
        Method divMethod = clazz.getDeclaredMethod("divide", int.class, int.class);
        divMethod.setAccessible(true);   // bypass access check
        double divResult = (double) divMethod.invoke(calc, 22, 7);
        System.out.printf("divide(22,7) via reflection = %.4f%n", divResult);

        // 7. Access private field directly
        Field memField = clazz.getDeclaredField("memory");
        memField.setAccessible(true);
        memField.set(calc, 99);
        System.out.println("Set private memory field to 99. recall() = " + calc.recall());
    }

    static String getParamTypes(Method m) {
        StringBuilder sb = new StringBuilder();
        for (Class<?> p : m.getParameterTypes()) {
            if (sb.length() > 0) sb.append(", ");
            sb.append(p.getSimpleName());
        }
        return sb.toString();
    }
}
