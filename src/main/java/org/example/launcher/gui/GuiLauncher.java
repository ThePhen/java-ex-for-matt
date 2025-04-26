package org.example.launcher.gui;

import org.example.JobArgsSource;
import org.example.JobProcessor;
import org.example.jobcontext.JobContext;
import org.example.launcher.Launcher;

import java.io.FileNotFoundException;

public class GuiLauncher implements Launcher {
    private final JobContext ctx;
    private final JobArgsSource args;
    private final JobStarterUi theUi;

    public GuiLauncher(JobContext next) {
        args = next;
        theUi = new JobStarterUi();
        ctx = new GuiJobContext(theUi, next);
    }

    public void start() {
        try {
            theUi.buildTheGui(args, e -> startProcessing());
            theUi.startTheGui();
        } catch (Exception e) {
            theUi.displayError(new RuntimeException("Unhandled error within the GUI.", e));
            System.exit(-1);
        }
    }

    private void startProcessing() {
        try {
            JobProcessor job = new JobProcessor(ctx);
            job.startProcessing();
        } catch (IllegalArgumentException ex) {
            theUi.displayError(new RuntimeException("Re-check the inputs and try again.", ex));
        } catch (Exception ex) {
            theUi.displayError(new RuntimeException("Unhandled error during processing.", ex));
            if (!(ex.getCause() instanceof FileNotFoundException)) System.exit(-1);
        }
    }
}
