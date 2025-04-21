package org.example.jobcontext;

import java.io.File;

public interface JobContext {
    boolean areInputsGood();

    String getClientName();

    File getJobProjectRootDir();

    String getProjectName();

    File getProjectsRootDir();

    int getStartingSequenceNumber();

    boolean isRunningSilent();

    void logProgress(String message);
}