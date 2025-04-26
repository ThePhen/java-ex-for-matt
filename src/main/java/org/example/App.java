package org.example;

import org.example.launcher.Launcher;
import org.example.launcher.LauncherFactory;

import java.io.IOException;

class App {
    public static void main(String[] args) throws IOException {
        new App().start(args);
    }

    private void start(String[] args) throws IOException {
        Launcher worker = LauncherFactory.make(args);
        worker.start();
    }
}