package org.example.jobcontext;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class CmdArgsJobContextTest {

    /**
     * testHeppyPath checks that when provided sufficient input commmandline args,
     * that CmdArgsJobContext does not delegate to its parent context. Note that logProgress,
     * on the other hand, is meant to use delegation, however.
     *
     * @throws IOException
     */
    @SuppressWarnings("MagicNumber")
    @Test
    void testHappyPath() throws IOException {

        String[] testArgs = {
                "-r",
                "-c", "client",
                "-p", "project",
                "-s", "99",
                "--user-home", "bag's end"};

        TrackIfEverCalledJobContext parentCtx = new TrackIfEverCalledJobContext();
        JobContext testCtx = new CmdArgsJobContext(testArgs, parentCtx);

        assertEquals("client", testCtx.getClientName());
        assertEquals("project", testCtx.getProjectName());
        assertEquals(99, testCtx.getStartingSequenceNumber());
        assertEquals("bag's end", testCtx.getUserHomePath());
        assertTrue(testCtx.isRunningSilent());
        assertFalse(parentCtx.ctxWasCalled);
        testCtx.logProgress("");
        assertTrue(parentCtx.ctxWasCalled);

    }
}
