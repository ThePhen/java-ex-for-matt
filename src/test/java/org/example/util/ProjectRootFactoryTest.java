package org.example.util;

import org.example.TestHelpers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProjectRootFactoryTest {

    private File testHome;

    /**
     * These tests don't need to duplicate the test resources-- they're not altered, here.
     */
    @BeforeEach
    void setUpTestResourceRoot() {
        this.testHome = new TestHelpers().getTestHomeDir();
    }

    @Test
    void getReadableFileHappy() throws FileNotFoundException {
        final String testGoodHomeAbsolutePath = testHome.getAbsolutePath();

        final File expected = new File(testGoodHomeAbsolutePath);
        final File actual = ProjectRootFactory.getReadableFile(testGoodHomeAbsolutePath);
        assertEquals(expected, actual);
    }

    @Test
    void getOverrideXmlFile() throws IOException {
        final File expected = new File(this.testHome, "local_config_overrides/LocalOverrides.xml");
        final File settingsFile = ProjectRootFactory.parseTheSettingsFile(this.testHome);
        final File actual = ProjectRootFactory.getOverrideXmlFile(settingsFile);
        assertEquals(expected, actual);
    }

    @Test
    void parseTheSettingsFile() throws IOException {
        File expected = new File(this.testHome, "local_config_overrides");
        File actual = ProjectRootFactory.parseTheSettingsFile(this.testHome);
        assertEquals(expected.getCanonicalFile(), actual.getCanonicalFile());
    }


    @Test
    void testDeriveProjectsRootDir() throws IOException {
        File expected = new File(testHome, "../" + TestHelpers.TEST_PROJECTS_ROOT_DIR_NAME).getCanonicalFile();

        final String homePath = testHome.getCanonicalPath();
        File actual = ProjectRootFactory.deriveProjectsRootDir(homePath).getCanonicalFile();

        assertEquals(expected, actual);
    }
}