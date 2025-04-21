package org.example.launcher;

import org.example.JobContext;
import org.example.JobProcessor;

import java.util.Objects;

public class HeadlessAppLauncher implements Launcher, JobContext {
    private final JobContext parentContext;

    public HeadlessAppLauncher(JobContext ctx) {
        parentContext = ctx;
    }

    public void start() {
        validateNeededInputArgs();
        final JobProcessor runner = new JobProcessor();
        runner.startProcessing(this);
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

    @Override
    public String getProjectName() {
        return "";
    }

    @Override
    public String getClientName() {
        return "";
    }

    @Override
    public int getStartingSequenceNumber() {
        final Integer seqNum = parentContext.getStartingSequenceNumber();
        if (Objects.isNull(seqNum)) return new Integer(1);
        return Math.min(10, Math.max(seqNum.intValue(), 1));
    }

    @Override
    public boolean isRunningSilent() {
        return false;
    }

    @Override
    public void logProgress(String message) {

    }
}
