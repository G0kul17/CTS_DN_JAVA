import java.util.*;
import java.util.stream.*;

/**
 * Exercise 29: Records (Java 16+)
 * Objective: Use the record keyword for immutable data.
 *
 * Records auto-generate: constructor, accessors, equals, hashCode, toString.
 *
 * Compile: javac Ex29_Records.java
 * Run:     java  Ex29_Records
 */
public class Ex29_Records {

    /** Immutable Person record with validation */
    record Person(String name, int age) {
        // Compact constructor for validation
        Person {
            if (name == null || name.isBlank()) throw new IllegalArgumentException("Name is blank");
            if (age < 0)                         throw new IllegalArgumentException("Age is negative");
        }
        // Custom method
        String greeting() { return "Hi, I'm " + name + ", age " + age + "."; }
    }

    /** Point record with a computed method */
    record Point(double x, double y) {
        double distanceTo(Point other) {
            return Math.sqrt(Math.pow(x - other.x, 2) + Math.pow(y - other.y, 2));
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Records Demo ===\n");

        Person p1 = new Person("Alice",   28);
        Person p2 = new Person("Bob",     19);
        Person p3 = new Person("Charlie", 17);
        Person p4 = new Person("Diana",   35);

        // Auto-generated toString
        System.out.println(p1);
        System.out.println(p1.greeting());

        // Accessors (no 'get' prefix)
        System.out.println("Name: " + p1.name() + ", Age: " + p1.age());

        // equals auto-generated
        System.out.println("p1.equals(new Person(\"Alice\",28)): " + p1.equals(new Person("Alice", 28)));

        // List + Stream filter
        List<Person> people = List.of(p1, p2, p3, p4);
        System.out.println("\nAdults (age >= 18):");
        people.stream()
            .filter(p -> p.age() >= 18)
            .sorted(Comparator.comparing(Person::name))
            .forEach(p -> System.out.println("  " + p));

        System.out.println("\nAverage age: " +
            people.stream().mapToInt(Person::age).average().orElse(0));

        // Point record
        Point a = new Point(0, 0), b = new Point(3, 4);
        System.out.printf("%nDistance %s → %s = %.2f%n", a, b, a.distanceTo(b));
    }
}
