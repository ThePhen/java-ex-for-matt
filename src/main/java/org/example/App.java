package org.example;

import org.example.launcher.Launcher;
import org.example.launcher.LauncherFactory;

import java.io.IOException;

public class App {
    public static void main(String[] args) {
        Launcher w = LauncherFactory.make(args);
        try {
            w.start();
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IO Exception under main entry point.", e);
        }
    }
}