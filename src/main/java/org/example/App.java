package org.example;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class App {
    public static void main(String[] args) {
        Frame frame = new Frame("Label and TextField Example");

        Label clientLabel = new Label("Client: ");
        frame.add(clientLabel);
        TextField clientTextField = new TextField(20);
        frame.add(clientTextField);

        Label projectLabel = new Label("Project: ");
        frame.add(projectLabel);
        TextField projectTextField = new TextField(20);
        frame.add(projectTextField);

        Label startSequenceNumLabel = new Label("Start Sequence Num: ");
        frame.add(startSequenceNumLabel);
        TextField startSequenceNumTextField = new TextField(20);
        frame.add(startSequenceNumTextField);

        Label dropdownLabel = new Label("Useless Dropdown: ");
        frame.add(dropdownLabel);
        String[] projectsForClientAry = {"Item 1", "Item 2", "Item 3", "Item 4", "Item 5"};
        java.util.List<String> arrayList1 = new ArrayList<>(Arrays.asList(projectsForClientAry));

        Choice l1 = new Choice();
        arrayList1.forEach(l1::add);
        frame.add(l1);

        Button startProcess = new Button("Start Processing");
        frame.add(startProcess);

        TextArea outputTextArea = new TextArea("Output to appear here.");
        outputTextArea.setEditable(false);
        frame.add(outputTextArea);

        frame.setSize(600, 800);
        frame.setLayout(new FlowLayout());
        frame.setVisible(true);
    }
}