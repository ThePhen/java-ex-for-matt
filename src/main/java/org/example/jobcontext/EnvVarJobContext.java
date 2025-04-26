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
        String val = env.get("EX_CLIENT");
        if (Objects.nonNull(val)) return val;
        return next.getClientName();
    }

    @Override
    public String getProjectName() {
        String val = env.get("EX_PROJECT");
        if (Objects.nonNull(val)) return val;
        return next.getProjectName();
    }

    @Override
    public int getStartingSequenceNumber() {
        try {
            String val = env.get("EX_START_SEQ_NUM");
            if (Objects.nonNull(val)) return Integer.parseInt(val);
            return next.getStartingSequenceNumber();
        } catch (Exception ex) {
            throw new IllegalArgumentException("Trouble handling the value in env-var EX_START_SEQ_NUM.", ex);
        }
    }

    @Override
    public String getUserHomePath() {
        String val = env.get("EX_USER_HOME");
        if (Objects.nonNull(val)) return val;
        return next.getClientName();
    }

    public boolean isRunningSilent() {
        try {
            String val = env.get("EX_RUN_SILENT");
            if (Objects.nonNull(val)) return Boolean.parseBoolean(val);
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
        @SuppressWarnings("unused")
        String get(String name);
    }
}
