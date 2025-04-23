package org.example.jobcontext;

import java.util.Objects;

public class EnvVarJobContext implements JobContext {
    private final JobContext next;
    private final GetEnv env;

    public EnvVarJobContext(JobContext next) {
        this(System::getenv, next);
    }

    public EnvVarJobContext(GetEnv env, JobContext next) {
        this.env = env;
        this.next = next;
    }

    @Override
    public String getClientName() {
        String c = env.get("EX_CLIENT");
        if (Objects.nonNull(c)) return c;
        return next.getClientName();
    }

    @Override
    public String getProjectName() {
        String c = env.get("EX_PROJECT");
        if (Objects.nonNull(c)) return c;
        return next.getProjectName();
    }

    @Override
    public int getStartingSequenceNumber() {
        try {
            String c = env.get("EX_START_SEQ_NUM");
            if (Objects.nonNull(c)) return Integer.parseInt(c);
            return next.getStartingSequenceNumber();
        } catch (Exception ex) {
            throw new RuntimeException("Trouble handline the value in env-var EX_START_SEQ_NUM.", ex);
        }
    }

    @Override
    public String getUserHomePath() {
        String c = env.get("EX_USER_HOME");
        if (Objects.nonNull(c)) return c;
        return next.getClientName();
    }

    public boolean isRunningSilent() {
        try {
            String c = env.get("EX_RUN_SILENT");
            if (Objects.nonNull(c)) return Boolean.parseBoolean(c);
            return next.isRunningSilent();
        } catch (Exception ex) {
            throw new RuntimeException("Trouble handling the value in the env-var EX_RUN_SILENT.", ex);
        }
    }

    public void logProgress(String message) {
        next.logProgress(message);
    }

    @FunctionalInterface
    public interface GetEnv {
        String get(String name);
    }
}
