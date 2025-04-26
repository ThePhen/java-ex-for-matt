package org.example.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public enum ProjectRootFactory {
    ;

    /**
     * parseTheSettingsFile reads the SETTINGS.txt file, and returns a handle to the location
     * of the overrides dir, where override XML files are held.
     *
     * @param userHome the location of the user's home
     * @return the location of the dir holding the overrides XML file
     * @throws IOException
     */
    static File parseTheSettingsFile(File userHome) throws IOException {
        File rootSettingsFile = new File(userHome, "SETTINGS.txt");
        if (!rootSettingsFile.canRead()) {
            throw new FileNotFoundException("Can not read the user's .SETTINGS.txt file " +
                    "(" + rootSettingsFile.getAbsolutePath() + ").");
        }
        List<String> localMachineConfigs = Files.readAllLines(rootSettingsFile.toPath());
        if (1 != localMachineConfigs.size()) {
            throw new IllegalArgumentException("The .SETTINGS.txt file doesn't parse correctly. The only line in the " +
                    "file ought to have a path to where the local machine's configuration overrides are written. " +
                    "The file is '" + rootSettingsFile + "'.");
        }
        return new File(userHome, localMachineConfigs.get(0));
    }

    /**
     * getOverrideXmlFile returns a handle to the implied XML after checking that the file exists.
     *
     * @param overrideDir the dir where the XML file is held
     * @return a handle to the XML file
     * @throws FileNotFoundException
     */
    static File getOverrideXmlFile(File overrideDir) throws FileNotFoundException {
        File xmlFile = new File(overrideDir, "LocalOverrides.xml");
        if (!xmlFile.canRead()) {
            throw new FileNotFoundException("Can not access the user's " +
                    "local overrides XML folder ('" + xmlFile + "').");
        }
        return xmlFile;
    }

    /**
     * deriveProjectsRootDir follows a set of files, starting in the user's home, to find out where
     * the 'projects root' is at (i.e. where the Client and Project Input files should be found).
     *
     * @param homePath the location of the user's home
     * @return the location of the 'projects root'
     */
    public static File deriveProjectsRootDir(String homePath) {
        try {
            File homeDir = getReadableFile(homePath);
            File overrideFile = parseTheSettingsFile(homeDir);
            File xmlFile = getOverrideXmlFile(overrideFile);
            File dirFromOverrides = XmlUtils.getProjectsRootDirFromOverrides(xmlFile);
            return new File(homeDir, dirFromOverrides.getPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * getReadableFile takes a string path, checks that the location is readable, and returns a File pointing to
     * that string path.
     *
     * @param path the location of the file or folder to handle
     * @return a known-readable File object pointing to the input path
     * @throws FileNotFoundException
     */
    static File getReadableFile(String path) throws FileNotFoundException {
        Path home = Paths.get(path);
        File homeFile = home.toFile();

        if (!homeFile.canRead()) {
            throw new FileNotFoundException("Can not access the user's HOME folder ('" + homeFile + "').");
        }
        return homeFile;
    }
}
