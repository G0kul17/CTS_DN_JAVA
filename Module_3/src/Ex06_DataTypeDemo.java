/**
 * Exercise 6: Data Type Demonstration
 * Objective: Understand Java's primitive data types.
 *
 * Compile: javac Ex06_DataTypeDemo.java
 * Run:     java  Ex06_DataTypeDemo
 */
public class Ex06_DataTypeDemo {
    public static void main(String[] args) {
        byte    byteVar   = 127;                         // 8-bit  signed: -128 to 127
        short   shortVar  = 32_767;                      // 16-bit signed
        int     intVar    = 2_147_483_647;               // 32-bit signed
        long    longVar   = 9_223_372_036_854_775_807L;  // 64-bit signed
        float   floatVar  = 3.14f;                       // 32-bit IEEE 754
        double  doubleVar = 3.141_592_653_589_793;       // 64-bit IEEE 754 (default)
        char    charVar   = 'A';                         // 16-bit Unicode
        boolean boolVar   = true;                        // true or false

        System.out.println("=== Java Primitive Data Types ===");
        System.out.println("byte    : " + byteVar);
        System.out.println("short   : " + shortVar);
        System.out.println("int     : " + intVar);
        System.out.println("long    : " + longVar);
        System.out.println("float   : " + floatVar);
        System.out.println("double  : " + doubleVar);
        System.out.println("char    : " + charVar);
        System.out.println("boolean : " + boolVar);

        System.out.println("\n=== Type Sizes (bits) ===");
        System.out.println("Byte.SIZE    = " + Byte.SIZE);
        System.out.println("Short.SIZE   = " + Short.SIZE);
        System.out.println("Integer.SIZE = " + Integer.SIZE);
        System.out.println("Long.SIZE    = " + Long.SIZE);
        System.out.println("Float.SIZE   = " + Float.SIZE);
        System.out.println("Double.SIZE  = " + Double.SIZE);
    }
}
