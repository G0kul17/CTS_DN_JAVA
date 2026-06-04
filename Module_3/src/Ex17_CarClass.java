/**
 * Exercise 17: Class and Object Creation
 * Objective: Understand classes and objects in Java.
 *
 * Demonstrates: attributes, constructor, methods, object instantiation.
 *
 * Compile: javac Ex17_CarClass.java
 * Run:     java  Ex17_CarClass
 */
public class Ex17_CarClass {

    // ── Inner class Car ──────────────────────────────────────
    static class Car {
        String make;
        String model;
        int    year;
        double price;

        Car(String make, String model, int year, double price) {
            this.make  = make;
            this.model = model;
            this.year  = year;
            this.price = price;
        }

        void displayDetails() {
            System.out.println("+------------------------------+");
            System.out.println("  Make  : " + make);
            System.out.println("  Model : " + model);
            System.out.println("  Year  : " + year);
            System.out.printf( "  Price : $%.2f%n", price);
            System.out.println("+------------------------------+");
        }

        void start() {
            System.out.println(year + " " + make + " " + model + " started. Vroom!");
        }
    }

    public static void main(String[] args) {
        Car car1 = new Car("Toyota", "Camry",   2022, 24_000.00);
        Car car2 = new Car("Honda",  "Civic",   2023, 22_500.00);
        Car car3 = new Car("Tesla",  "Model 3", 2024, 40_000.00);

        System.out.println("=== Car Details ===\n");
        car1.displayDetails();
        car2.displayDetails();
        car3.displayDetails();

        System.out.println("\n=== Starting Cars ===");
        car1.start();
        car2.start();
        car3.start();
    }
}
