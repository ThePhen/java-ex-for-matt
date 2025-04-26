package org.example.jobcontext;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class BaseJobContextTest {
    @Test
    void testHappyPath() throws IOException {
        JobContext ctx = new BaseJobContext();
        assertNotNull(ctx.getClientName());
        assertNotNull(ctx.getProjectName());
        assertEquals(1, ctx.getStartingSequenceNumber());
        assertNotNull(ctx.getUserHomePath());
        assertNotNull(ctx.getUserHomePath());
        assertFalse(ctx.isRunningSilent());
        assertDoesNotThrow(() -> ctx.logProgress(""));
    }
}