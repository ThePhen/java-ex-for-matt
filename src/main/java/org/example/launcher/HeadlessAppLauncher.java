package org.example.launcher;

import org.example.jobcontext.JobContext;
import org.example.JobProcessor;

import java.util.Objects;

public class HeadlessAppLauncher implements Launcher {
    private final JobContext parentContext;

    public HeadlessAppLauncher(JobContext ctx) {
        parentContext = ctx;
    }

    public void start() {
        validateNeededInputArgs();
        final JobProcessor runner = new JobProcessor();
        runner.startProcessing(parentContext);
    }

    private void validateNeededInputArgs() {
        if (parentContext.isRunningSilent() &&
                (Objects.isNull(parentContext.getClientName()) ||
                        Objects.isNull(parentContext.getProjectName()))) {
            System.err.println("If running in the headless mode, at least the --client " +
                    "and --project parameters must be provided. The starting sequence number " +
                    "is optional.");
            System.exit(-1);
        }
    }
}

