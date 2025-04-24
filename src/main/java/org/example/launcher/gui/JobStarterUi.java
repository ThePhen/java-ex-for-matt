package org.example.launcher.gui;

import org.example.jobcontext.JobContext;
import org.example.util.ExceptionUtils;
import org.example.util.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

class JobStarterUi {

    TextField projectName = null;
    TextField startSeqNum = null;
    TextArea progressDisplay;
    TextField clientName;
    private Frame frame;

    public void buildTheGui(JobContext nextCtx, ActionListener onStartButton) {
        frame = new Frame("Label and TextField Example");

        clientName = makeNewTextField("Client: ", nextCtx.getClientName());
        projectName = makeNewTextField("Project: ", nextCtx.getProjectName());

        final String seqNumAsString = "" + nextCtx.getStartingSequenceNumber();
        startSeqNum = makeNewTextField("Start Sequence Num: ", seqNumAsString);

        makeTheStartButton(onStartButton);
        makeTheOutputTextarea();
    }

    public void displayError(JobContext ctx, Exception e) {
        String trace = ExceptionUtils.getStackTraceAsString(e);
        ctx.logProgress("Unhandled Exception at startTheGui:\n");
        ctx.logProgress(trace);

        System.err.println("Unhandled Exception at startTheGui:" + "\n" + e + "\n" + trace + "\n");

        JOptionPane.showMessageDialog(frame,
                e.getMessage() + "\n\n" + trace, "Oh, My.", JOptionPane.ERROR_MESSAGE);

    }

    private TextField makeNewTextField(String labelText, String defaultText) {
        final TextField field = makeNewTextField(labelText);
        if (!StringUtils.isNullOrEmpty(defaultText)) field.setText(defaultText);
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
        progressDisplay = new TextArea("Output to appear here.\n");
        progressDisplay.setEditable(false);
        frame.add(progressDisplay);
    }

    private void makeTheStartButton(ActionListener onClick) {
        Button startProcessBtn = new Button("Start Processing");
        startProcessBtn.addActionListener(onClick);
        frame.add(startProcessBtn);
    }

    void startTheGui() {
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
}
