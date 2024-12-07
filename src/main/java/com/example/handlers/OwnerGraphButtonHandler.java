package com.example.handlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.example.PropertyGraph;

/**
 * Handles the action when the "Owner Graph" button is clicked.
 * This class triggers the display of the owner graph representation by invoking a specific method from the PropertyGraph class.
 */
public class OwnerGraphButtonHandler implements ActionListener {

    /**
     * Invoked when the "Owner Graph" button is clicked.
     * Calls the {@code Exercise_5} method from the {@code PropertyGraph} class to display the owner graph.
     *
     * @param e The ActionEvent triggered by the button click.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Trigger the owner graph display logic
        PropertyGraph.Exercise_5();
    }
}
