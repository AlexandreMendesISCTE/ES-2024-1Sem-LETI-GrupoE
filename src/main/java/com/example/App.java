package com.example;

import javax.swing.SwingUtilities;

/**
 * Main application entry point.
 * This class initializes and launches the MadeiraMapGUI.
 */
public class App {

    /**
     * Main method to start the application.
     * Uses {@code SwingUtilities.invokeLater} to ensure the GUI is created and updated on the Event Dispatch Thread (EDT).
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        // Ensure thread safety by running the GUI creation on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            // Create an instance of the MadeiraMapGUI
            MadeiraMapGUI gui = new MadeiraMapGUI();

            // Initialize and display the GUI
            gui.createAndShowGUI();
        });
    }
}
