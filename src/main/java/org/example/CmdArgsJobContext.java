package org.example;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(separators = "=")
class CmdArgsJobContext implements JobContext {
    @Parameter(names = {"-run-silent", "--run-silent"}, description = "When this flag is present, the job will " +
            "run without any user intervention required. Used for job automation.")
    private boolean runSilent = false;

    @Parameter(names = {"-h", "--help"}, description = "Print help message", help = true)
    private boolean help = false;

    @Parameter(names = {"-c", "--client"}, description = "The Client name to process")
    private String clientName = null;

    @Parameter(names = {"-p", "--project"}, description = "The Client's Project to process")
    private String projectName = null;

    @Parameter(names = {"-s", "--start-num"}, description = "The sequence number of the first record to process")
    private int startSeqNum = 1;

    private CmdArgsJobContext() {
    }

    public static JobContext make(String[] args) {
        CmdArgsJobContext ctx = new CmdArgsJobContext();
        JCommander jCommander = JCommander.newBuilder()
                .addObject(ctx)
                .build();
        jCommander.parse(args);

        if (ctx.help) {
            jCommander.usage();
            System.exit(0);
        }

        return ctx;
    }

    @Override
    public String getProjectName() {
        return projectName;
    }

    @Override
    public String getClientName() {
        return clientName;
    }

    @Override
    public int getStartingSequenceNumber() {
        return Math.min(10, Math.max(startSeqNum, 1));
    }

    @Override
    public boolean isRunningSilent() {
        return runSilent;
    }

    @Override
    public void logProgress(String message) {
        System.out.println(message);
    }
}