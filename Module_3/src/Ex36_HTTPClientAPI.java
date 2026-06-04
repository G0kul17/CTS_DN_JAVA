import java.net.URI;
import java.net.http.*;
import java.time.Duration;

/**
 * Exercise 36: HTTP Client API (Java 11+)
 * Objective: Make HTTP requests from Java.
 *
 * Fetches data from a public REST API (GitHub API — no auth needed).
 *
 * Compile: javac Ex36_HTTPClientAPI.java
 * Run:     java  Ex36_HTTPClientAPI
 */
public class Ex36_HTTPClientAPI {

    public static void main(String[] args) throws Exception {
        System.out.println("=== Java 11 HttpClient Demo ===\n");

        HttpClient client = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .connectTimeout(Duration.ofSeconds(10))
            .build();

        // ── Request 1: GitHub API — public repo info ─────────
        HttpRequest req1 = HttpRequest.newBuilder()
            .uri(URI.create("https://api.github.com/repos/openjdk/jdk"))
            .header("Accept", "application/vnd.github+json")
            .GET()
            .build();

        System.out.println("Sending GET → " + req1.uri());
        HttpResponse<String> res1 = client.send(req1, HttpResponse.BodyHandlers.ofString());

        System.out.println("Status : " + res1.statusCode());
        System.out.println("Headers: Content-Type = " + res1.headers().firstValue("content-type").orElse("N/A"));

        // Print first 500 chars of JSON body
        String body = res1.body();
        System.out.println("Body (first 500 chars):");
        System.out.println(body.substring(0, Math.min(500, body.length())));
        System.out.println("...");

        // ── Request 2: Simple public API ─────────────────────
        HttpRequest req2 = HttpRequest.newBuilder()
            .uri(URI.create("https://httpbin.org/get?source=java"))
            .GET()
            .build();

        System.out.println("\nSending GET → " + req2.uri());
        HttpResponse<String> res2 = client.send(req2, HttpResponse.BodyHandlers.ofString());
        System.out.println("Status : " + res2.statusCode());
        System.out.println("Response:\n" + res2.body());
    }
}
