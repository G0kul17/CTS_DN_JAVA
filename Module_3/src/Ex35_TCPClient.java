import java.io.*;
import java.net.*;

/**
 * Exercise 35: TCP Client-Server Chat — CLIENT
 * Objective: Use Java sockets for TCP communication.
 *
 * Start Ex35_TCPServer first, then run this in a second terminal.
 * Type messages and press Enter to send. Type 'bye' to disconnect.
 *
 * Compile: javac Ex35_TCPClient.java
 * Run:     java  Ex35_TCPClient
 */
public class Ex35_TCPClient {
    static final String HOST = "localhost";
    static final int    PORT = 5000;

    public static void main(String[] args) throws IOException {
        System.out.println("=== TCP Client connecting to " + HOST + ":" + PORT + " ===");

        try (Socket socket = new Socket(HOST, PORT)) {
            System.out.println("Connected to server.");

            BufferedReader  in      = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter     out     = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            BufferedReader  console = new BufferedReader(new InputStreamReader(System.in));

            // Thread to receive server messages
            Thread receiver = new Thread(() -> {
                try {
                    String msg;
                    while ((msg = in.readLine()) != null) {
                        System.out.println("[Server]: " + msg);
                        if (msg.equalsIgnoreCase("bye")) break;
                    }
                } catch (IOException ignored) {}
                System.out.println("Server disconnected.");
            });
            receiver.start();

            System.out.println("Type messages (type 'bye' to quit):");
            String line;
            while ((line = console.readLine()) != null) {
                out.println(line);
                if (line.equalsIgnoreCase("bye")) break;
            }

            receiver.interrupt();
            System.out.println("Client closed.");
        }
    }
}
