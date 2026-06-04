import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Exercise 24: ArrayList Example
 * Objective: Use dynamic arrays.
 *
 * Commands: ADD <name> | REMOVE <name> | LIST | SORT | QUIT
 *
 * Compile: javac Ex24_ArrayListExample.java
 * Run:     java  Ex24_ArrayListExample
 */
public class Ex24_ArrayListExample {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<String> names = new ArrayList<>();

        System.out.println("=== Student Name Manager ===");
        System.out.println("Commands: ADD <name> | REMOVE <name> | LIST | SORT | QUIT\n");

        while (true) {
            System.out.print("> ");
            String input = sc.nextLine().trim();
            if (input.isEmpty()) continue;

            String[] parts = input.split("\\s+", 2);
            String cmd = parts[0].toUpperCase();

            switch (cmd) {
                case "ADD" -> {
                    if (parts.length < 2) { System.out.println("Usage: ADD <name>"); break; }
                    names.add(parts[1]);
                    System.out.println("Added: " + parts[1] + "  (total: " + names.size() + ")");
                }
                case "REMOVE" -> {
                    if (parts.length < 2) { System.out.println("Usage: REMOVE <name>"); break; }
                    System.out.println(names.remove(parts[1]) ? "Removed: " + parts[1] : "Not found: " + parts[1]);
                }
                case "LIST" -> {
                    if (names.isEmpty()) { System.out.println("List is empty."); break; }
                    for (int i = 0; i < names.size(); i++)
                        System.out.println("  " + (i + 1) + ". " + names.get(i));
                }
                case "SORT" -> { Collections.sort(names); System.out.println("Sorted alphabetically."); }
                case "QUIT", "EXIT" -> { System.out.println("Bye!"); sc.close(); return; }
                default -> System.out.println("Unknown: " + cmd);
            }
        }
    }
}
