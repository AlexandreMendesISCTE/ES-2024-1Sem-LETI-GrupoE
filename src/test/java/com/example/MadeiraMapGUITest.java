package com.example;

import javax.swing.SwingUtilities;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import org.junit.jupiter.api.Test;

public class MadeiraMapGUITest {

    @Test
    public void testCreateAndShowGUI() {
        // Test if the createAndShowGUI method runs without throwing any exceptions
        assertDoesNotThrow(() -> {
            SwingUtilities.invokeAndWait(() -> {
                MadeiraMapGUI gui = new MadeiraMapGUI();
                gui.createAndShowGUI();
            });
        });
    }
}