package org.example.launcher.headless;

import org.example.JobProcessor;
import org.example.jobcontext.JobContext;
import org.example.launcher.Launcher;

import java.io.IOException;

public class HeadlessLauncher implements Launcher {
    private final JobContext parentContext;

    public HeadlessLauncher(JobContext ctx) {
        parentContext = ctx;
    }

    private boolean isNullOrEmpty(String s) {
        return (s == null || s.trim().isEmpty());
    }

    public void start() throws IOException {
        validateNeededInputArgs();
        final JobProcessor runner = new JobProcessor(parentContext);
        runner.startProcessing();
    }

    private void validateNeededInputArgs() {
        if (parentContext.isRunningSilent() &&
                (isNullOrEmpty(parentContext.getClientName()) ||
                        isNullOrEmpty(parentContext.getProjectName()))) {
            throw new IllegalArgumentException("If running in the headless mode, at least the --client " +
                    "and --project parameters must be provided. The starting sequence number " +
                    "is optional.");
        }
    }
}

