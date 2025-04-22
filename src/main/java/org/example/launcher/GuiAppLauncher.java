package org.example.launcher;

import org.example.JobProcessor;
import org.example.jobcontext.JobContext;
import org.example.util.ExceptionUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GuiAppLauncher implements Launcher {
    private final GuiJobContext guiJobContext;
    private final JobContext parentJobContext;
    private Frame frame;
    private TextField projectTextField;
    private TextField startSequenceNumTextField;
    private TextArea outputTextArea;
    private TextField clientTextField;

    public GuiAppLauncher(JobContext next) {
        parentJobContext = next;
        guiJobContext = new GuiJobContext(next);
    }

    private void buildAndStartTheGui() {
        frame = new Frame("Label and TextField Example");

        clientTextField = makeNewTextField("Client: ",
                parentJobContext.getClientName());
        projectTextField = makeNewTextField("Project: ",
                parentJobContext.getProjectName());
        startSequenceNumTextField = makeNewTextField("Start Sequence Num: ", "" +
                parentJobContext.getStartingSequenceNumber());
        makeTheStartButton();
        makeTheOutputTextarea();

        frame.setSize(600, 800);
        frame.setLayout(new FlowLayout());
        frame.addWindowListener(
                new WindowAdapter() {
                    public void windowClosing(WindowEvent windowEvent) {
                        System.exit(0);
                    }
                });
        frame.setVisible(true);
    }

    private void guiCentricErrorHandler(Exception e) {
        String trace = ExceptionUtils.getStackTraceAsString(e);
        guiJobContext.logProgress("Unhandled Exception at startTheGui:\n");
        guiJobContext.logProgress(trace);

        System.err.println("Unhandled Exception at startTheGui:" +
                "\n" + e + "\n" + trace + "\n");

        JOptionPane.showMessageDialog(frame, e.getMessage() +
                "\n\n" + trace, "Oh, My.", JOptionPane.ERROR_MESSAGE);

        System.exit(-1);
    }

    private boolean isNullOrEmpty(String s) {
        return (s == null || s.trim().isEmpty());
    }

    private TextField makeNewTextField(String labelText, String defaultText) {
        final TextField field = makeNewTextField(labelText);
        if (!isNullOrEmpty(defaultText)) field.setText(defaultText);
        return field;
    }

    private TextField makeNewTextField(String labelText) {
        Label clientLabel = new Label(labelText);
        frame.add(clientLabel);
        TextField newComponent = new TextField(20);
        frame.add(newComponent);
        return newComponent;
    }

    private void makeTheOutputTextarea() {
        outputTextArea = new TextArea("Output to appear here.\n");
        outputTextArea.setEditable(false);
        frame.add(outputTextArea);
    }

    private void makeTheStartButton() {
        Button startProcessBtn = new Button("Start Processing");
        startProcessBtn.addActionListener(e -> {
            try {
                new JobProcessor(guiJobContext).startProcessing();
            } catch (Exception ex) {
                guiCentricErrorHandler(new RuntimeException("Unhandled error during processing.", ex));
            }
        });
        frame.add(startProcessBtn);
    }

    public void start() {
        try {
            buildAndStartTheGui();
        } catch (Exception e) {
            guiCentricErrorHandler(new RuntimeException("Unhandled error within the GUI.", e));
        }
    }

    private class GuiJobContext implements JobContext {
        final JobContext next;

        public GuiJobContext(JobContext next) {
            this.next = next;
        }

        public String getClientName() {
            if (isNullOrEmpty(clientTextField.getText())) return next.getClientName();
            return clientTextField.getText().trim();
        }

        public String getProjectName() {
            final String s = projectTextField.getText();
            if (isNullOrEmpty(s)) return next.getProjectName();
            return s.trim();
        }

        public int getStartingSequenceNumber() {
            final String fieldText = startSequenceNumTextField.getText();
            if (isNullOrEmpty(fieldText)) return next.getStartingSequenceNumber();
            try {
                return Integer.parseInt(fieldText);
            } catch (NumberFormatException e) {
                logProgress("\nThe Starting Sequence Number must be blank " +
                        "or a number greater than zero. Job will default to 1.");
                return 1;
            }
        }

        public String getUserHomePath() {
            return next.getUserHomePath();
        }

        public boolean isRunningSilent() {
            return next.isRunningSilent();
        }

        public void logProgress(String message) {
            outputTextArea.append("\n" + message);
        }
    }
}
