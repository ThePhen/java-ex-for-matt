package org.example.jobcontext;

public class BaseJobContext implements JobContext {

    public String getClientName() {
        return "";
    }

    public String getProjectName() {
        return "";
    }

    public int getStartingSequenceNumber() {
        return 1;
    }

    public String getUserHomePath() {
        return System.getProperty("user.home");
    }

    public boolean isRunningSilent() {
        return false;
    }

    public void logProgress(String message) {
        System.out.println(message);
    }
}
