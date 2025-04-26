package org.example.jobcontext;

import org.example.JobArgsSource;
import org.example.ProgressSink;

import java.io.IOException;

public interface JobContext extends JobArgsSource, ProgressSink {

    String getUserHomePath() throws IOException;

    boolean isRunningSilent();
}