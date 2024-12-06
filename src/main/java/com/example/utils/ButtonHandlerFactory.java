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

public class ButtonHandlerFactory {

    public static ActionListener createHandler(String actionName, JPanel panel, List<Property> properties, JFrame frame) {
        switch (actionName) {
            case "Details":
                return new DetailsButtonHandler(panel, properties);
            case "Property Map":
                return new PropertyMapButtonHandler(panel, properties);
            case "Area":
                return new AreaButtonHandler(panel, properties);
            case "Owner Graph":
                return new OwnerGraphButtonHandler();
            case "Suggestion":
                return new SuggestionButtonHandler(panel, properties);
            case "Extra":
                return new ExtraButtonHandler(panel, properties);
            case "Close":
                return e -> frame.dispose();
            default:
                throw new IllegalArgumentException("Unknown action: " + actionName);
        }
    }
}
