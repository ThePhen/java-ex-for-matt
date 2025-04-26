package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

class AppTest {

    /**
     * testBadHeadlessConfigRaisesError checks that a specific exception is thrown when the headless
     * runner can't find the necessary parameters.
     */
    @Test
    void testBadHeadlessConfigRaisesError() {
        assertThrowsExactly(IllegalArgumentException.class, () -> App.main(new String[]{"--run-silent"}));
    }

    /**
     * testIdealHeadlessRun checks that given the known-good test-time resources (see `src/test/resources/*`)
     * will permit a successful job run.
     *
     * @param tempResourcesCopy this is auto-wired, thanks to JUnit5's @TempDir,
     *                          which will clean up that dir after the test.
     * @throws IOException
     */
    @Test
    void testIdealHeadlessRun(@TempDir File tempResourcesCopy) throws IOException {
        File testHome = new TestHelpers().getTestHomeDir();
        File tempTestHome = TestHelpers.makeTempTestResourcesCopy(testHome, tempResourcesCopy);

        assertDoesNotThrow(() -> App.main(new String[]{"--run-silent",
                "-c=Client A", "-p=Project Y", "-s=2",
                "--user-home", tempTestHome.getCanonicalPath()}));
    }

    /**
     * testStartsInGuiWithBlankishConfig expects that the given no args, from `java org.example.App`, for example,
     * it will starts up as if the user will then make inputs, start processing, etc.
     * <p>
     * It really just checks that the GUI can start.
     */
    @Test
    void testStartsInGuiWithBlankishConfig() {
        assertDoesNotThrow(() -> App.main(new String[]{}));
    }
}