package org.example.jobcontext;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CmdArgsJobContextTest {

    @Test
    public void testHappyPath() throws IOException {

        String[] testArgs = {
                "-run-silent",
                "-c", "client",
                "-p", "project",
                "-s", "99",
                "--user-home", "bag's end"};

        AlwaysDeferToParentTestJobContext parentCtx = new AlwaysDeferToParentTestJobContext();
        JobContext testCtx = new CmdArgsJobContext(testArgs, parentCtx);

        assertEquals("client", testCtx.getClientName());
        assertEquals("project", testCtx.getProjectName());
        assertEquals(99, testCtx.getStartingSequenceNumber());
        assertEquals("bag's end", testCtx.getUserHomePath());
        assertTrue(testCtx.isRunningSilent());
        testCtx.logProgress("");
        assertTrue(parentCtx.wasParentCtxCalled);

    }
}
