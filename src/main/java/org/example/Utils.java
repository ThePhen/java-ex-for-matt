package org.example;

import java.io.PrintWriter;
import java.io.StringWriter;

public class Utils {
    public static String getStackTraceAsString(Exception e) {
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            return sw.toString();
        } catch (Exception ex) {
            System.err.println("unexpected error while building an exception " +
                    "stack trace for display to GUI user.\n" + e);
            return "<<< error getting stack trace for display >>>";
        }
    }
}
