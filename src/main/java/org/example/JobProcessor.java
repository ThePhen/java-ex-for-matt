package org.example;

import org.example.jobcontext.JobContext;
import org.example.util.ProjectRootFactory;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class JobProcessor {
    private final JobContext ctx;
    private File cachedProjectsRootDir;

    public JobProcessor(JobContext ctx) {
        this.ctx = ctx;
    }

    private boolean areInputsGood() {
        //run both checks — side-stepping short-circuit eval
        final boolean a = isClientNameGood();
        final boolean b = isProjectNameGood();
        return a && b;
    }

    private File getJobProjectRootDir() {
        final String path = getProjectsRootDir().getPath();
        final String clientName = ctx.getClientName();
        final String projectName = ctx.getProjectName();
        final Path outputPath = Paths.get(path, clientName, projectName);
        return outputPath.toFile();
    }

    private File getProjectsRootDir() {
        if (!Objects.isNull(cachedProjectsRootDir)) return cachedProjectsRootDir;
        cachedProjectsRootDir = ProjectRootFactory.projectsRootDir(ctx.getUserHomePath());
        return cachedProjectsRootDir;
    }

    private boolean isClientNameGood() {
        boolean isBad = isNullOrEmpty(ctx.getClientName());
        if (isBad) ctx.logProgress("\nThe Client Name must not be blank.");
        return !isBad;
    }

    private boolean isNullOrEmpty(String s) {
        return (s == null || s.trim().isEmpty());
    }

    private boolean isProjectNameGood() {
        boolean isBad = isNullOrEmpty(ctx.getProjectName());
        if (isBad) ctx.logProgress("\nThe Project Name must not be blank.");
        return !isBad;
    }

    private int numInputFiles() {
        File inputDir = new File(getJobProjectRootDir(), "Inputs");
        return inputDir.listFiles().length;
    }

    public void startProcessing() {
        // NOTE: This would be much more complicated. It's where the real work begins.
        if (!areInputsGood()) throw new IllegalArgumentException("The input configuration is flawed. See above.");
        String msg = "Job Project Root (" + numInputFiles() + " Records found): " + getJobProjectRootDir() +
                "\nClient: " + ctx.getClientName() +
                "\nProject: " + ctx.getProjectName() +
                "\nStarting at (if any): " + ctx.getStartingSequenceNumber();
        ctx.logProgress(msg);
    }
}
