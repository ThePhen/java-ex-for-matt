package org.example.jobcontext;

/**
 * TestAllEnvsPresentJobContext is used to test that all getters are not called. Useful when
 * testing JobContext chains.
 */
public class DontCallMeJobContext implements JobContext {
    public boolean thisCtxWasCalled;

    @Override
    public String getClientName() {
        throw new IShouldNotHaveBeenCalledException();
    }

    @Override
    public String getProjectName() {
        throw new IShouldNotHaveBeenCalledException();
    }

    @Override
    public int getStartingSequenceNumber() {
        throw new IShouldNotHaveBeenCalledException();
    }

    @Override
    public String getUserHomePath() {
        throw new IShouldNotHaveBeenCalledException();
    }

    @Override
    public boolean isRunningSilent() {
        throw new IShouldNotHaveBeenCalledException();
    }

    @Override
    public void logProgress(String message) {
        thisCtxWasCalled = true;
    }

}
