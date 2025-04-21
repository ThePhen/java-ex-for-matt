package org.example;

public class JobProcessor {
    public void startProcessing(JobContext ctx) {
        // NOTE: This would be much more complicated.
        String msg = "Client: " + ctx.getClientName() +
                "\nProject: " + ctx.getProjectName() +
                "\nStarting at (if any): " + ctx.getStartingSequenceNumber();
        ctx.logProgress(msg);
    }
}
