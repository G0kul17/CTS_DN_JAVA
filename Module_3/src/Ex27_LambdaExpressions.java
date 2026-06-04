import java.util.*;
import java.util.function.*;

/**
 * Exercise 27: Lambda Expressions
 * Objective: Use functional programming features.
 *
 * Demonstrates: Comparator, Predicate, Function, Consumer, Supplier,
 * method references, forEach.
 *
 * Compile: javac Ex27_LambdaExpressions.java
 * Run:     java  Ex27_LambdaExpressions
 */
public class Ex27_LambdaExpressions {
    public static void main(String[] args) {
        System.out.println("=== Lambda Expressions Demo ===\n");

        List<String> cities = new ArrayList<>(
            Arrays.asList("Chicago", "New York", "Los Angeles", "Houston", "Phoenix")
        );

        // 1. Sort with Comparator lambda
        System.out.println("Original : " + cities);
        Collections.sort(cities, (a, b) -> a.compareTo(b));
        System.out.println("A→Z sort : " + cities);
        cities.sort((a, b) -> b.compareTo(a));
        System.out.println("Z→A sort : " + cities);
        cities.sort(Comparator.comparingInt(String::length));
        System.out.println("By length: " + cities);

        // 2. Predicate
        System.out.println("\n--- Predicate (starts with N?) ---");
        Predicate<String> startsN = s -> s.startsWith("N");
        cities.forEach(c -> System.out.println(c + " → " + startsN.test(c)));

        // 3. Function (transform)
        System.out.println("\n--- Function (toUpperCase) ---");
        Function<String, String> upper = String::toUpperCase;
        cities.stream().map(upper).forEach(System.out::println);

        // 4. Consumer
        System.out.println("\n--- Consumer ---");
        Consumer<String> printer = c -> System.out.println("City: " + c);
        cities.forEach(printer);

        // 5. Supplier
        System.out.println("\n--- Supplier ---");
        Supplier<List<String>> newList = ArrayList::new;
        List<String> fresh = newList.get();
        fresh.add("Boston");
        System.out.println("Supplier created list: " + fresh);
    }
}
