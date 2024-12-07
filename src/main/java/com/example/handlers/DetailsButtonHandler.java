package com.example.handlers;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import com.example.Property;
import com.example.utils.PropertyAdjacencyUtils;

/**
 * Handles the action of the "Details" button click.
 * This class is responsible for displaying detailed information about a specific property
 * based on the user's selection.
 */
public class DetailsButtonHandler implements ActionListener {

    private final JPanel panel;
    private final List<Property> properties;

    /**
     * Constructor for DetailsButtonHandler.
     *
     * @param panel      The panel that contains the button.
     * @param properties The list of properties loaded from the CSV file.
     */
    public DetailsButtonHandler(JPanel panel, List<Property> properties) {
        this.panel = panel;
        this.properties = properties;
    }

    /**
     * Invoked when the "Details" button is clicked.
     * Prompts the user to input a property number and displays its details if valid.
     *
     * @param e The ActionEvent triggered by the button click.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Prompt user for the property number
        String propertyInput = JOptionPane.showInputDialog(panel, "Which property would you like more details on?");
        try {
            int propertyNumber = Integer.parseInt(propertyInput); // Parse the input to an integer
            if (propertyNumber > 0 && propertyNumber <= properties.size()) {
                // Retrieve and display property details
                Property property = properties.get(propertyNumber - 1);
                showPropertyDetails(property);
            } else {
                // Handle invalid property number
                JOptionPane.showMessageDialog(panel, "Property not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            // Handle invalid input format
            JOptionPane.showMessageDialog(panel, "Invalid input. Please enter a valid property number.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Displays the detailed information of a specific property in a new frame.
     *
     * @param property The property whose details are to be displayed.
     */
    private void showPropertyDetails(Property property) {
        // Create a new frame to display property details
        JFrame detailFrame = new JFrame("Property Details");
        detailFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        detailFrame.setSize(800, 600);
        detailFrame.setLayout(new BorderLayout());

        // Add a title label
        JLabel titleLabel = new JLabel("Property " + property.getObjectId(), SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        detailFrame.add(titleLabel, BorderLayout.NORTH);

        // Create a panel to hold property details
        JPanel detailPanel = new JPanel();
        detailPanel.setLayout(new BorderLayout());

        // Gather property data and adjacent property information
        String adjacentProperties = getAdjacentProperties(property);
        String[][] propertyData = {
                {"Object ID", property.getObjectId()},
                {"Parcel ID", property.getParId()},
                {"Parcel Number", property.getParNum()},
                {"Shape Length", property.getShapeLength()},
                {"Shape Area", property.getShapeArea()},
                {"Geometry", property.getGeometry()},
                {"Owner", property.getOwner()},
                {"Freguesia", property.getFreguesia()},
                {"Municipio", property.getMunicipio()},
                {"Ilha", property.getIlha()},
                {"Adjacencies", adjacentProperties}
        };
        String[] columnNames = {"Attribute", "Value"};

        // Create a table to display property data
        JTable propertyTable = new JTable(propertyData, columnNames);
        JScrollPane tableScrollPane = new JScrollPane(propertyTable);
        detailPanel.add(tableScrollPane, BorderLayout.CENTER);

        // Add a "Back" button to close the detail frame
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.addActionListener(e -> {
            detailFrame.dispose();
            panel.setVisible(true); // Return to the main panel
        });

        // Add the button to a panel and include it in the detail frame
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        detailPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Finalize and display the detail frame
        detailFrame.add(detailPanel);
        detailFrame.setLocationRelativeTo(null); // Center the frame on the screen
        detailFrame.setVisible(true);
    }

    /**
     * Retrieves a list of properties adjacent to the given property.
     *
     * @param property The property to find adjacent properties for.
     * @return A string containing the Object IDs of adjacent properties, or "None" if no adjacencies are found.
     */
    private String getAdjacentProperties(Property property) {
        // Build a list of adjacent property IDs
        StringBuilder adjacencyInfo = new StringBuilder();
        for (Property otherProperty : properties) {
            if (!property.equals(otherProperty) && PropertyAdjacencyUtils.areAdjacent(property, otherProperty)) {
                if (adjacencyInfo.length() > 0) {
                    adjacencyInfo.append(", ");
                }
                adjacencyInfo.append(otherProperty.getObjectId());
            }
        }
        // Return the list of adjacencies or "None" if no adjacencies are found
        return adjacencyInfo.length() > 0 ? adjacencyInfo.toString() : "None";
    }
}
