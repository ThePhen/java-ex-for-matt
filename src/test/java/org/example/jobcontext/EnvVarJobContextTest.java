package org.example.jobcontext;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class EnvVarJobContextTest {

    private final String NONCE = "";

    /**
     * testAllEnvsPresentEnvVars checks that none of the EnvVarJobContext's getters wind-up delegating
     * to its parent context (which is a test-related JobContext implementation written to support a
     * test like this). Note: the logProgress method is expected to delegate the parent context, and this
     * is checked for as well.
     *
     * @throws IOException
     */
    @Test
    void testAllEnvsPresentEnvVars() throws IOException {

        /* this GetEnv impl returns either the name of the env-var whose value was sought,
        or a specific value for non-String values.
         */
        EnvVarJobContext.GetEnv testEnv = (String name) -> {
            if (name.equals("EX_START_SEQ_NUM")) return "-9";
            if (name.equals("EX_RUN_SILENT")) return "TRUE";
            return name;
        };
        DontCallMeJobContext testParentCtx = new DontCallMeJobContext();
        JobContext ctx = new EnvVarJobContext(testEnv, testParentCtx);

        assertEquals("EX_CLIENT", ctx.getClientName());
        assertEquals("EX_PROJECT", ctx.getProjectName());
        assertEquals(-9, ctx.getStartingSequenceNumber());
        assertEquals("EX_USER_HOME", ctx.getUserHomePath());
        assertTrue(ctx.isRunningSilent());
        assertFalse(testParentCtx.thisCtxWasCalled);

        ctx.logProgress(NONCE);
        assertTrue(testParentCtx.thisCtxWasCalled);
    }

    /**
     * testPassthroughs expects that none of the various `EX_*` environment variables are set, and checks
     * that EnvVarJobContext will delegate its getters to the parent JobContext (which is a test-related
     * JobContext implementation written to support this kind of test).
     * @throws IOException
     */
    @Test
    void testPassthroughs() throws IOException {
        TrackIfEverCalledJobContext testParentCtx = new TrackIfEverCalledJobContext();
        JobContext ctx = new EnvVarJobContext(testParentCtx);

        // ignore the return vals. Just check that each call went 'up' to the parent ctx.
        ctx.getClientName();
        assertTrue(testParentCtx.ctxWasCalled);

        ctx.getProjectName();
        assertTrue(testParentCtx.ctxWasCalled);

        ctx.getStartingSequenceNumber();
        assertTrue(testParentCtx.ctxWasCalled);

        ctx.getUserHomePath();
        assertTrue(testParentCtx.ctxWasCalled);

        ctx.isRunningSilent();
        assertTrue(testParentCtx.ctxWasCalled);

        ctx.logProgress(NONCE);
        assertTrue(testParentCtx.ctxWasCalled);

    }
}