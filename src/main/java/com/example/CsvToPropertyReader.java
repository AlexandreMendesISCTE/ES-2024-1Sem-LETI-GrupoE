package com.example;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

/**
 * The CsvToPropertyReader class is responsible for reading property data from a CSV file
 * and converting it into a list of Property objects. This class uses Apache Commons CSV
 * to parse the CSV file and create Property instances from each record.
 */
public class CsvToPropertyReader {

    private static final Logger LOGGER = Logger.getLogger(CsvToPropertyReader.class.getName());

    // Variable to store the selected CSV file path
    private static File selectedCsvFile;

    // Expected column headers in the CSV file
    private static final Set<String> REQUIRED_COLUMNS = new HashSet<>(Arrays.asList(
            "OBJECTID", "PAR_ID", "PAR_NUM", "Shape_Length", "Shape_Area", "geometry", "OWNER", "Freguesia", "Municipio", "Ilha"
    ));

    /**
     * Allows the user to select a CSV file through a file chooser dialog.
     */
    private static void selectCsvFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select a CSV file containing property data");
        int userSelection = fileChooser.showOpenDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            selectedCsvFile = fileChooser.getSelectedFile();
        } else {
            JOptionPane.showMessageDialog(null, "No file selected. Operation cancelled.", "File Selection", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Reads the selected CSV file and returns a list of Property objects.
     *
     * @return A list of Property objects representing the data in the CSV file.
     */
    public static List<Property> readPropertiesFromCsv() {
        if (selectedCsvFile == null) {
            selectCsvFile();
            if (selectedCsvFile == null) {
                return new ArrayList<>(); // Return an empty list if no file was selected
            }
        }

        List<Property> properties = new ArrayList<>();

        try (FileReader reader = new FileReader(selectedCsvFile);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                     .builder()
                     .setHeader()
                     .setSkipHeaderRecord(true)
                     .setIgnoreHeaderCase(true)
                     .setTrim(true)
                     .setDelimiter(';')
                     .build())) {

            // Validate CSV headers
            Set<String> csvHeaders = csvParser.getHeaderMap().keySet();
            if (!csvHeaders.containsAll(REQUIRED_COLUMNS)) {
                JOptionPane.showMessageDialog(null, "The file format is not compatible. Missing required columns.", "File Format Error", JOptionPane.ERROR_MESSAGE);
                return properties; // Return an empty list if the file format is incorrect
            }

            // Iterate over each record in the CSV file
            for (CSVRecord record : csvParser) {
                // Check if the record is not empty
                if (!record.get("OBJECTID").isEmpty()) {
                    // Create a new Property object from each record in the CSV
                    Property property = new Property(
                            record.get("OBJECTID"),
                            record.get("PAR_ID"),
                            record.get("PAR_NUM"),
                            record.get("Shape_Length"),
                            record.get("Shape_Area"),
                            record.get("geometry"),
                            record.get("OWNER"),
                            record.get("Freguesia"),
                            record.get("Municipio"),
                            record.get("Ilha")
                    );
                    properties.add(property); // Add the property to the list
                }
            }

        } catch (IOException e) {
            // Handle exceptions that occur during file reading
            LOGGER.log(Level.SEVERE, "Error reading CSV file", e);
            JOptionPane.showMessageDialog(null, "Error reading CSV file: " + e.getMessage(), "File Reading Error", JOptionPane.ERROR_MESSAGE);
        }

        return properties; // Return the list of properties
    }

    /**
     * Exercise_1 function reads the selected CSV file and returns the list of Property objects.
     * This method also prints the properties to verify the results.
     *
     * @return A list of Property objects read from the CSV file.
     */
    public static List<Property> Exercise_1() {
        List<Property> properties = readPropertiesFromCsv();

        // Print the properties to verify the results
        if (properties.isEmpty()) {
            LOGGER.info("No properties found in the CSV file.");
        } else {
            //properties.forEach(property -> LOGGER.info(property.toString()));
        }
        return properties; // Return the list of properties
    }

    /**
     * Filters the list of properties to only include those with a specific "Freguesia".
     *
     * @param properties The list of Property objects to be filtered.
     * @param freguesia The name of the "Freguesia" to filter by.
     * @return A filtered list of Property objects with the specified "Freguesia".
     */
    public static List<Property> filterPropertiesByFreguesia(List<Property> properties, String freguesia) {
        List<Property> filteredProperties = properties.stream()
                .filter(property -> freguesia.equalsIgnoreCase(property.getFreguesia()))
                .collect(Collectors.toList());
        if (filteredProperties.isEmpty()) {
            throw new IllegalArgumentException("No properties found for Freguesia: " + freguesia);
        }
        return filteredProperties;
    }

    /**
     * Filters the list of properties to only include those with a specific "Municipio".
     *
     * @param properties The list of Property objects to be filtered.
     * @param municipio The name of the "Municipio" to filter by.
     * @return A filtered list of Property objects with the specified "Municipio".
     */
    public static List<Property> filterPropertiesByMunicipio(List<Property> properties, String municipio) {
        List<Property> filteredProperties = properties.stream()
                .filter(property -> municipio.equalsIgnoreCase(property.getMunicipio()))
                .collect(Collectors.toList());
        if (filteredProperties.isEmpty()) {
            throw new IllegalArgumentException("No properties found for Municipio: " + municipio);
        }
        return filteredProperties;
    }

    /**
     * Filters the list of properties to only include those with a specific "Ilha".
     *
     * @param properties The list of Property objects to be filtered.
     * @param ilha The name of the "Ilha" to filter by.
     * @return A filtered list of Property objects with the specified "Ilha".
     */
    public static List<Property> filterPropertiesByIlha(List<Property> properties, String ilha) {
        List<Property> filteredProperties = properties.stream()
                .filter(property -> ilha.equalsIgnoreCase(property.getIlha()))
                .collect(Collectors.toList());
        if (filteredProperties.isEmpty()) {
            throw new IllegalArgumentException("No properties found for Ilha: " + ilha);
        }
        return filteredProperties;
    }

    /**
     * Main method to execute the Exercise_1 function and verify the output.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        Exercise_1(); // Call the Exercise_1 method to read properties from the selected CSV file
    }
}
