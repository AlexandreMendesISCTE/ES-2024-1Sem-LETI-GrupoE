package com.example.handlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.example.Property;
import com.example.PropertySwapSuggestion;

/**
 * SuggestionButtonHandler handles the action when the "Suggestion" button is clicked.
 * This class displays property swap suggestions.
 */
public class SuggestionButtonHandler implements ActionListener {

    private JPanel panel;
    private List<Property> properties;

    /**
     * Constructor for SuggestionButtonHandler.
     *
     * @param panel The panel that contains the button.
     * @param properties The list of properties loaded from the CSV file.
     */
    public SuggestionButtonHandler(JPanel panel, List<Property> properties) {
        this.panel = panel;
        this.properties = properties;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Set<String> uniqueFreguesias = properties.stream().map(Property::getFreguesia).collect(Collectors.toSet());
        String freguesiaInput = (String) JOptionPane.showInputDialog(
                panel,
                "Select a Freguesia:",
                "Freguesia Selection",
                JOptionPane.QUESTION_MESSAGE,
                null,
                uniqueFreguesias.toArray(),
                uniqueFreguesias.iterator().next()
        );

        if (freguesiaInput != null) {
            JFrame suggestionFrame = new JFrame("Suggestion Console");
            suggestionFrame.setSize(600, 400);
            suggestionFrame.setLocationRelativeTo(null);
            suggestionFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            JTextArea textArea = new JTextArea();
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);
            suggestionFrame.add(scrollPane);

            suggestionFrame.setVisible(true);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PrintStream printStream = new PrintStream(outputStream);
            PrintStream originalOut = System.out;

            try {
                System.setOut(printStream);
                System.setErr(printStream);

                System.out.println("Generating swap suggestions for Freguesia: " + freguesiaInput);
                PropertySwapSuggestion.Exercise_6(freguesiaInput);

                textArea.setText(outputStream.toString());
            } finally {
                System.setOut(originalOut);
            }
        }
    }
}
