package org.example.launcher;

import org.example.JobContext;
import org.example.JobProcessor;
import org.example.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class GuiAppLauncher implements Launcher {
    private final JobContext parentContext;
    private Frame frame;
    private TextField projectTextField;
    private TextField startSequenceNumTextField;
    private Choice choiceList;
    private TextArea outputTextArea;
    private TextField clientTextField;
    private final JobContext jobContext = getGuiJobContext();

    public GuiAppLauncher(JobContext ctx) {
        parentContext = ctx;
    }

    private void buildAndStartTheGui() {
        frame = new Frame("Label and TextField Example");

        clientTextField = makeNewTextField("Client: ", parentContext.getClientName());
        projectTextField = makeNewTextField("Project: ", parentContext.getProjectName());
        startSequenceNumTextField = makeNewTextField("Start Sequence Num: ");
        makeTheExtraFieldDropdownExample();
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

    private JobContext getGuiJobContext() {
        return new JobContext() {
            @Override
            public String getClientName() {
                return clientTextField.getText();
            }

            @Override
            public String getProjectName() {
                return projectTextField.getText();
            }

            @Override
            public int getStartingSequenceNumber() {
                try {
                    return Integer.parseInt(startSequenceNumTextField.getText());
                } catch (NumberFormatException e) {
                    return 1;
                }
            }

            @Override
            public boolean isRunningSilent() {
                return parentContext.isRunningSilent();
            }

            @Override
            public void logProgress(String message) {
                outputTextArea.append("\n" + message);
            }
        };
    }

    private void guiCentricErrorHandler(Exception e) {
        System.err.println("Unhandled Exception at startTheGui:\n" + e);
        e.printStackTrace();

        String trace = Utils.getStackTraceAsString(e);
        jobContext.logProgress("Unhandled Exception at startTheGui:\n");
        jobContext.logProgress(trace);

        JOptionPane.showMessageDialog(frame, e.getMessage() +
                "\n\n" + trace, "Oh, My.", JOptionPane.ERROR_MESSAGE);

        System.exit(-1);
    }

    private boolean inputsAreGood() {
        final boolean a = isClientNameGood();
        final boolean b = isProjectNameGood();
        final boolean c = isStartSeqNumGood();
        return a && b && c;
    }

    private boolean isClientNameGood() {
        if (isNullOrEmpty(clientTextField.getText())) {
            outputTextArea.append("\nThe Client Name must not be blank.");
            return false;
        }
        return true;
    }

    private boolean isNullOrEmpty(String s) {
        return (s == null || s.trim().isEmpty());
    }

    private boolean isProjectNameGood() {
        if (isNullOrEmpty(projectTextField.getText())) {
            outputTextArea.append("\nThe Project Name must not be blank.");
            return false;
        }
        return true;
    }

    private boolean isStartSeqNumGood() {
        final String s = startSequenceNumTextField.getText();
        if (isNullOrEmpty(s)) return true;
        try {
            return 0 < Integer.parseInt(s);
        } catch (NumberFormatException e) {
            outputTextArea.append("\nThe Starting Sequence Number must be blank " +
                    "or a number greater than zero.");
            return false;
        }
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

    private void makeTheExtraFieldDropdownExample() {
        Label dropdownLabel = new Label("Extra Parameter: ");
        frame.add(dropdownLabel);
        String[] projectsForClientAry = {"Item 1", "Item 2", "Item 3", "Item 4", "Item 5"};
        java.util.List<String> projects = new ArrayList<>(Arrays.asList(projectsForClientAry));
        choiceList = new Choice();
        projects.forEach(choiceList::add); // syntax A
        for (String s : projectsForClientAry) choiceList.add(s); // syntax B
        frame.add(choiceList);
    }

    private void makeTheOutputTextarea() {
        outputTextArea = new TextArea("Output to appear here.");
        outputTextArea.setEditable(false);
        frame.add(outputTextArea);
    }

    private void makeTheStartButton() {
        Button startProcess = new Button("Start Processing");
        startProcess.addActionListener(e -> {
            outputTimestamp();
            if (inputsAreGood()) {
                try {
                    new JobProcessor().startProcessing(jobContext);
                } catch (Exception ex) {
                    guiCentricErrorHandler(new RuntimeException("Unhandled error during processing.", ex));
                }
            }
            outputTimestamp();
            outputTextArea.append("\n");
        });
        frame.add(startProcess);
    }

    private void outputTimestamp() {
        outputTextArea.append("\n=== " + LocalDateTime.now() + " ===");
    }

    public void start() {
        try {
            buildAndStartTheGui();
        } catch (Exception e) {
            guiCentricErrorHandler(new RuntimeException("Unhandled error within the GUI.", e));
        }
    }
}
