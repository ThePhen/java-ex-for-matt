package org.example.jobcontext;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EnvVarJobContextTest {

    private final String NONCE = "";

    @Test
    void testAllEnvsPresentEnvVars() throws IOException {
        EnvVarJobContext.GetEnv testEnv = (String name) -> {
            if (name.equals("EX_START_SEQ_NUM")) return "-9";
            if (name.equals("EX_RUN_SILENT")) return "TRUE";
            return name;
        };
        TestAllEnvsPresentJobContext testParentCtx = new TestAllEnvsPresentJobContext();
        JobContext ctx = new EnvVarJobContext(testEnv, testParentCtx);

        ctx.logProgress(NONCE);
        assertTrue(testParentCtx.wasParentCtxCalled);
        assertEquals("EX_CLIENT", ctx.getClientName());
        assertEquals("EX_PROJECT", ctx.getProjectName());
        assertEquals(-9, ctx.getStartingSequenceNumber());
        assertEquals("EX_USER_HOME", ctx.getUserHomePath());
        assertTrue(ctx.isRunningSilent());
    }

    @Test
    void testPassthroughs() throws IOException {
        TestAllEnvsMissingJobContext testParentCtx = new TestAllEnvsMissingJobContext();
        JobContext ctx = new EnvVarJobContext(testParentCtx);

        // ignore the return vals. Just check that each call went 'up' to the parent ctx.
        ctx.getClientName();
        assertTrue(testParentCtx.wasParentCtxCalled);

        ctx.getProjectName();
        assertTrue(testParentCtx.wasParentCtxCalled);

        ctx.getStartingSequenceNumber();
        assertTrue(testParentCtx.wasParentCtxCalled);

        ctx.getUserHomePath();
        assertTrue(testParentCtx.wasParentCtxCalled);

        ctx.isRunningSilent();
        assertTrue(testParentCtx.wasParentCtxCalled);

        ctx.logProgress(NONCE);
        assertTrue(testParentCtx.wasParentCtxCalled);

    }

    public static class TestAllEnvsPresentJobContext implements JobContext {
        public boolean wasParentCtxCalled = false;

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
            wasParentCtxCalled = true;
        }
    }

    class TestAllEnvsMissingJobContext implements JobContext {
        boolean wasParentCtxCalled = false;

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
}