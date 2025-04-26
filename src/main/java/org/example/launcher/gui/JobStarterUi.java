package org.example.launcher.gui;

import org.example.JobArgsSource;
import org.example.util.ExceptionUtils;
import org.example.util.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;

public class JobStarterUi implements JobArgsSource {

    private TextField projectName;
    private TextField startSeqNum;
    private TextArea progressDisplay;
    private TextField clientName;
    private Frame frame;

    void buildTheGui(JobArgsSource preFill, ActionListener onStartButton) {
        frame = new Frame("Label and TextField Example");

        clientName = makeNewTextField("Client: ", preFill.getClientName());
        projectName = makeNewTextField("Project: ", preFill.getProjectName());

        String seqNumAsString = "" + preFill.getStartingSequenceNumber();
        startSeqNum = makeNewTextField("Start Sequence Num: ", seqNumAsString);

        makeTheStartButton(onStartButton);
        makeTheOutputTextarea();
    }

    void displayError(Exception e) {
        String trace = ExceptionUtils.getStackTraceAsString(e);
        displayMessage("Unhandled Exception at startTheGui:\n");
        displayMessage(trace);

        JOptionPane.showMessageDialog(frame,
                e.getMessage() + "\n\n" + trace, "Oh, My.", JOptionPane.ERROR_MESSAGE);

    }

    void displayMessage(String message) {
        progressDisplay.append(message);
    }

    public String getClientName() {
        return clientName.getText();
    }

    public String getProjectName() {
        return projectName.getText();
    }

    public int getStartingSequenceNumber() {
        try {
            return Integer.parseInt(startSeqNum.getText());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("\nThe Starting Sequence Number must be blank " +
                    "or a number greater than zero. Job will default to 1."); //TODO move the 2nd sentence to caller
        }
    }

    private TextField makeNewTextField(String labelText, String defaultText) {
        TextField field = makeNewTextField(labelText);
        if (!StringUtils.isNullOrEmpty(defaultText)) field.setText(defaultText);
        return field;
    }

    @SuppressWarnings("MagicNumber")
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

    @SuppressWarnings("MagicNumber")
    void startTheGui() {
        frame.setSize(600, 800);
        frame.setLayout(new FlowLayout());
        frame.addWindowListener(
                new OnCloseHandler());
        frame.setVisible(true);
    }

    private static class OnCloseHandler extends WindowAdapter {
        @SuppressWarnings("unused")
        public void windowClosing(ComponentEvent ignoredE) {
            System.exit(0);
        }
    }
}
