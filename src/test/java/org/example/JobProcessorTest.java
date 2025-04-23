package org.example;

import org.example.jobcontext.JobContext;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class JobProcessorTest {

    private static final String CLIENT = "Client A";
    private static final String PROJECT = "Project Y";
    private static final int START_SEQ_NUM = 3;

    static JobContext ctx;

    @TempDir
    private static File tempRoot;
    private static File tempTestHome;

    private static JobProcessor processor;

    @BeforeAll
    static void makeJobContextAndProcessor() throws IOException {
        File origTestHome = new TestHelpers().getTestHomeDir();

        tempTestHome = TestHelpers.makeTempTestResourcesCopy(origTestHome, tempRoot);

        ctx = new JobContext() {
            @Override
            public String getClientName() {
                return CLIENT;
            }

            @Override
            public String getProjectName() {
                return PROJECT;
            }

            @Override
            public int getStartingSequenceNumber() {
                return START_SEQ_NUM;
            }

            @Override
            public String getUserHomePath() throws IOException {
                return tempTestHome.getCanonicalPath();
            }

            @Override
            public boolean isRunningSilent() {
                return true;
            }

            @Override
            public void logProgress(String message) {
                System.out.println(message);
            }
        };

        processor = new JobProcessor(ctx);
    }

    @Test
    void testNumInputFiles() {
        assertEquals(5, processor.numInputFiles());
    }

    @Test
    void testProcessingHappyPath() {
        assertDoesNotThrow(() -> processor.startProcessing());
    }
}