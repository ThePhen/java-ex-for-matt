package org.example.launcher.gui;

import org.example.JobProcessor;
import org.example.jobcontext.JobContext;
import org.example.launcher.Launcher;
import org.example.util.StringUtils;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;

public class GuiAppLauncher implements Launcher {
    private final GuiJobContext ctx;
    private final JobContext parentCtx;
    private Frame frame;
    private Gui gui;

    public GuiAppLauncher(JobContext next) {
        parentCtx = next;
        ctx = new GuiJobContext(next);
    }

    public void start() {
        try {
            gui = new Gui(this);
            gui.buildTheGui(parentCtx, e -> {
                try {
                    final JobProcessor job = new JobProcessor(ctx);
                    job.startProcessing();
                } catch (IllegalArgumentException ex) {
                    gui.displayError(ctx, new RuntimeException("Re-check the inputs and try again.", ex));
                } catch (Exception ex) {
                    gui.displayError(ctx, new RuntimeException("Unhandled error during processing.", ex));
                    if (!(ex.getCause() instanceof FileNotFoundException)) System.exit(-1);
                }
            });
            gui.startTheGui();
        } catch (Exception e) {
            gui.displayError(ctx, new RuntimeException("Unhandled error within the GUI.", e));
            System.exit(-1);
        }
    }

    private class GuiJobContext implements JobContext {
        final JobContext next;

        public GuiJobContext(JobContext next) {
            this.next = next;
        }

        public String getClientName() {
            if (StringUtils.isNullOrEmpty(gui.clientName.getText())) return next.getClientName();
            return gui.clientName.getText().trim();
        }

        public String getProjectName() {
            final String s = gui.projectName.getText();
            if (StringUtils.isNullOrEmpty(s)) return next.getProjectName();
            return s.trim();
        }

        public int getStartingSequenceNumber() {
            final String fieldText = gui.startSeqNum.getText();
            if (StringUtils.isNullOrEmpty(fieldText)) return next.getStartingSequenceNumber();
            try {
                return Integer.parseInt(fieldText);
            } catch (NumberFormatException e) {
                logProgress("\nThe Starting Sequence Number must be blank " +
                        "or a number greater than zero. Job will default to 1.");
                return 1;
            }
        }

        public String getUserHomePath() throws IOException {
            return next.getUserHomePath();
        }

        public boolean isRunningSilent() {
            return next.isRunningSilent();
        }

        public void logProgress(String message) {
            gui.progressDisplay.append("\n" + message);
        }
    }
}
