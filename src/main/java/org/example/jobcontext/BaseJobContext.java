package org.example.jobcontext;

import org.example.util.ProjectRootFactory;

import java.io.File;
import java.nio.file.Paths;
import java.util.Objects;

public abstract class BaseJobContext implements JobContext {
    private File cachedProjectsRootDir = null;

    public boolean areInputsGood() {
        final boolean a = isClientNameGood();
        final boolean b = isProjectNameGood();
        return a && b;
    }

    @Override
    public abstract String getClientName();

    @Override
    public File getJobProjectRootDir() {
        return Paths.get(getProjectsRootDir().getPath(), getClientName(), getProjectName()).toFile();
    }

    @Override
    public abstract String getProjectName();

    @Override
    public File getProjectsRootDir() {
        if (!Objects.isNull(cachedProjectsRootDir)) return cachedProjectsRootDir;
        final File projectsRootDirFromOverrides = ProjectRootFactory.projectsRootDir(getUserHomePath());
        cachedProjectsRootDir = projectsRootDirFromOverrides;
        return projectsRootDirFromOverrides;
    }

    @Override
    public abstract int getStartingSequenceNumber();

    abstract String getUserHomePath();

    private boolean isClientNameGood() {
        if (isNullOrEmpty(getClientName())) {
            logProgress("\nThe Client Name must not be blank.");
            return false;
        }
        return true;
    }

    private boolean isNullOrEmpty(String s) {
        return (s == null || s.trim().isEmpty());
    }

    private boolean isProjectNameGood() {
        if (isNullOrEmpty(getProjectName())) {
            logProgress("\nThe Project Name must not be blank.");
            return false;
        }
        return true;
    }

    @Override
    public abstract boolean isRunningSilent();

    @Override
    public abstract void logProgress(String message);
}
