package org.example;

public interface JobContext {
    String getProjectName();

    String getClientName();

    int getStartingSequenceNumber();

    boolean isRunningSilent();

    void logProgress(String message);
}
