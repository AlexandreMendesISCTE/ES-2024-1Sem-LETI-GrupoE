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
 * This GUI allows users to view property details, generate graphs, suggestions, and more.
 * It contains a list of properties and various action buttons for different functionalities.
 */
public class MadeiraMapGUI {

    // Instance Variables
    private final List<Property> properties; // List of properties
    private JFrame frame;              // Main application frame

    /**
     * Constructor for MadeiraMapGUI.
     * Initializes the list of properties by reading from the CSV.
     */
    public MadeiraMapGUI() {
        properties = CsvToPropertyReader.Exercise_1();
    }

    /**
     * Creates and displays the main GUI.
     * Sets up the frame, background image, and buttons.
     */
    public void createAndShowGUI() {
        if (frame == null) {
            frame = new JFrame();
            frame.setUndecorated(true); // Removes the title bar from the window
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setIconImage(new ImageIcon(getClass().getResource("/madeira_icon.png")).getImage()); // Sets the window icon
        }

        // Load image to define frame size
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/madeira.jpg"));
        int imageWidth = imageIcon.getIconWidth();
        int imageHeight = imageIcon.getIconHeight();
        frame.setSize(imageWidth, imageHeight);

        frame.setResizable(false);
        frame.setLocationRelativeTo(null); // Centers the window

        // Main panel with background image
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(imageIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel.setLayout(null);

        // Add buttons to the panel
        addButtonsToPanel(panel, imageWidth, imageHeight);

        frame.getContentPane().removeAll();
        frame.add(panel);
        frame.revalidate();
        frame.repaint();
        frame.setVisible(true);
    }

    /**
     * Adds buttons to the main panel.
     *
     * @param panel       The panel to add buttons to.
     * @param imageWidth  The width of the background image.
     * @param imageHeight The height of the background image.
     */
    private void addButtonsToPanel(JPanel panel, int imageWidth, int imageHeight) {
        int buttonWidth = 150;
        int buttonHeight = 40;
        int spacing = 10;
        int startX = (imageWidth - buttonWidth) / 2;
        int startY = (imageHeight / 2) - ((buttonHeight + spacing) * 6) / 2;

        // Adding action buttons to the panel
        String[] buttonLabels = {"Details", "Property Map", "Area", "Owner Graph", "Suggestion", "Extra", "Close"};
        for (int i = 0; i < buttonLabels.length; i++) {
            JButton button = createButton(buttonLabels[i], startX, startY + (buttonHeight + spacing) * i, buttonWidth, buttonHeight);
            panel.add(button);
        }
    }

    /**
     * Creates a new JButton with consistent styling.
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
        button.setBounds(x, y, width, height);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(211, 211, 211)); // Light gray background
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorderPainted(false);
        button.setFocusable(false);
        button.addActionListener(ButtonHandlerFactory.createHandler(text, null, properties, frame));
        return button;
    }

    /**
     * Main method to launch the GUI application.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MadeiraMapGUI().createAndShowGUI());
    }
}