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

    /**
     * copyDirectory duplicates the contents of the sourceDirectoryLocation into the destinationDirectoryLocation.
     * <p>
     * Sometimes the files will already exist, so we're swallowing the error that occurs when that happens.
     *
     * @param sourceDirectoryLocation the root directory whose contents will be duplicated
     * @param destinationDirectoryLocation the root directory into which the duplicates will be placed.
     * @throws IOException
     */
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

    /**
     * makeTempTestResourcesCopy is intended to duplicate the test-specific 'home' and 'projects root'
     * files and directories into the testResourceCopyRoot directory, provided. It creates the needed
     * 'home' and 'projects root' sub-dirs, and populates them.
     *
     * @param origTestHome         the location of the test-time assets to duplicate
     * @param testResourceCopyRoot the location where the duplicates will be created
     * @return File the location of the 'new' duplicate 'user home',
     * i.e. where the SETTINGS.txt file, et al., can be found.
     * @throws IOException
     */
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

    /**
     * getTestHomeDir figures out where the test-specific 'home' and 'project root' assets at.
     * If we're running from within a JAR, this would be different than running
     * outside a JAR.
     * <p>
     * Assets are (sometimes) duplicated from this location so that testing
     * won't alter these 'original' files.
     *
     * @return File where the test-time assets can be found.
     */
    @SuppressWarnings("DataFlowIssue")
    public File getTestHomeDir() {
        ClassLoader classLoader = getClass().getClassLoader();
        final URL testHomeUrl = classLoader.getResource(TEST_HOME_DIR_NAME);
        final String testHomeFilePath = testHomeUrl.getFile();
        return new File(testHomeFilePath);
    }
}
