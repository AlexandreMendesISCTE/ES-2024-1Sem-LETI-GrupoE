package com.example;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 * The MadeiraMapGUI class represents the main graphical user interface (GUI) for the Madeira Property Map system.
 * This GUI allows users to view property details, generate graphs, suggestions, and more.
 * It contains a list of properties and various action buttons for different functionalities.
 */
public class MadeiraMapGUI {

    // Instance Variables
    private List<Property> properties; // List of properties
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
        addButton(panel, "Details", startX, startY, buttonWidth, buttonHeight);
        addButton(panel, "Property Map", startX, startY + (buttonHeight + spacing) * 1, buttonWidth, buttonHeight);
        addButton(panel, "Area", startX, startY + (buttonHeight + spacing) * 2, buttonWidth, buttonHeight);
        addButton(panel, "Owner Graph", startX, startY + (buttonHeight + spacing) * 3, buttonWidth, buttonHeight);
        addButton(panel, "Suggestion", startX, startY + (buttonHeight + spacing) * 4, buttonWidth, buttonHeight);
        addButton(panel, "Extra", startX, startY + (buttonHeight + spacing) * 5, buttonWidth, buttonHeight);
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
     */
    private void addButton(JPanel panel, String text, int x, int y, int width, int height) {
        JButton button = new JButton(text);
        button.setBounds(x, y, width, height);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(211, 211, 211)); // Light gray background
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorderPainted(false);
        button.setFocusable(false);
        button.addActionListener(createButtonActionListener(text, panel));
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

    // Action Handling Methods for Buttons

    /**
     * Creates an ActionListener for each button based on its text label.
     *
     * @param text  The text label of the button.
     * @param panel The panel that contains the button.
     * @return The ActionListener for the button.
     */
    private ActionListener createButtonActionListener(String text, JPanel panel) {
        return e -> {
            switch (text) {
                case "Details":
                    handleDetailsAction(panel);
                    break;
                case "Property Map":
                    handlePropertyMapAction(panel);
                    break;
                case "Area":
                    handleAreaAction(panel);
                    break;
                case "Owner Graph":
                    PropertyGraph.Exercise_5();
                    break;
                case "Suggestion":
                    handleSuggestionAction(panel);
                    break;
                case "Extra":
                    handleExtraAction(panel);
                    break;
            }
        };
    }

