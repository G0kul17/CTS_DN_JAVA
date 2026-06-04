package com.utils;

/**
 * Exercise 34: Java Module System — Utility class exported by com.utils module.
 *
 * HOW TO COMPILE AND RUN (from Module_3 directory):
 *
 *   # Step 1: Compile com.utils module
 *   javac -d mods/com.utils src/modules/com.utils/module-info.java src/modules/com.utils/com/utils/StringUtils.java
 *
 *   # Step 2: Compile com.greetings (depends on com.utils)
 *   javac --module-path mods -d mods/com.greetings src/modules/com.greetings/module-info.java src/modules/com.greetings/com/greetings/Main.java
 *
 *   # Step 3: Run com.greetings
 *   java --module-path mods -m com.greetings/com.greetings.Main
 */
public class StringUtils {

    /** Repeat a string n times */
    public static String repeat(String s, int n) {
        return s.repeat(n);
    }

    /** Reverse a string */
    public static String reverse(String s) {
        return new StringBuilder(s).reverse().toString();
    }

    /** Convert to title case */
    public static String toTitleCase(String s) {
        if (s == null || s.isEmpty()) return s;
        String[] words = s.split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                sb.append(Character.toUpperCase(word.charAt(0)))
                  .append(word.substring(1).toLowerCase())
                  .append(' ');
            }
        }
        return sb.toString().trim();
    }

    /** Check if string is a palindrome */
    public static boolean isPalindrome(String s) {
        String clean = s.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
        return clean.equals(reverse(clean));
    }
}
