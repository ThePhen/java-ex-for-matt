package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

class AppTest {

    @Test
    void testBadHeadlessConfigRaisesError() {
        assertThrowsExactly(IllegalArgumentException.class, () -> App.main(new String[]{"--run-silent"}));
    }

    @Test
    void testIdealHeadlessRun(@TempDir File tempResourcesCopy) throws IOException {
        File testHome = new TestHelpers().getTestHomeDir();
        final File tempTestHome = TestHelpers.makeTempTestResourcesCopy(testHome, tempResourcesCopy);
        assertDoesNotThrow(() -> App.main(new String[]{"--run-silent",
                "-c=Client A", "-p=Project Y", "-s=2",
                "--user-home", tempTestHome.getCanonicalPath()}));
    }

    @Test
    void testStartsInGuiWithBlankishConfig() {
        assertDoesNotThrow(() -> App.main(new String[]{}));
    }
}