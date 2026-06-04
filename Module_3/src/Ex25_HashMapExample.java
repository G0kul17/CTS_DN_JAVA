import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Exercise 25: HashMap Example
 * Objective: Use key-value pairs.
 *
 * Maps student IDs (Integer) to names (String).
 * Commands: PUT <id> <name> | GET <id> | LIST | REMOVE <id> | QUIT
 *
 * Compile: javac Ex25_HashMapExample.java
 * Run:     java  Ex25_HashMapExample
 */
public class Ex25_HashMapExample {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        HashMap<Integer, String> map = new HashMap<>();

        // Pre-populate
        map.put(1001, "Alice Johnson");
        map.put(1002, "Bob Smith");
        map.put(1003, "Charlie Lee");

        System.out.println("=== Student ID Registry ===");
        System.out.println("Commands: PUT <id> <name> | GET <id> | LIST | REMOVE <id> | QUIT\n");

        while (true) {
            System.out.print("> ");
            String input = sc.nextLine().trim();
            if (input.isEmpty()) continue;

            String[] parts = input.split("\\s+", 3);
            String cmd = parts[0].toUpperCase();

            try {
                switch (cmd) {
                    case "PUT" -> {
                        int id = Integer.parseInt(parts[1]); String name = parts[2];
                        map.put(id, name); System.out.println("Stored: " + id + " → " + name);
                    }
                    case "GET" -> {
                        int id = Integer.parseInt(parts[1]);
                        String n = map.get(id);
                        System.out.println(n != null ? id + " → " + n : "ID " + id + " not found.");
                    }
                    case "LIST" -> {
                        if (map.isEmpty()) { System.out.println("Empty."); break; }
                        for (Map.Entry<Integer, String> e : map.entrySet())
                            System.out.println("  " + e.getKey() + " → " + e.getValue());
                    }
                    case "REMOVE" -> {
                        int id = Integer.parseInt(parts[1]);
                        System.out.println(map.remove(id) != null ? "Removed ID " + id : "Not found.");
                    }
                    case "QUIT", "EXIT" -> { System.out.println("Bye!"); sc.close(); return; }
                    default -> System.out.println("Unknown: " + cmd);
                }
            } catch (NumberFormatException e) { System.out.println("ID must be an integer."); }
              catch (ArrayIndexOutOfBoundsException e) { System.out.println("Missing argument."); }
        }
    }
}
