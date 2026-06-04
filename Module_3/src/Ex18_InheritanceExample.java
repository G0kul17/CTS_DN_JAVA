/**
 * Exercise 18: Inheritance Example
 * Objective: Implement inheritance in Java.
 *
 * Demonstrates: base class, subclass, @Override, super(), polymorphism.
 *
 * Compile: javac Ex18_InheritanceExample.java
 * Run:     java  Ex18_InheritanceExample
 */
public class Ex18_InheritanceExample {

    // ── Base class ──────────────────────────────────────────
    static class Animal {
        String name;

        Animal(String name) { this.name = name; }

        void makeSound() {
            System.out.println(name + " makes a generic sound.");
        }

        void breathe() {
            System.out.println(name + " breathes air.  [inherited]");
        }
    }

    // ── Subclass Dog ────────────────────────────────────────
    static class Dog extends Animal {
        String breed;

        Dog(String name, String breed) {
            super(name);       // Call parent constructor
            this.breed = breed;
        }

        @Override
        void makeSound() {
            System.out.println(name + " (" + breed + ") says: Bark! Bark!");
        }

        void fetch() { System.out.println(name + " fetches the ball!"); }
    }

    // ── Subclass Cat ────────────────────────────────────────
    static class Cat extends Animal {
        Cat(String name) { super(name); }

        @Override
        void makeSound() { System.out.println(name + " says: Meow~"); }
    }

    public static void main(String[] args) {
        System.out.println("=== Inheritance Demo ===\n");

        Animal generic = new Animal("Generic Animal");
        Dog    dog     = new Dog("Rex", "German Shepherd");
        Cat    cat     = new Cat("Whiskers");

        generic.makeSound();
        generic.breathe();

        System.out.println();
        dog.makeSound();  // overridden
        dog.breathe();    // inherited
        dog.fetch();      // Dog-specific

        System.out.println();
        cat.makeSound();  // overridden
        cat.breathe();    // inherited

        // Polymorphism via parent reference
        System.out.println("\n=== Polymorphism ===");
        Animal poly = new Dog("Buddy", "Labrador");
        poly.makeSound();   // calls Dog's makeSound at runtime
    }
}
