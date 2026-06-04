import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Exercise 22: File Writing
 * Objective: Write data to a file.
 *
 * Writes user input line-by-line to output.txt.
 * Type DONE to finish.
 *
 * Compile: javac Ex22_FileWriting.java
 * Run:     java  Ex22_FileWriting
 */
public class Ex22_FileWriting {
    static final String FILE_NAME = "output.txt";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("=== File Writing Demo ===");
        System.out.println("Enter lines to write to '" + FILE_NAME + "'. Type DONE to finish.\n");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            int lineNum = 1;
            while (true) {
                System.out.print("Line " + lineNum + ": ");
                String line = sc.nextLine();
                if (line.equalsIgnoreCase("DONE")) break;
                writer.write(line);
                writer.newLine();
                lineNum++;
            }
            System.out.println("\n✔ Data successfully written to '" + FILE_NAME + "'.");
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        } finally {
            sc.close();
        }
    }
}
