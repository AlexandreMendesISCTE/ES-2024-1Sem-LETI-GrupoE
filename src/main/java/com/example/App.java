package com.example;

import javax.swing.SwingUtilities;

/**
 * Main application entry point.
 * Responsible for launching the MadeiraMapGUI.
 */
public class App {
    
    public static void main(String[] args) {
        // Launch MadeiraMapGUI using SwingUtilities to ensure thread safety
        SwingUtilities.invokeLater(() -> {
            MadeiraMapGUI gui = new MadeiraMapGUI();
            gui.createAndShowGUI();
        });
    }
}
