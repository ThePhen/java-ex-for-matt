package org.example.util;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
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

public class ProjectRootFactory {
    static File parseLocalOverridesDir(File homeFile) throws IOException {
        File rootSettingsFile = new File(homeFile, "SETTINGS.txt");
        if (!rootSettingsFile.canRead()) {
            throw new FileNotFoundException("Can not read the user's .SETTINGS.txt file " +
                    "(" + rootSettingsFile.getAbsolutePath() + ").");
        }
        List<String> localMachineConfigs = Files.readAllLines(rootSettingsFile.toPath());
        if (localMachineConfigs.size() != 1) {
            throw new IllegalArgumentException("The .SETTINGS.txt file doesn't parse correctly. The only line in the " +
                    "file ought to have a path to where the local machine's configuration overrides are written. " +
                    "The file is '" + rootSettingsFile + "'.");
        }
        return new File(homeFile, localMachineConfigs.get(0));
    }

    static File getOverrideXmlFile(File overrideDir) throws FileNotFoundException {
        File xmlFile = new File(overrideDir, "LocalOverrides.xml");
        if (!xmlFile.canRead()) {
            throw new FileNotFoundException("Can not access the user's " +
                    "local overrides XML folder ('" + xmlFile + "').");
        }
        return xmlFile;
    }

    static File getProjectsRootDirFromOverrides(File overrideDir) {
        try {
            final File xmlFile = getOverrideXmlFile(overrideDir);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("ProjectRoot");
            if (nodeList.getLength() > 0) {
                Element element = (Element) nodeList.item(0);
                final String body = element.getTextContent().trim();
                return new File(body);
            }
            throw new IllegalStateException("Can not determine the Project Root " +
                    "from the '" + xmlFile + "' file.");
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static File projectsRootDir(String homePath) {
        try {
            final File homeDir = getHomeFile(homePath);
            final File localConfigOverrideFile = parseLocalOverridesDir(homeDir);
            final File projectsRootDirFromOverrides = getProjectsRootDirFromOverrides(localConfigOverrideFile);
            return new File(homeDir, projectsRootDirFromOverrides.getPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static File getHomeFile(String homePath) throws FileNotFoundException {
        Path home = Paths.get(homePath);
        File homeFile = home.toFile();

        if (!homeFile.canRead()) {
            throw new FileNotFoundException("Can not access the user's HOME folder ('" + homeFile + "').");
        }
        return homeFile;
    }
}
