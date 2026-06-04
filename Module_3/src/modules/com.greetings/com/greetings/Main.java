package com.greetings;

import com.utils.StringUtils;

/**
 * Exercise 34: Java Module System — Main class in com.greetings module.
 * Uses StringUtils exported by com.utils.
 *
 * See StringUtils.java for compile + run instructions.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=== Java Module System Demo ===\n");

        String msg = "hello from the module system";
        System.out.println("Original  : " + msg);
        System.out.println("Title Case: " + StringUtils.toTitleCase(msg));
        System.out.println("Reversed  : " + StringUtils.reverse(msg));
        System.out.println("Repeated  : " + StringUtils.repeat("Java! ", 3));
        System.out.println("Palindrome (racecar): " + StringUtils.isPalindrome("racecar"));
        System.out.println("Palindrome (hello)  : " + StringUtils.isPalindrome("hello"));
    }
}
