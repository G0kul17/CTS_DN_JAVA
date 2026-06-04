import java.io.*;
import java.net.*;

/**
 * Exercise 35: TCP Client-Server Chat — SERVER
 * Objective: Use Java sockets for TCP communication.
 *
 * Start this FIRST in one terminal, then start Ex35_TCPClient.
 * Type messages in either terminal. Type 'bye' to disconnect.
 *
 * Compile: javac Ex35_TCPServer.java
 * Run:     java  Ex35_TCPServer
 */
public class Ex35_TCPServer {
    static final int PORT = 5000;

    public static void main(String[] args) throws IOException {
        System.out.println("=== TCP Server listening on port " + PORT + " ===");

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            Socket client = serverSocket.accept();
            System.out.println("Client connected: " + client.getInetAddress());

            BufferedReader  in      = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter     out     = new PrintWriter(new OutputStreamWriter(client.getOutputStream()), true);
            BufferedReader  console = new BufferedReader(new InputStreamReader(System.in));

            // Thread to read messages from client
            Thread reader = new Thread(() -> {
                try {
                    String msg;
                    while ((msg = in.readLine()) != null) {
                        System.out.println("[Client]: " + msg);
                        if (msg.equalsIgnoreCase("bye")) break;
                    }
                } catch (IOException ignored) {}
                System.out.println("Client disconnected.");
            });
            reader.start();

            // Main thread sends messages to client
            System.out.println("Type messages to send to client (type 'bye' to quit):");
            String line;
            while ((line = console.readLine()) != null) {
                out.println(line);
                if (line.equalsIgnoreCase("bye")) break;
            }

            reader.interrupt();
            System.out.println("Server closed.");
        }
    }
}
