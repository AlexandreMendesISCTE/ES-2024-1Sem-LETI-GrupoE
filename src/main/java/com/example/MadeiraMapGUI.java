package com.example;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.example.utils.ButtonHandlerFactory;

/**
 * The MadeiraMapGUI class represents the main graphical user interface (GUI) for the Madeira Property Map system.
 * This GUI allows users to interact with property data through buttons for various functionalities, 
 * such as viewing details, generating graphs, and suggestions.
 */
public class MadeiraMapGUI {

    // Instance Variables
    private final List<Property> properties; // List of properties loaded from the CSV
    private JFrame frame;                    // Main application frame

    /**
     * Constructor for MadeiraMapGUI.
     * Initializes the list of properties by reading from a CSV file using {@link CsvToPropertyReader}.
     */
    public MadeiraMapGUI() {
        properties = CsvToPropertyReader.Exercise_1();
    }

    /**
     * Creates and displays the main GUI.
     * Sets up the JFrame with a background image, buttons, and basic configurations.
     */
    public void createAndShowGUI() {
        if (frame == null) {
            frame = new JFrame();
            frame.setUndecorated(true); // Removes the title bar for a clean UI
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setIconImage(new ImageIcon(getClass().getResource("/madeira_icon.png")).getImage()); // Sets the application icon
        }

        // Load the background image to define the frame's size
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/madeira.jpg"));
        int imageWidth = imageIcon.getIconWidth();
        int imageHeight = imageIcon.getIconHeight();
        frame.setSize(imageWidth, imageHeight);

        frame.setResizable(false);
        frame.setLocationRelativeTo(null); // Centers the frame on the screen

        // Main panel with the background image
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Draw the background image to fill the panel
                g.drawImage(imageIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel.setLayout(null); // Allows absolute positioning of components

        // Add buttons to the panel
        addButtonsToPanel(panel, imageWidth, imageHeight);

        // Configure and display the frame
        frame.getContentPane().removeAll();
        frame.add(panel);
        frame.revalidate();
        frame.repaint();
        frame.setVisible(true);
    }

    /**
     * Adds a set of buttons to the main panel.
     * Each button represents a specific action, such as viewing details or generating graphs.
     *
     * @param panel       The panel where the buttons will be added.
     * @param imageWidth  The width of the background image.
     * @param imageHeight The height of the background image.
     */
    private void addButtonsToPanel(JPanel panel, int imageWidth, int imageHeight) {
        int buttonWidth = 150;   // Standard button width
        int buttonHeight = 40;   // Standard button height
        int spacing = 10;        // Space between buttons
        int startX = (imageWidth - buttonWidth) / 2; // Center buttons horizontally
        int startY = (imageHeight / 2) - ((buttonHeight + spacing) * 6) / 2; // Vertically align buttons

        // Define button labels for various actions
        String[] buttonLabels = {"Details", "Property Map", "Area", "Owner Graph", "Suggestion", "Extra", "Close"};
        for (int i = 0; i < buttonLabels.length; i++) {
            // Create and add each button to the panel
            JButton button = createButton(buttonLabels[i], startX, startY + (buttonHeight + spacing) * i, buttonWidth, buttonHeight);
            panel.add(button);
        }
    }

    /**
     * Creates a new JButton with consistent styling and an associated action handler.
     *
     * @param text   The text displayed on the button.
     * @param x      The x-coordinate of the button's position.
     * @param y      The y-coordinate of the button's position.
     * @param width  The width of the button.
     * @param height The height of the button.
     * @return A styled JButton instance.
     */
    private JButton createButton(String text, int x, int y, int width, int height) {
        JButton button = new JButton(text);
        button.setBounds(x, y, width, height);               // Set position and size
        button.setFont(new Font("Arial", Font.BOLD, 16));    // Set font style and size
        button.setBackground(new Color(211, 211, 211));      // Light gray background
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorderPainted(false);
        button.setFocusable(false);
        // Attach action listener using ButtonHandlerFactory
        button.addActionListener(ButtonHandlerFactory.createHandler(text, null, properties, frame));
        return button;
    }

    /**
     * Main method to launch the GUI application.
     * Initializes the MadeiraMapGUI and displays the main interface.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MadeiraMapGUI().createAndShowGUI());
    }
}
