import java.util.Scanner;

/**
 * Exercise 9: Grade Calculator
 * Objective: Use conditional statements to determine grades.
 *
 * Grade scale: 90-100=A, 80-89=B, 70-79=C, 60-69=D, <60=F
 *
 * Compile: javac Ex09_GradeCalculator.java
 * Run:     java  Ex09_GradeCalculator
 */
public class Ex09_GradeCalculator {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter marks (0-100): ");
        int marks = sc.nextInt();

        if (marks < 0 || marks > 100) {
            System.out.println("Invalid marks. Enter a value between 0 and 100.");
            sc.close();
            return;
        }

        String grade, remark;

        if (marks >= 90)      { grade = "A"; remark = "Outstanding!"; }
        else if (marks >= 80) { grade = "B"; remark = "Very Good!"; }
        else if (marks >= 70) { grade = "C"; remark = "Good."; }
        else if (marks >= 60) { grade = "D"; remark = "Satisfactory."; }
        else                  { grade = "F"; remark = "Needs Improvement."; }

        System.out.println("Marks : " + marks + " / 100");
        System.out.println("Grade : " + grade);
        System.out.println("Remark: " + remark);
        sc.close();
    }
}
