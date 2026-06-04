import java.util.Scanner;

/**
 * Exercise 14: Array Sum and Average
 * Objective: Work with arrays and perform calculations.
 *
 * Compile: javac Ex14_ArraySumAverage.java
 * Run:     java  Ex14_ArraySumAverage
 */
public class Ex14_ArraySumAverage {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("How many elements? ");
        int n = sc.nextInt();

        if (n <= 0) {
            System.out.println("Must be a positive count.");
            sc.close();
            return;
        }

        double[] arr = new double[n];
        System.out.println("Enter " + n + " numbers:");
        for (int i = 0; i < n; i++) {
            System.out.print("  [" + (i + 1) + "]: ");
            arr[i] = sc.nextDouble();
        }

        double sum = 0;
        double max = arr[0], min = arr[0];
        for (double v : arr) {
            sum += v;
            if (v > max) max = v;
            if (v < min) min = v;
        }

        System.out.printf("%nSum     = %.2f%n", sum);
        System.out.printf("Average = %.2f%n", sum / n);
        System.out.printf("Max     = %.2f%n", max);
        System.out.printf("Min     = %.2f%n", min);
        sc.close();
    }
}
