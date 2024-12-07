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
 * Handles the action when the "Suggestion" button is clicked.
 * This class generates and displays property swap suggestions for a selected "Freguesia".
 */
public class SuggestionButtonHandler implements ActionListener {

    private final JPanel panel;
    private final List<Property> properties;

    /**
     * Constructor for SuggestionButtonHandler.
     *
     * @param panel      The panel that contains the button.
     * @param properties The list of properties loaded from the CSV file.
     */
    public SuggestionButtonHandler(JPanel panel, List<Property> properties) {
        this.panel = panel;
        this.properties = properties;
    }

    /**
     * Invoked when the "Suggestion" button is clicked.
     * Prompts the user to select a "Freguesia", then generates and displays swap suggestions in a new window.
     *
     * @param e The ActionEvent triggered by the button click.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Retrieve all unique "Freguesias" from the properties list
        Set<String> uniqueFreguesias = properties.stream()
                .map(Property::getFreguesia)
                .collect(Collectors.toSet());

        // Prompt the user to select a "Freguesia"
        String freguesiaInput = (String) JOptionPane.showInputDialog(
                panel,
                "Select a Freguesia:",
                "Freguesia Selection",
                JOptionPane.QUESTION_MESSAGE,
                null,
                uniqueFreguesias.toArray(),
                uniqueFreguesias.iterator().next() // Default selection
        );

        if (freguesiaInput != null) {
            // Create a new JFrame to display the suggestions
            JFrame suggestionFrame = new JFrame("Suggestion Console");
            suggestionFrame.setSize(600, 400);
            suggestionFrame.setLocationRelativeTo(null); // Center the frame
            suggestionFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            // Create a non-editable text area with a scroll pane
            JTextArea textArea = new JTextArea();
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);
            suggestionFrame.add(scrollPane);

            // Make the frame visible
            suggestionFrame.setVisible(true);

            // Redirect System.out to capture console output
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PrintStream printStream = new PrintStream(outputStream);
            PrintStream originalOut = System.out; // Backup original System.out

            try {
                // Redirect standard output and error streams to capture log messages
                System.setOut(printStream);
                System.setErr(printStream);

                // Log the Freguesia being processed
                System.out.println("Generating swap suggestions for Freguesia: " + freguesiaInput);

                // Call the method to generate swap suggestions
                PropertySwapSuggestion.Exercise_6(freguesiaInput);

                // Display captured output in the text area
                textArea.setText(outputStream.toString());
            } finally {
                // Restore the original System.out stream
                System.setOut(originalOut);
            }
        }
    }
}
