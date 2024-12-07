package com.example.handlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.example.Property;
import com.example.PropertyGeometryPlotter;

/**
 * Handles the action of the "Extra" button click.
 * Allows users to select two properties from a specified "Freguesia" and displays their geometries on a graph.
 */
public class ExtraButtonHandler implements ActionListener {

    private final JPanel panel;
    private final List<Property> properties;

    /**
     * Constructor for ExtraButtonHandler.
     *
     * @param panel      The panel that contains the button.
     * @param properties The list of properties loaded from the CSV file.
     */
    public ExtraButtonHandler(JPanel panel, List<Property> properties) {
        this.panel = panel;
        this.properties = properties;
    }

    /**
     * Invoked when the "Extra" button is clicked.
     * Prompts the user to select a "Freguesia" and two properties within it, then plots the geometries.
     *
     * @param e The ActionEvent triggered by the button click.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Retrieve all unique "Freguesias" from the properties list
        Set<String> uniqueFreguesias = properties.stream().map(Property::getFreguesia).collect(Collectors.toSet());

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
            // Filter properties based on the selected "Freguesia"
            List<Property> filteredProperties = properties.stream()
                    .filter(p -> p.getFreguesia().equalsIgnoreCase(freguesiaInput))
                    .collect(Collectors.toList());

            // Extract unique Object IDs from the filtered properties
            Set<String> objectIds = filteredProperties.stream().map(Property::getObjectId).collect(Collectors.toSet());

            // Prompt the user to select the first property
            String property1Input = (String) JOptionPane.showInputDialog(
                    panel,
                    "Select the first property:",
                    "Property Selection",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    objectIds.toArray(),
                    objectIds.iterator().next() // Default selection
            );

            // Prompt the user to select the second property
            String property2Input = (String) JOptionPane.showInputDialog(
                    panel,
                    "Select the second property:",
                    "Property Selection",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    objectIds.toArray(),
                    objectIds.iterator().next() // Default selection
            );

            // Validate that both properties were selected
            if (property1Input != null && property2Input != null) {
                // Notify the user of the selected properties
                JOptionPane.showMessageDialog(
                        panel,
                        "You selected Property " + property1Input + " and Property " + property2Input,
                        "Selected Properties",
                        JOptionPane.INFORMATION_MESSAGE
                );

                // Create a PropertyGeometryPlotter instance and plot the selected properties
                PropertyGeometryPlotter plotter = new PropertyGeometryPlotter("Selected Property Geometries");
                plotter.drawProperties(properties, freguesiaInput, Integer.parseInt(property1Input), Integer.parseInt(property2Input));
            }
        }
    }
}
