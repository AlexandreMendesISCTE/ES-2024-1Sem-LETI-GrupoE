package com.example.utils;

import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.example.Property;
import com.example.handlers.AreaButtonHandler;
import com.example.handlers.DetailsButtonHandler;
import com.example.handlers.ExtraButtonHandler;
import com.example.handlers.OwnerGraphButtonHandler;
import com.example.handlers.PropertyMapButtonHandler;
import com.example.handlers.SuggestionButtonHandler;

/**
 * A factory class to create appropriate ActionListeners for different button actions.
 * Based on the action name provided, it returns an instance of the corresponding handler.
 */
public class ButtonHandlerFactory {

    /**
     * Creates an ActionListener based on the given action name.
     *
     * @param actionName The name of the action to handle.
     * @param panel      The panel associated with the action, used by most handlers.
     * @param properties The list of properties, required by some handlers.
     * @param frame      The main frame, used for actions like closing the application.
     * @return An ActionListener instance corresponding to the specified action.
     * @throws IllegalArgumentException If the actionName is unknown.
     */
    public static ActionListener createHandler(String actionName, JPanel panel, List<Property> properties, JFrame frame) {
        switch (actionName) {
            case "Details":
                // Handler for displaying details of a specific property
                return new DetailsButtonHandler(panel, properties);

            case "Property Map":
                // Handler for showing a specific property on the map
                return new PropertyMapButtonHandler(panel, properties);

            case "Area":
                // Handler for displaying area-related information
                return new AreaButtonHandler(panel, properties);

            case "Owner Graph":
                // Handler for showing the owner graph
                return new OwnerGraphButtonHandler();

            case "Suggestion":
                // Handler for displaying property swap suggestions
                return new SuggestionButtonHandler(panel, properties);

            case "Extra":
                // Handler for extra functionality like plotting two selected properties
                return new ExtraButtonHandler(panel, properties);

            case "Close":
                // Handler to close the frame
                return e -> frame.dispose();

            default:
                // Throws an exception for unsupported or unknown actions
                throw new IllegalArgumentException("Unknown action: " + actionName);
        }
    }
}
