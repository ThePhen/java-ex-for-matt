package org.example.jobcontext;

/**
 * TrackIfEverCalledJobContext is used to test JobContext chains. If any of the
 * implemented methods are invoked, the `ctxWasCalled` flagg will be 'true'.
 */
class TrackIfEverCalledJobContext implements JobContext {

    private final String NONCE = "";
    public boolean ctxWasCalled;

    @Override
    public String getClientName() {
        ctxWasCalled = true;
        return NONCE;
    }

    @Override
    public String getProjectName() {
        ctxWasCalled = true;
        return NONCE;
    }

    @Override
    public int getStartingSequenceNumber() {
        ctxWasCalled = true;
        return 0;
    }

    @Override
    public String getUserHomePath() {
        ctxWasCalled = true;
        return NONCE;
    }

    @Override
    public boolean isRunningSilent() {
        ctxWasCalled = true;
        return false;
    }

    @Override
    public void logProgress(String message) {
        ctxWasCalled = true;
    }
}
