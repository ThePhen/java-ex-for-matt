package org.example;

import org.example.jobcontext.JobContext;
import org.example.util.ProjectRootFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
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

    private File getJobProjectRootDir() throws IOException {
        final String path = getProjectsRootDir().getPath();
        final String clientName = ctx.getClientName();
        final String projectName = ctx.getProjectName();
        final Path outputPath = Paths.get(path, clientName, projectName);
        return outputPath.toFile();
    }

    private File getProjectsRootDir() throws IOException {
        if (!Objects.isNull(cachedProjectsRootDir)) return cachedProjectsRootDir;
        cachedProjectsRootDir = ProjectRootFactory.projectsRootDir(ctx.getUserHomePath());
        return cachedProjectsRootDir;
    }

    private boolean isClientNameGood() {
        return nameCheck("Client Name", ctx.getClientName());
    }

    private boolean isNullOrEmpty(String s) {
        return (s == null || s.trim().isEmpty());
    }

    private boolean isProjectNameGood() {
        return nameCheck("Project Name", ctx.getProjectName());
    }

    private String makePreRunSummaryMessage() throws IOException {
        final int numInputFiles = numInputFiles();
        final int numToProcess = Math.max(0, numInputFiles - ctx.getStartingSequenceNumber() + 1);
        final String projectsRoot = getJobProjectRootDir().getCanonicalPath();
        return "Job Project Root: " + projectsRoot +
                "\nClient: " + ctx.getClientName() +
                "\nProject: " + ctx.getProjectName() +
                "\nStarting at (if any): " + ctx.getStartingSequenceNumber() +
                "\nNum records found: " + numInputFiles +
                "\nNum records to process: " + numToProcess;
    }

    private boolean nameCheck(String nameTitle, String nameVal) {
        boolean isBad = isNullOrEmpty(nameVal);
        if (isBad) ctx.logProgress("\nThe " + nameTitle + " must not be blank.");
        return !isBad;
    }

    @SuppressWarnings("DataFlowIssue")
    public int numInputFiles() {
        try {
            File inputDir = new File(getJobProjectRootDir(), "Inputs");
            if (!inputDir.exists())
                throw new FileNotFoundException("Can't find file '" + inputDir.getCanonicalPath() + "'.");
            File[] files = inputDir.listFiles();
            return files.length;
        } catch (Exception e) {
            throw new RuntimeException("Trouble trying to count the number of record files for the project.", e);
        }
    }

    public void startProcessing() throws IOException {
        // NOTE: This would be much more complicated. It's where the real work begins.
        try {
            ctx.logProgress("=== " + LocalDateTime.now() + " ===");
            if (!areInputsGood()) throw new IllegalArgumentException("The input configuration is flawed. See above.");
            final String msg = makePreRunSummaryMessage();
            ctx.logProgress(msg);
        } finally {
            ctx.logProgress("=== " + LocalDateTime.now() + " ===");
        }
    }
}
