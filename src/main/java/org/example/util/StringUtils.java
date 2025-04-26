package org.example.util;

@SuppressWarnings("ClassUnconnectedToPackage")
public enum StringUtils {
    ;

    public static boolean isNullOrEmpty(String s) {
        return (null == s || s.trim().isEmpty());
    }
}
