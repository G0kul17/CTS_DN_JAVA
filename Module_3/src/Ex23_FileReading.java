import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Exercise 23: File Reading
 * Objective: Read data from a file.
 *
 * Reads and displays the contents of output.txt.
 * Run Ex22_FileWriting.java first to create the file.
 *
 * Compile: javac Ex23_FileReading.java
 * Run:     java  Ex23_FileReading
 */
public class Ex23_FileReading {
    static final String FILE_NAME = "output.txt";

    public static void main(String[] args) {
        System.out.println("=== File Reading Demo ===");
        System.out.println("Reading from: " + FILE_NAME + "\n");

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            int lineNum = 1;
            while ((line = reader.readLine()) != null) {
                System.out.printf("[%3d] %s%n", lineNum++, line);
            }
            System.out.println("\n✔ End of file. Lines read: " + (lineNum - 1));
        } catch (FileNotFoundException e) {
            System.out.println("File not found: '" + FILE_NAME + "'");
            System.out.println("Run Ex22_FileWriting first to create it.");
        } catch (IOException e) {
            System.out.println("Read error: " + e.getMessage());
        }
    }
}
