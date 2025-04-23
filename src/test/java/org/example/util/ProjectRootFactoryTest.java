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
    void getHomeFileHappy() throws FileNotFoundException {
        final String testGoodHomeAbsolutePath = testHome.getAbsolutePath();

        final File expected = new File(testGoodHomeAbsolutePath);
        final File goodActual = ProjectRootFactory.getHomeFile(testGoodHomeAbsolutePath);
        assertEquals(expected, goodActual);
    }

    @Test
    void getOverrideXmlFile() throws IOException {
        final File expected = new File(this.testHome, "local_config_overrides/LocalOverrides.xml");
        final File settingsFile = ProjectRootFactory.parseLocalOverridesDir(this.testHome);
        final File actual = ProjectRootFactory.getOverrideXmlFile(settingsFile);
        assertEquals(expected, actual);
    }

    @Test
    void parseLocalOverridesDir() throws IOException {
        File expected = new File(this.testHome, "local_config_overrides");
        File actual = ProjectRootFactory.parseLocalOverridesDir(this.testHome);
        assertEquals(expected.getCanonicalFile(), actual.getCanonicalFile());
    }

    @BeforeEach
    void setUpTestResourceRoot() {
        this.testHome = new TestHelpers().getTestHomeDir();
    }

    @Test
    void testProjectsRootDir() throws IOException {
        File expected = new File(testHome, "../" + TestHelpers.TEST_PROJECTS_ROOT_DIR_NAME).getCanonicalFile();

        final String homePath = testHome.getCanonicalPath();
        File actual = ProjectRootFactory.projectsRootDir(homePath).getCanonicalFile();

        assertEquals(expected, actual);
    }
}