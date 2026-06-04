import java.util.*;
import java.util.stream.*;

/**
 * Exercise 28: Stream API
 * Objective: Process collections using streams.
 *
 * Demonstrates: filter, map, reduce, collect, sorted, distinct,
 * count, average, anyMatch, partitioningBy.
 *
 * Compile: javac Ex28_StreamAPI.java
 * Run:     java  Ex28_StreamAPI
 */
public class Ex28_StreamAPI {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 4, 6);
        System.out.println("Original list : " + numbers);

        // Filter even numbers
        List<Integer> evens = numbers.stream()
            .filter(n -> n % 2 == 0)
            .collect(Collectors.toList());
        System.out.println("Even numbers  : " + evens);

        // Distinct + sorted
        List<Integer> ds = numbers.stream().distinct().sorted().collect(Collectors.toList());
        System.out.println("Distinct+sorted: " + ds);

        // Map: square each
        List<Integer> squares = numbers.stream().distinct().map(n -> n * n).collect(Collectors.toList());
        System.out.println("Squares       : " + squares);

        // Reduce: sum
        int sum = numbers.stream().reduce(0, Integer::sum);
        System.out.println("Sum           : " + sum);

        // Count evens
        long evenCount = numbers.stream().filter(n -> n % 2 == 0).count();
        System.out.println("Even count    : " + evenCount);

        // Average
        OptionalDouble avg = numbers.stream().mapToInt(Integer::intValue).average();
        avg.ifPresent(a -> System.out.printf("Average       : %.2f%n", a));

        // Match
        System.out.println("Any > 9?      : " + numbers.stream().anyMatch(n -> n > 9));
        System.out.println("All > 0?      : " + numbers.stream().allMatch(n -> n > 0));
        System.out.println("None < 0?     : " + numbers.stream().noneMatch(n -> n < 0));

        // String stream with joining
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "Anna", "Brian");
        String aNames = names.stream().filter(n -> n.startsWith("A"))
            .sorted().collect(Collectors.joining(", "));
        System.out.println("\nNames with A  : " + aNames);

        // PartitioningBy
        Map<Boolean, List<Integer>> parts = numbers.stream().distinct()
            .collect(Collectors.partitioningBy(n -> n % 2 == 0));
        System.out.println("Even group    : " + parts.get(true));
        System.out.println("Odd  group    : " + parts.get(false));
    }
}
