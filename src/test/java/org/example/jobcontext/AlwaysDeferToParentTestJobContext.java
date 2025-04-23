package org.example.jobcontext;

class AlwaysDeferToParentTestJobContext implements JobContext {

    public final String NONCE = "";
    public boolean wasParentCtxCalled = false;

    @Override
    public String getClientName() {
        wasParentCtxCalled = true;
        return NONCE;
    }

    @Override
    public String getProjectName() {
        wasParentCtxCalled = true;
        return NONCE;
    }

    @Override
    public int getStartingSequenceNumber() {
        wasParentCtxCalled = true;
        return 0;
    }

    @Override
    public String getUserHomePath() {
        wasParentCtxCalled = true;
        return NONCE;
    }

    @Override
    public boolean isRunningSilent() {
        wasParentCtxCalled = true;
        return false;
    }

    @Override
    public void logProgress(String message) {
        wasParentCtxCalled = true;
    }
}
