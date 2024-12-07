package com.example.handlers;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.example.CsvToPropertyReader;
import com.example.Property;
import com.example.utils.PropertyGeometryUtils;
import com.example.utils.PropertyMergeUtils;

/**
 * Handles the action of the "Area" button click.
 * Allows the user to choose the type of area (Normal or Merged) and view the
 * corresponding details for Freguesia, Municipio, or Ilha.
 */
public class AreaButtonHandler implements ActionListener {

    private final JPanel panel;
    private final List<Property> properties;

    /**
     * Constructor for AreaButtonHandler.
     *
     * @param panel      The panel that contains the button.
     * @param properties The list of properties loaded from the CSV file.
     */
    public AreaButtonHandler(JPanel panel, List<Property> properties) {
        this.panel = panel;
        this.properties = properties;
    }

    /**
     * Invoked when the "Area" button is clicked.
     * Opens dialogs to select the type of area and the location, then displays the results.
     *
     * @param e The ActionEvent triggered by the button click.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // First dialog: type of land selection
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
            return; // Exit if the dialog is closed
        }

        boolean isMerged = (terrainOption == JOptionPane.NO_OPTION);

        // Second dialog: type of area selection
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
            return; // Exit if the dialog is closed
        }

        // Handle user selection
        switch (zoneOption) {
            case JOptionPane.YES_OPTION: // Freguesia
                handleFreguesiaSelection(isMerged);
                break;

            case JOptionPane.NO_OPTION: // Municipio
                handleMunicipioSelection(isMerged);
                break;

            case JOptionPane.CANCEL_OPTION: // Ilha
                handleIlhaSelection(isMerged);
                break;
        }
    }

    /**
     * Handles the selection of Freguesia and displays the corresponding area details.
     *
     * @param isMerged True if the user selected "Merged" land type, otherwise false.
     */
    private void handleFreguesiaSelection(boolean isMerged) {
        Set<String> uniqueFreguesias = properties.stream()
                .map(Property::getFreguesia)
                .collect(Collectors.toSet());
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
                List<Property> mergedProperties = PropertyMergeUtils.mergePropertiesByAdjacencyAndOwner(
                        CsvToPropertyReader.filterPropertiesByFreguesia(properties, freguesiaInput));
                showMergedAreaTable(freguesiaInput, mergedProperties);
            } else {
                showAreaTable("freguesia", freguesiaInput);
            }
        }
    }

    /**
     * Handles the selection of Municipio and displays the corresponding area details.
     *
     * @param isMerged True if the user selected "Merged" land type, otherwise false.
     */
    private void handleMunicipioSelection(boolean isMerged) {
        Set<String> uniqueMunicipios = properties.stream()
                .map(Property::getMunicipio)
                .collect(Collectors.toSet());
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
                List<Property> mergedProperties = PropertyMergeUtils.mergePropertiesByAdjacencyAndOwner(
                        CsvToPropertyReader.filterPropertiesByMunicipio(properties, municipioInput));
                showMergedAreaTable(municipioInput, mergedProperties);
            } else {
                showAreaTable("municipio", municipioInput);
            }
        }
    }

    /**
     * Handles the selection of Ilha and displays the corresponding area details.
     *
     * @param isMerged True if the user selected "Merged" land type, otherwise false.
     */
    private void handleIlhaSelection(boolean isMerged) {
        if (isMerged) {
            List<Property> mergedProperties = PropertyMergeUtils.mergePropertiesByAdjacencyAndOwner(
                    CsvToPropertyReader.filterPropertiesByIlha(properties, "Ilha da Madeira (Madeira)"));
            showMergedAreaTable("Ilha da Madeira (Madeira)", mergedProperties);
        } else {
            showAreaTable("ilha", "Ilha da Madeira (Madeira)");
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

        calculateAndAddAreaRow(tableModel, location, filteredProperties);

        showResultsTable(tableModel, "Area Results");
    }

    /**
     * Displays a table with merged area details for the selected location.
     *
     * @param location The location name.
     * @param mergedProperties The list of merged properties.
     */
    private void showMergedAreaTable(String location, List<Property> mergedProperties) {
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Location");
        tableModel.addColumn("Total Area (m²)");
        tableModel.addColumn("Average Area (m²)");

        calculateAndAddAreaRow(tableModel, location, mergedProperties);

        showResultsTable(tableModel, "Merged Area Results");
    }

    /**
     * Calculates the total and average area for a list of properties and adds a row to the table model.
     *
     * @param tableModel The table model to add the row to.
     * @param location The location name.
     * @param properties The list of properties.
     */
    private void calculateAndAddAreaRow(DefaultTableModel tableModel, String location, List<Property> properties) {
        double totalArea = properties.stream()
                .mapToDouble(p -> Double.parseDouble(p.getShapeArea()))
                .sum();

        double averageArea;
        try {
            averageArea = PropertyGeometryUtils.calculateAverageArea(properties);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(panel, "No valid property areas found for calculation.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        tableModel.addRow(new Object[]{location, totalArea, averageArea});
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
        scrollPane.setPreferredSize(new java.awt.Dimension(600, 400));

        JOptionPane.showMessageDialog(panel, scrollPane, title, JOptionPane.INFORMATION_MESSAGE);
    }
}
