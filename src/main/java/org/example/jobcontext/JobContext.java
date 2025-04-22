package org.example.jobcontext;

public interface JobContext {

    String getClientName();

    String getProjectName();

    int getStartingSequenceNumber();

    String getUserHomePath();

    boolean isRunningSilent();

    void logProgress(String message);
}