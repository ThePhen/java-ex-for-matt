package org.example.util;

@SuppressWarnings("ClassUnconnectedToPackage")
public class StringUtils {
    public static boolean isNullOrEmpty(String s) {
        return (s == null || s.trim().isEmpty());
    }
}
