package org.example.jobcontext;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BaseJobContextTest {
    @Test
    public void testHappyPath() {
        final BaseJobContext ctx = new BaseJobContext();

        assertNotNull(ctx.getClientName());
        assertNotNull(ctx.getProjectName());
        assertEquals(1, ctx.getStartingSequenceNumber());
        assertNotNull(ctx.getUserHomePath());
        assertNotNull(ctx.getUserHomePath());
        assertFalse(ctx.isRunningSilent());
        assertDoesNotThrow(() -> ctx.logProgress(""));
    }
}