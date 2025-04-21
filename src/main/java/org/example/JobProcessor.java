package org.example;

import org.example.jobcontext.JobContext;

public class JobProcessor {
    public void startProcessing(JobContext ctx) {
        // NOTE: This would be much more complicated. It's where the real work begins.
        String msg = "Job Project Root: " + ctx.getJobProjectRootDir() +
                "\nClient: " + ctx.getClientName() +
                "\nProject: " + ctx.getProjectName() +
                "\nStarting at (if any): " + ctx.getStartingSequenceNumber();
        ctx.logProgress(msg);
    }
}
