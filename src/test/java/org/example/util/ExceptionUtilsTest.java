package org.example.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ExceptionUtilsTest {
    @Test
    void testHappyPath() {
        Exception testEx = new RuntimeException("ZZZZ_TEST_ZZZZ");
        final String stackTraceAsString = ExceptionUtils.getStackTraceAsString(testEx);
        assertTrue(stackTraceAsString.indexOf("ZZZZ_TEST_ZZZZ") > 0);
    }
}