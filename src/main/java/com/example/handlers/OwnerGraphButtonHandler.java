package com.example.handlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.example.PropertyGraph;

/**
 * OwnerGraphButtonHandler handles the action when the "Owner Graph" button is clicked.
 * This class is responsible for displaying the owner graph representation.
 */
public class OwnerGraphButtonHandler implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        PropertyGraph.Exercise_5();
    }
}
