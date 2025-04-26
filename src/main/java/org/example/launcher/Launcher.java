package org.example.launcher;

import java.io.IOException;

@FunctionalInterface
public interface Launcher {
    void start() throws IOException;
}
