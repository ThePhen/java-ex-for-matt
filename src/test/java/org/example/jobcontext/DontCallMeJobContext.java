package org.example.jobcontext;

/**
 * TestAllEnvsPresentJobContext is used to test that all getters are not called. Useful when
 * testing JobContext chains.
 */
public class DontCallMeJobContext implements JobContext {
    public boolean thisCtxWasCalled = false;

    @Override
    public String getClientName() {
        throw new RuntimeException("This should not have been called.");
    }

    @Override
    public String getProjectName() {
        throw new RuntimeException("This should not have been called.");
    }

    @Override
    public int getStartingSequenceNumber() {
        throw new RuntimeException("This should not have been called.");
    }

    @Override
    public String getUserHomePath() {
        throw new RuntimeException("This should not have been called.");
    }

    @Override
    public boolean isRunningSilent() {
        throw new RuntimeException("This should not have been called.");
    }

    @Override
    public void logProgress(String message) {
        thisCtxWasCalled = true;
    }
}

