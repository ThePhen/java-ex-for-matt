package org.example.jobcontext;

import java.io.IOException;

public interface JobContext {

    String getClientName();

    String getProjectName();

    int getStartingSequenceNumber();

    String getUserHomePath() throws IOException;

    boolean isRunningSilent();

    void logProgress(String message);
}