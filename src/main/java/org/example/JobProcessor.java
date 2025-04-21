package org.example;

import org.example.jobcontext.JobContext;

import java.io.File;

public class JobProcessor {
    private final JobContext ctx;

    public JobProcessor(JobContext ctx) {
        this.ctx = ctx;
    }

    private int numInputFiles() {
        File inputDir = new File(ctx.getJobProjectRootDir(), "Inputs");
        return inputDir.listFiles().length;
    }

    public void startProcessing() {
        // NOTE: This would be much more complicated. It's where the real work begins.
        String msg = "Job Project Root (" + numInputFiles() + " Records found): " + ctx.getJobProjectRootDir() +
                "\nClient: " + ctx.getClientName() +
                "\nProject: " + ctx.getProjectName() +
                "\nStarting at (if any): " + ctx.getStartingSequenceNumber();
        ctx.logProgress(msg);
    }
}
