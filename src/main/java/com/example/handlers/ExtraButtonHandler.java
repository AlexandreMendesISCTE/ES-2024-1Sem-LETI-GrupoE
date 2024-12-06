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
 * ExtraButtonHandler handles the action when the "Extra" button is clicked.
 * This class allows users to select and draw two properties on a graph.
 */
public class ExtraButtonHandler implements ActionListener {

    private JPanel panel;
    private List<Property> properties;

    /**
     * Constructor for ExtraButtonHandler.
     *
     * @param panel The panel that contains the button.
     * @param properties The list of properties loaded from the CSV file.
     */
    public ExtraButtonHandler(JPanel panel, List<Property> properties) {
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
            List<Property> filteredProperties = properties.stream()
                    .filter(p -> p.getFreguesia().equalsIgnoreCase(freguesiaInput))
                    .collect(Collectors.toList());

            Set<String> objectIds = filteredProperties.stream().map(Property::getObjectId).collect(Collectors.toSet());

            String property1Input = (String) JOptionPane.showInputDialog(
                    panel,
                    "Select the first property:",
                    "Property Selection",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    objectIds.toArray(),
                    objectIds.iterator().next()
            );

            String property2Input = (String) JOptionPane.showInputDialog(
                    panel,
                    "Select the second property:",
                    "Property Selection",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    objectIds.toArray(),
                    objectIds.iterator().next()
            );

            if (property1Input != null && property2Input != null) {
                JOptionPane.showMessageDialog(
                        panel,
                        "You selected Property " + property1Input + " and Property " + property2Input,
                        "Selected Properties",
                        JOptionPane.INFORMATION_MESSAGE
                );

                PropertyGeometryPlotter plotter = new PropertyGeometryPlotter("Selected Property Geometries");
                plotter.drawProperties(properties, freguesiaInput, Integer.parseInt(property1Input), Integer.parseInt(property2Input));
            }
        }
    }
}
