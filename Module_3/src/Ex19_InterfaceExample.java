/**
 * Exercise 19: Interface Implementation
 * Objective: Use interfaces in Java.
 *
 * Demonstrates: interface definition, multiple implementations,
 * default method, polymorphism via interface reference.
 *
 * Compile: javac Ex19_InterfaceExample.java
 * Run:     java  Ex19_InterfaceExample
 */
public class Ex19_InterfaceExample {

    // ── Interface ────────────────────────────────────────────
    interface Playable {
        void play();                 // abstract — must implement

        default void stop() {        // default — may override
            System.out.println("  Stopping playback.");
        }
    }

    // ── Guitar ───────────────────────────────────────────────
    static class Guitar implements Playable {
        @Override public void play() {
            System.out.println("Guitar: Strum strum strum!");
        }
    }

    // ── Piano ────────────────────────────────────────────────
    static class Piano implements Playable {
        @Override public void play() {
            System.out.println("Piano : Do Re Mi Fa Sol!");
        }
        @Override public void stop() {
            System.out.println("Piano : Sustain pedal released.");
        }
    }

    // ── Drums ────────────────────────────────────────────────
    static class Drums implements Playable {
        @Override public void play() {
            System.out.println("Drums : Boom chick boom chick!");
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Interface Demo ===\n");

        Playable[] instruments = { new Guitar(), new Piano(), new Drums() };
        for (Playable inst : instruments) {
            inst.play();
            inst.stop();
            System.out.println();
        }
    }
}
