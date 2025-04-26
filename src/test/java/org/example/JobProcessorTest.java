package org.example;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class JobProcessorTest {

    @TempDir
    private static File tempRoot;

    private static JobProcessor processor;

    @BeforeAll
    static void makeJobContextAndProcessor() throws IOException {
        File origTestHome = TestHelpers.getTestHomeDir();
        File tempTestHome = TestHelpers.makeTempTestResourcesCopy(origTestHome, tempRoot);
        processor = new JobProcessor(new SimpleJobContext(tempTestHome));
    }

    @Test
    void testNumInputFiles() throws IOException {
        assertEquals(5, processor.numInputFiles());
    }

    @Test
    void testProcessingHappyPath() {
        assertDoesNotThrow(() -> processor.startProcessing());
    }

}