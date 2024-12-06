package com.example.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.example.PropertyGraph;

/**
 * Utility class to manage the creation and behavior of buttons for the GUI.
 */
public class ButtonManager {

    /**
     * Adds buttons to the given panel.
     *
     * @param panel       The panel to add buttons to.
     * @param imageWidth  The width of the background image.
     * @param imageHeight The height of the background image.
     * @param frame       The main application frame.
     * @param propertyManager The PropertyManager instance to handle property operations.
     */
    public void addButtonsToPanel(JPanel panel, int imageWidth, int imageHeight, JFrame frame, PropertyManager propertyManager) {
        int buttonWidth = 150;
        int buttonHeight = 40;
        int spacing = 10;
        int startX = (imageWidth - buttonWidth) / 2;
        int startY = (imageHeight / 2) - ((buttonHeight + spacing) * 6) / 2;

        // Adding action buttons to the panel
        addButton(panel, "Details", startX, startY, buttonWidth, buttonHeight, propertyManager);
        addButton(panel, "Property Map", startX, startY + (buttonHeight + spacing) * 1, buttonWidth, buttonHeight, propertyManager);
        addButton(panel, "Area", startX, startY + (buttonHeight + spacing) * 2, buttonWidth, buttonHeight, propertyManager);
        addButton(panel, "Owner Graph", startX, startY + (buttonHeight + spacing) * 3, buttonWidth, buttonHeight, propertyManager);
        addButton(panel, "Suggestion", startX, startY + (buttonHeight + spacing) * 4, buttonWidth, buttonHeight, propertyManager);
        addButton(panel, "Extra", startX, startY + (buttonHeight + spacing) * 5, buttonWidth, buttonHeight, propertyManager);
        addCloseButton(panel, "Close", startX, startY + (buttonHeight + spacing) * 6 + 20, buttonWidth, buttonHeight, frame);
    }

    /**
     * Adds a button to the given panel.
     *
     * @param panel  The panel to add the button to.
     * @param text   The text displayed on the button.
     * @param x      The x-coordinate of the button's position.
     * @param y      The y-coordinate of the button's position.
     * @param width  The width of the button.
     * @param height The height of the button.
     * @param propertyManager The PropertyManager instance.
     */
    private void addButton(JPanel panel, String text, int x, int y, int width, int height, PropertyManager propertyManager) {
        JButton button = new JButton(text);
        button.setBounds(x, y, width, height);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(211, 211, 211)); // Light gray background
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorderPainted(false);
        button.setFocusable(false);
        button.addActionListener(createButtonActionListener(text, panel, propertyManager));
        panel.add(button);
    }

    /**
     * Adds a close button to the given panel.
     *
     * @param panel  The panel to add the button to.
     * @param text   The text displayed on the button.
     * @param x      The x-coordinate of the button's position.
     * @param y      The y-coordinate of the button's position.
     * @param width  The width of the button.
     * @param height The height of the button.
     * @param frame  The frame to close when the button is clicked.
     */
    private void addCloseButton(JPanel panel, String text, int x, int y, int width, int height, JFrame frame) {
        JButton button = new JButton(text);
        button.setBounds(x, y, width, height);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(211, 211, 211)); // Light gray background
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setForeground(Color.BLACK); // Black text
        button.setBorderPainted(false);
        button.setFocusable(false);
        button.addActionListener(e -> frame.dispose());
        panel.add(button);
    }

    /**
     * Creates an ActionListener for each button based on its text label.
     *
     * @param text  The text label of the button.
     * @param panel The panel that contains the button.
     * @param propertyManager The PropertyManager instance.
     * @return The ActionListener for the button.
     */
    private ActionListener createButtonActionListener(String text, JPanel panel, PropertyManager propertyManager) {
        return e -> {
            switch (text) {
                case "Details":
                    handleDetailsAction(panel, propertyManager);
                    break;
                case "Property Map":
                    handlePropertyMapAction(panel, propertyManager);
                    break;
                case "Area":
                    handleAreaAction(panel, propertyManager);
                    break;
                case "Owner Graph":
                    PropertyGraph.Exercise_5();
                    break;
                case "Suggestion":
                    handleSuggestionAction(panel, propertyManager);
                    break;
                case "Extra":
                    handleExtraAction(panel, propertyManager);
                    break;
            }
        };
    }

    // Methods to handle button actions (implementations omitted for brevity)
    private void handleDetailsAction(JPanel panel, PropertyManager propertyManager) {
        // Implement action for Details button
    }

    private void handlePropertyMapAction(JPanel panel, PropertyManager propertyManager) {
        // Implement action for Property Map button
    }

    private void handleAreaAction(JPanel panel, PropertyManager propertyManager) {
        // Implement action for Area button
    }

    private void handleSuggestionAction(JPanel panel, PropertyManager propertyManager) {
        // Implement action for Suggestion button
    }

    private void handleExtraAction(JPanel panel, PropertyManager propertyManager) {
        // Implement action for Extra button
    }
}
