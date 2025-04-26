package org.example;

import org.example.launcher.LauncherFactory;

import java.io.IOException;

class App {
    public static void main(String[] args) throws IOException {
        LauncherFactory.makeAndStart(args);
    }
}