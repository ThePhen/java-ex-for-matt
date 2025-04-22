package org.example.jobcontext;

import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BaseJobContextTest {
    @Test
    public void testGetUserHomePath() {
        final BaseJobContext baseJobContext = new BaseJobContext();
        assertTrue(Objects.nonNull(baseJobContext.getUserHomePath()));
    }
}