    /**
     * Handles the action for the "Details" button.
     * Displays property details for the selected property.
     *
     * @param panel The panel that contains the button.
     */
    private void handleDetailsAction(JPanel panel) {
        String propertyInput = JOptionPane.showInputDialog(panel, "Which property would you like more details on?");
        try {
            int propertyNumber = Integer.parseInt(propertyInput);
            if (propertyNumber > 0 && propertyNumber <= properties.size()) {
                Property property = properties.get(propertyNumber - 1);
                showPropertyDetails(property);
            } else {
                JOptionPane.showMessageDialog(panel, "Property not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(panel, "Invalid input. Please enter a valid property number.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Handles the action for the "Property Map" button.
     * Displays the map representation of a selected property.
     *
     * @param panel The panel that contains the button.
     */
    private void handlePropertyMapAction(JPanel panel) {
        String propertyInput = JOptionPane.showInputDialog(panel, "Which property would you like to see on the map?");
        try {
            int propertyNumber = Integer.parseInt(propertyInput);
            if (propertyNumber > 0 && propertyNumber <= properties.size()) {
                Property property = properties.get(propertyNumber - 1);
                PropertyGraph.Exercise_2(property.getObjectId());
            } else {
                JOptionPane.showMessageDialog(panel, "Property not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(panel, "Invalid input. Please enter a valid property number.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Handles the action for the "Area" button.
     * Allows the user to select the type of land and area to view.
     *
     * @param panel The panel that contains the button.
     */
    private void handleAreaAction(JPanel panel) {
        // First question: type of land
        int terrainOption = JOptionPane.showOptionDialog(
                panel,
                "What type of land would you like to see?",
                "Land Type Selection",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new Object[]{"Normal", "Merged"},
                "Normal"
        );

        if (terrainOption == JOptionPane.CLOSED_OPTION) {
            return; // If the dialog is closed, end the flow
        }

        boolean isMerged = (terrainOption == JOptionPane.NO_OPTION);

        // Second question: type of area
        int zoneOption = JOptionPane.showOptionDialog(
                panel,
                "What type of area would you like to see?",
                "Area Type Selection",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new Object[]{"Freguesia", "Municipio", "Ilha"},
                "Freguesia"
        );

        if (zoneOption == JOptionPane.CLOSED_OPTION) {
            return; // If the dialog is closed, end the flow
        }

        switch (zoneOption) {
            case JOptionPane.YES_OPTION: // Freguesia
                Set<String> uniqueFreguesias = properties.stream().map(Property::getFreguesia).collect(Collectors.toSet());
                String freguesiaInput = (String) JOptionPane.showInputDialog(
                        panel,
                        "Select a Freguesia:",
                        "Freguesia Selection",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        uniqueFreguesias.toArray(),
                        uniqueFreguesias.iterator().next()
                );

                if (freguesiaInput != null) {
                    if (isMerged) {
                        List<Property> mergedProperties = Property.mergePropertiesByAdjacencyAndOwner(CsvToPropertyReader.filterPropertiesByFreguesia(properties, freguesiaInput));
                        showMergedAreaTable("freguesia", freguesiaInput, mergedProperties);
                    } else {
                        showAreaTable("freguesia", freguesiaInput);
                    }
                }
                break;

            case JOptionPane.NO_OPTION: // Municipio
                Set<String> uniqueMunicipios = properties.stream().map(Property::getMunicipio).collect(Collectors.toSet());
                String municipioInput = (String) JOptionPane.showInputDialog(
                        panel,
                        "Select a Municipio:",
                        "Municipio Selection",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        uniqueMunicipios.toArray(),
                        uniqueMunicipios.iterator().next()
                );

                if (municipioInput != null) {
                    if (isMerged) {
                        List<Property> mergedProperties = Property.mergePropertiesByAdjacencyAndOwner(CsvToPropertyReader.filterPropertiesByMunicipio(properties, municipioInput));
                        showMergedAreaTable("municipio", municipioInput, mergedProperties);
                    } else {
                        showAreaTable("municipio", municipioInput);
                    }
                }
                break;

            case JOptionPane.CANCEL_OPTION: // Ilha
                if (isMerged) {
                    List<Property> mergedProperties = Property.mergePropertiesByAdjacencyAndOwner(CsvToPropertyReader.filterPropertiesByIlha(properties, "Ilha da Madeira (Madeira)"));
                    showMergedAreaTable("ilha", "Ilha da Madeira (Madeira)", mergedProperties);
                } else {
                    showAreaTable("ilha", "Ilha da Madeira (Madeira)");
                }
                break;
        }
    }

    /**
     * Handles the action for the "Suggestion" button.
     * Displays swap suggestions for properties in a selected "freguesia".
     *
     * @param panel The panel that contains the button.
     */
    private void handleSuggestionAction(JPanel panel) {
        Set<String> uniqueFreguesias = properties.stream().map(Property::getFreguesia).collect(Collectors.toSet());
        String freguesiaInput = (String) JOptionPane.showInputDialog(
                panel,
                "Select a Freguesia:",
                "Freguesia Selection",
                JOptionPane.QUESTION_MESSAGE,
                null,
                uniqueFreguesias.toArray(),
                uniqueFreguesias.iterator().next()
        );

        if (freguesiaInput != null) {
            JFrame suggestionFrame = new JFrame("Suggestion Console");
            suggestionFrame.setSize(600, 400);
            suggestionFrame.setLocationRelativeTo(null);
            suggestionFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            JTextArea textArea = new JTextArea();
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);
            suggestionFrame.add(scrollPane);

            suggestionFrame.setVisible(true);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PrintStream printStream = new PrintStream(outputStream);
            PrintStream originalOut = System.out;

            try {
                System.setOut(printStream);
                System.setErr(printStream);

                System.out.println("Generating swap suggestions for Freguesia: " + freguesiaInput);
                PropertySwapSuggestion.Exercise_6(freguesiaInput);

                textArea.setText(outputStream.toString());
            } finally {
                System.setOut(originalOut);
            }
        }
    }

    /**
     * Handles the action for the "Extra" button.
     * Allows the user to select and draw two properties on a graph.
     *
     * @param panel The panel that contains the button.
     */
    private void handleExtraAction(JPanel panel) {
        Set<String> uniqueFreguesias = properties.stream().map(Property::getFreguesia).collect(Collectors.toSet());
        String freguesiaInput = (String) JOptionPane.showInputDialog(
                panel,
                "Select a Freguesia:",
                "Freguesia Selection",
                JOptionPane.QUESTION_MESSAGE,
                null,
                uniqueFreguesias.toArray(),
                uniqueFreguesias.iterator().next()
        );

        if (freguesiaInput != null) {
            List<Property> filteredProperties = CsvToPropertyReader.filterPropertiesByFreguesia(properties, freguesiaInput);
            Set<String> objectIds = filteredProperties.stream().map(Property::getObjectId).collect(Collectors.toSet());

            String property1Input = (String) JOptionPane.showInputDialog(
                    panel,
                    "Select the first property:",
                    "Property Selection",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    objectIds.toArray(),
                    objectIds.iterator().next()
            );

            String property2Input = (String) JOptionPane.showInputDialog(
                    panel,
                    "Select the second property:",
                    "Property Selection",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    objectIds.toArray(),
                    objectIds.iterator().next()
            );

            if (property1Input != null && property2Input != null) {
                JOptionPane.showMessageDialog(
                        panel,
                        "You selected Property " + property1Input + " and Property " + property2Input,
                        "Selected Properties",
                        JOptionPane.INFORMATION_MESSAGE
                );

                PropertyGeometryPlotter plotter = new PropertyGeometryPlotter("Selected Property Geometries");
                plotter.drawProperties(properties, freguesiaInput, Integer.parseInt(property1Input), Integer.parseInt(property2Input));
            }
        }
    }

    // Helper Methods for Display Actions

    /**
     * Displays a table with area details for the selected location.
     *
     * @param areaType The type of area (freguesia, municipio, ilha).
     * @param location The location name.
     */
    private void showAreaTable(String areaType, String location) {
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Location");
        tableModel.addColumn("Total Area (m²)");
        tableModel.addColumn("Average Area (m²)");

        List<Property> filteredProperties = properties.stream()
                .filter(p -> {
                    switch (areaType) {
                        case "freguesia":
                            return p.getFreguesia().equalsIgnoreCase(location);
                        case "municipio":
                            return p.getMunicipio().equalsIgnoreCase(location);
                        case "ilha":
                            return p.getIlha().equalsIgnoreCase(location);
                        default:
                            return false;
                    }
                })
                .collect(Collectors.toList());

        double totalArea = filteredProperties.stream()
                .mapToDouble(p -> Double.parseDouble(p.getShapeArea()))
                .sum();

        double averageArea = 0.0;
        try {
            averageArea = Property.calculateAverageArea(filteredProperties);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(frame, "No valid property areas found for calculation.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        tableModel.addRow(new Object[]{location, totalArea, averageArea});

        showResultsTable(tableModel, "Area Results");
    }

    /**
     * Displays a table with merged area details for the selected location.
     *
     * @param areaType The type of area (freguesia, municipio, ilha).
     * @param location The location name.
     * @param mergedProperties List of merged properties.
     */
    private void showMergedAreaTable(String areaType, String location, List<Property> mergedProperties) {
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Location");
        tableModel.addColumn("Total Area (m²)");
        tableModel.addColumn("Average Area (m²)");

        double totalArea = mergedProperties.stream()
                .mapToDouble(p -> Double.parseDouble(p.getShapeArea()))
                .sum();

        double averageArea = 0.0;
        try {
            averageArea = Property.calculateAverageArea(mergedProperties);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(frame, "No valid property areas found for calculation.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        tableModel.addRow(new Object[]{location, totalArea, averageArea});

        showResultsTable(tableModel, "Merged Area Results");
    }

    /**
     * Displays the results in a table format.
     *
     * @param tableModel The data model for the table.
     * @param title      The title of the results table.
     */
    private void showResultsTable(DefaultTableModel tableModel, String title) {
        JTable resultTable = new JTable(tableModel);
        resultTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (row % 2 == 0) {
                    comp.setBackground(new Color(240, 240, 240));
                } else {
                    comp.setBackground(Color.white);
                }
                return comp;
            }
        });

        JScrollPane scrollPane = new JScrollPane(resultTable);
        scrollPane.setPreferredSize(new Dimension(600, 400));

        JOptionPane.showMessageDialog(frame, scrollPane, title, JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Displays the property details in a detailed view.
     *
     * @param property The property to display details for.
     */
    private void showPropertyDetails(Property property) {
        frame.getContentPane().removeAll();
        frame.repaint();
        frame.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Property " + property.getObjectId(), SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        frame.add(titleLabel, BorderLayout.NORTH);

        JPanel detailPanel = new JPanel();
        detailPanel.setLayout(new BorderLayout());

        String adjacentProperties = getAdjacentProperties(property);
        String[][] propertyData = {
                {"Object ID", property.getObjectId()},
                {"Parcel ID", property.getParId()},
                {"Parcel Number", property.getParNum()},
                {"Shape Length", property.getShapeLength()},
                {"Shape Area", property.getShapeArea()},
                {"Geometry", property.getGeometry()},
                {"Owner", property.getOwner()},
                {"Freguesia", property.getFreguesia()},
                {"Municipio", property.getMunicipio()},
                {"Ilha", property.getIlha()},
                {"Adjacencies", adjacentProperties}
        };
        String[] columnNames = {"Attribute", "Value"};
        JTable propertyTable = new JTable(propertyData, columnNames);
        JScrollPane tableScrollPane = new JScrollPane(propertyTable);
        detailPanel.add(tableScrollPane, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.addActionListener(e -> createAndShowGUI());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        detailPanel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(detailPanel);
        frame.revalidate();
        frame.repaint();
    }

    /**
     * Gets the adjacent properties for a given property.
     *
     * @param property The property to find adjacent properties for.
     * @return A string listing the adjacent properties.
     */
    private String getAdjacentProperties(Property property) {
        StringBuilder adjacencyInfo = new StringBuilder();
        for (Property otherProperty : properties) {
            if (!property.equals(otherProperty) && property.isAdjacentTo(otherProperty)) {
                if (adjacencyInfo.length() > 0) {
                    adjacencyInfo.append(", ");
                }
                adjacencyInfo.append(otherProperty.getObjectId());
            }
        }
        return adjacencyInfo.length() > 0 ? adjacencyInfo.toString() : "None";
    }

    // Main Method

    /**
     * Main method to launch the GUI application.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MadeiraMapGUI().createAndShowGUI());
    }
}
