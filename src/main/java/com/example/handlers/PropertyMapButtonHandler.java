package com.example.handlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.example.Property;
import com.example.PropertyGraph;

/**
 * Handles the action when the "Property Map" button is clicked.
 * Prompts the user to select a property by number and displays its location on the map.
 */
public class PropertyMapButtonHandler implements ActionListener {

    private final JPanel panel;
    private final List<Property> properties;

    /**
     * Constructor for PropertyMapButtonHandler.
     *
     * @param panel      The panel containing the button that triggers this handler.
     * @param properties The list of properties loaded from the CSV file.
     */
    public PropertyMapButtonHandler(JPanel panel, List<Property> properties) {
        this.panel = panel;
        this.properties = properties;
    }

    /**
     * Invoked when the "Property Map" button is clicked.
     * Prompts the user for a data entry (property number), validates the input,
     * and displays the corresponding property on the map.
     *
     * @param e The ActionEvent triggered by the button click.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Prompt the user to enter the property number they want to view
        String propertyInput = JOptionPane.showInputDialog(panel, "Which property would you like to see on the map?");
        try {
            // Convert the user input to an integer
            int propertyNumber = Integer.parseInt(propertyInput);

            // Check if the entered number corresponds to a valid property in the list
            if (propertyNumber > 0 && propertyNumber <= properties.size()) {
                // Retrieve the selected property
                Property property = properties.get(propertyNumber - 1);

                // Display the property on the map using its Object ID
                PropertyGraph.Exercise_2(property.getObjectId());
            } else {
                // Show an error message if the property number is invalid
                JOptionPane.showMessageDialog(panel, "Property not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            // Show an error message if the input is not a valid number
            JOptionPane.showMessageDialog(panel, "Invalid input. Please enter a valid property number.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
