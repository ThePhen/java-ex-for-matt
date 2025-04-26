package org.example.jobcontext;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

import java.util.Objects;

public class CmdArgsJobContext implements JobContext {

    private final JobContext next;

    private final ParmsFromCommandLine parms = new ParmsFromCommandLine();

    public CmdArgsJobContext(String[] args, JobContext next) {
        this.next = next;
        JCommander jCommander = JCommander.newBuilder()
                .addObject(parms)
                .build();
        jCommander.parse(args);

        if (parms.help) {
            jCommander.usage();
            System.exit(0);
        }
    }

    public String getClientName() {
        if (Objects.isNull(parms.clientName)) return next.getClientName();
        return parms.clientName;
    }

    public String getProjectName() {
        if (Objects.isNull(parms.projectName)) return next.getProjectName();
        return parms.projectName;
    }

    public int getStartingSequenceNumber() {
        return Math.min(10, Math.max(parms.startSeqNum, 1));
    }

    public String getUserHomePath() {
        if (Objects.isNull(parms.userHomePath)) return next.getUserHomePath();
        return parms.userHomePath;
    }

    public boolean isRunningSilent() {
        return parms.runSilent;
    }

    public void logProgress(String message) {
        next.logProgress(message);
    }

    @Parameters(separators = "=")
    static class ParmsFromCommandLine {
        @Parameter(names = {"-run-silent", "--run-silent"}, description = "When this flag is present, the job will " +
                "run without any user intervention required. Used for job automation.")
        private boolean runSilent = false;

        @Parameter(names = {"-c", "--client"}, description = "The Client name to process")
        private String clientName = null;

        @Parameter(names = {"-p", "--project"}, description = "The Client's Project to process")
        private String projectName = null;

        @Parameter(names = {"-s", "--start-num"}, description = "The sequence number of the first record to process")
        private int startSeqNum = 1;

        @Parameter(names = {"--user-home"}, description = "The path to the user's home. By default, the value " +
                "the OS provides is used.")
        private String userHomePath = null;

        @Parameter(names = {"-h", "--help"}, description = "Print help message", help = true)
        private boolean help = false;
    }
}