package org.example.jobcontext;

import org.example.util.ProjectRootFactory;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class BaseJobContext implements JobContext {

    private File cachedProjectsRootDir = null;


    public String getClientName() {
        return "";
    }

    public String getProjectName() {
        return "";
    }

    public File getProjectsRootDir() {
        if (!Objects.isNull(cachedProjectsRootDir)) return cachedProjectsRootDir;
        cachedProjectsRootDir = ProjectRootFactory.projectsRootDir(getUserHomePath());
        return cachedProjectsRootDir;
    }

    public int getStartingSequenceNumber() {
        return 1;
    }

    public String getUserHomePath() {
        return System.getProperty("user.home");
    }

    public boolean isRunningSilent() {
        return false;
    }

    public void logProgress(String message) {
        System.out.println(message);
    }
}
