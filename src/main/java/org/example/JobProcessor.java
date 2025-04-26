package org.example;

import org.example.jobcontext.JobContext;
import org.example.util.ProjectRootFactory;
import org.example.util.StringUtils;

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
        boolean check1 = isClientNameGood();
        boolean check2 = isProjectNameGood();
        return check1 && check2;
    }

    private File getJobProjectRootDir() throws IOException {
        String path = getProjectsRootDir().getPath();
        String clientName = ctx.getClientName();
        String projectName = ctx.getProjectName();
        Path outputPath = Paths.get(path, clientName, projectName);
        return outputPath.toFile();
    }

    private File getProjectsRootDir() throws IOException {
        if (!Objects.isNull(cachedProjectsRootDir)) return cachedProjectsRootDir;
        cachedProjectsRootDir = ProjectRootFactory.deriveProjectsRootDir(ctx.getUserHomePath());
        return cachedProjectsRootDir;
    }

    private boolean isClientNameGood() {
        return isNameGood("Client Name", ctx.getClientName());
    }

    private boolean isNameGood(String nameTitle, String nameVal) {
        boolean isBad = StringUtils.isNullOrEmpty(nameVal);
        if (isBad) ctx.logProgress("\nThe " + nameTitle + " must not be blank.");
        return !isBad;
    }

    private boolean isProjectNameGood() {
        return isNameGood("Project Name", ctx.getProjectName());
    }

    private String makePreRunSummaryMessage() throws IOException {
        int numInputFiles = numInputFiles();
        int numToProcess = Math.max(0, numInputFiles - ctx.getStartingSequenceNumber() + 1);
        String projectsRoot = getJobProjectRootDir().getCanonicalPath();
        return "Job Project Root: " + projectsRoot +
                "\nClient: " + ctx.getClientName() +
                "\nProject: " + ctx.getProjectName() +
                "\nStarting at (if any): " + ctx.getStartingSequenceNumber() +
                "\nNum records found: " + numInputFiles +
                "\nNum records to process: " + numToProcess;
    }

    @SuppressWarnings("DataFlowIssue")
    public int numInputFiles() throws IOException {
        File projectRootDir = getJobProjectRootDir();
        File inputDir = new File(projectRootDir, "Inputs");
        if (!inputDir.exists())
            throw new FileNotFoundException("Can't find file '" + inputDir.getCanonicalPath() + "'.");
        File[] files = inputDir.listFiles();
        return files.length;
    }

    public void startProcessing() throws IOException {
        // NOTE: This would be much more complicated IRL. It's where the real work begins.
        try {
            ctx.logProgress("=== " + LocalDateTime.now() + " ===");
            if (!areInputsGood()) throw new IllegalArgumentException("The input configuration is flawed. See above.");
            String msg = makePreRunSummaryMessage();
            ctx.logProgress(msg);
        } finally {
            ctx.logProgress("=== " + LocalDateTime.now() + " ===");
        }
    }
}
