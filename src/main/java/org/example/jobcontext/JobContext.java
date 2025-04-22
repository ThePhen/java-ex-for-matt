package org.example.jobcontext;

import java.io.File;

public interface JobContext {

    String getClientName();

    String getProjectName();

    int getStartingSequenceNumber();

    String getUserHomePath();

    boolean isRunningSilent();

    void logProgress(String message);
}