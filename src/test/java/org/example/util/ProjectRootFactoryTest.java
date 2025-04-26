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

    @Test
    void getOverrideXmlFile() throws IOException {
        File expected = new File(testHome, "local_config_overrides/LocalOverrides.xml");
        File settingsFile = ProjectRootFactory.parseTheSettingsFile(testHome);
        File actual = ProjectRootFactory.getOverrideXmlFile(settingsFile);
        assertEquals(expected, actual);
    }

    @Test
    void getReadableFileHappy() throws FileNotFoundException {
        String testGoodHomeAbsolutePath = testHome.getAbsolutePath();

        File expected = new File(testGoodHomeAbsolutePath);
        File actual = ProjectRootFactory.getReadableFile(testGoodHomeAbsolutePath);
        assertEquals(expected, actual);
    }

    @Test
    void parseTheSettingsFile() throws IOException {
        File expected = new File(testHome, "local_config_overrides");
        File actual = ProjectRootFactory.parseTheSettingsFile(testHome);
        assertEquals(expected.getCanonicalFile(), actual.getCanonicalFile());
    }

    /**
     * These tests don't need to duplicate the test resources-- they're not altered, here.
     */
    @BeforeEach
    void setUpTestResourceRoot() {
        testHome = new TestHelpers().getTestHomeDir();
    }

    @Test
    void testDeriveProjectsRootDir() throws IOException {
        File expected = new File(testHome, "../" + TestHelpers.TEST_PROJECTS_ROOT_DIR_NAME).getCanonicalFile();

        String homePath = testHome.getCanonicalPath();
        File actual = ProjectRootFactory.deriveProjectsRootDir(homePath).getCanonicalFile();

        assertEquals(expected, actual);
    }
}