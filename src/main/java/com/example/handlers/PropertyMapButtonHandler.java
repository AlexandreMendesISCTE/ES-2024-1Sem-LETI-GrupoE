package com.example.handlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.example.Property;
import com.example.PropertyGraph;

public class PropertyMapButtonHandler implements ActionListener {

    private final JPanel panel;
    private final List<Property> properties;

    public PropertyMapButtonHandler(JPanel panel, List<Property> properties) {
        this.panel = panel;
        this.properties = properties;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String propertyInput = JOptionPane.showInputDialog(panel, "Which property would you like to see on the map?");
        try {
            int propertyNumber = Integer.parseInt(propertyInput);
            if (propertyNumber > 0 && propertyNumber <= properties.size()) {
                Property property = properties.get(propertyNumber - 1);
                PropertyGraph.Exercise_2(property.getObjectId());
            } else {
                JOptionPane.showMessageDialog(panel, "Property not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(panel, "Invalid input. Please enter a valid property number.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
