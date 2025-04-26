package org.example.util;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
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
     * of the overrids dir, where override XML files are held.
     *
     * @param userHome the location of the user's home
     * @return the location of the dir holding the overides XML file
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
     * getProjectsRootDirFromOverrides follows information stored in a runtime configuration-related XML file to
     * determine where the final location of the 'projects root'.
     *
     * @param overrideDir the location of the directory where the XML file that points to the 'projects root' is at
     * @return the final location of the 'projects root', as configured
     */
    private static File getProjectsRootDirFromOverrides(File overrideDir) {
        try {
            File xmlFile = getOverrideXmlFile(overrideDir);
            Document doc = parseXmlFileIntoDom(xmlFile);

            String body = getBodyOfFirstMatchingNode(doc, "ProjectRoot");
            File out = new File(body);
            if (null != out) {
                return out;
            }
            throw new IllegalStateException("Can not determine the Project Root " +
                    "from the '" + xmlFile + "' file.");
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private static String getBodyOfFirstMatchingNode(Document doc, String tagname) {
        NodeList nodeList = doc.getElementsByTagName(tagname);
        if (0 < nodeList.getLength()) {
            Node element = nodeList.item(0);
            String body = element.getTextContent().trim();
            return body;
        }
        return null;
    }

    private static Document parseXmlFileIntoDom(File xmlFile) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(xmlFile);
        doc.getDocumentElement().normalize();
        return doc;
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
            File localConfigOverrideFile = parseTheSettingsFile(homeDir);
            File projectsRootDirFromOverrides = getProjectsRootDirFromOverrides(localConfigOverrideFile);
            return new File(homeDir, projectsRootDirFromOverrides.getPath());
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
