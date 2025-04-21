package org.example.jobcontext;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(separators = "=")
public class CmdArgsJobContext extends BaseJobContext {

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
    private String userHomePath = System.getProperty("user.home");

    @Parameter(names = {"-h", "--help"}, description = "Print help message", help = true)
    private boolean help = false;

    public CmdArgsJobContext(String[] args) {
        JCommander jCommander = JCommander.newBuilder()
                .addObject(this)
                .build();
        jCommander.parse(args);

        if (this.help) {
            jCommander.usage();
            System.exit(0);
        }
    }

    @Override
    public String getClientName() {
        return clientName;
    }

    @Override
    public String getProjectName() {
        return projectName;
    }

    @Override
    public int getStartingSequenceNumber() {
        return Math.min(10, Math.max(startSeqNum, 1));
    }

    String getUserHomePath() {
        return userHomePath;
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