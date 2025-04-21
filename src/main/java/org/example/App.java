package org.example;

import org.example.launcher.Launcher;
import org.example.launcher.LauncherFactory;

public class App {
    public static void main(String[] args) {
        Launcher w = LauncherFactory.make(args);
        w.start();
    }
}