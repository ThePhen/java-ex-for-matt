package org.example;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.Files.walk;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestHelpers {

    public static final String TEST_HOME_DIR_NAME = "test_home_files";
    public static final String TEST_PROJECTS_ROOT_DIR_NAME = "test_projects_root";

    public static void copyDirectory(String sourceDirectoryLocation, String destinationDirectoryLocation)
            throws IOException {
        //noinspection resource
        walk(Paths.get(sourceDirectoryLocation))
                .forEach(source -> {
                    Path destination = Paths.get(destinationDirectoryLocation, source.toString()
                            .substring(sourceDirectoryLocation.length()));
                    try {
                        Files.copy(source, destination);
                    } catch (java.nio.file.FileAlreadyExistsException e) {
                        // noop
                    } catch (IOException e) {
                        System.err.println("Swallowing IOException while duplicating a directory. " +
                                "IOException was: " + e.getMessage());
                    }
                });
    }

    static File makeTempTestResourcesCopy(File origTestHome, File testResourceCopyRoot) throws IOException {
        File tmpHome = new File(testResourceCopyRoot, TEST_HOME_DIR_NAME);
        assertTrue(tmpHome.mkdir());
        File tmpProjects = new File(testResourceCopyRoot, TEST_PROJECTS_ROOT_DIR_NAME);
        assertTrue(tmpProjects.mkdir());

        copyDirectory(origTestHome.getCanonicalPath(), tmpHome.getCanonicalPath());
        copyDirectory(origTestHome.getCanonicalPath() + "/../" + TEST_PROJECTS_ROOT_DIR_NAME, tmpProjects.getCanonicalPath());

        System.out.println("Test Resources are at:\n" + testResourceCopyRoot.getCanonicalPath() +
                "\n" + tmpHome.getCanonicalPath() +
                "\n" + tmpProjects.getCanonicalPath());
        return tmpHome;
    }

    @SuppressWarnings("DataFlowIssue")
    public File getTestHomeDir() {
        ClassLoader classLoader = getClass().getClassLoader();
        final URL testHomeUrl = classLoader.getResource(TEST_HOME_DIR_NAME);
        final String testHomeFilePath = testHomeUrl.getFile();
        return new File(testHomeFilePath);
    }
}
