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
 * The CsvToPropertyReader class is responsible for:
 * - Reading property data from a CSV file.
 * - Converting the data into a list of {@link Property} objects.
 * - Providing filtering methods to retrieve utility functionality.
 * This class uses Apache Commons CSV for parsing.
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
     * If no file is selected, a warning message is displayed.
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
     * Reads the selected CSV file and converts its records into a list of {@link Property} objects.
     * Ensures that required headers are present and logs any errors during parsing.
     *
     * @return A list of {@link Property} objects representing the data in the CSV file.
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
                // Check if the record has a valid OBJECTID
                if (!record.get("OBJECTID").isEmpty()) {
                    // Create a new Property object from the record
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
            // Handle exceptions during file reading
            LOGGER.log(Level.SEVERE, "Error reading CSV file", e);
            JOptionPane.showMessageDialog(null, "Error reading CSV file: " + e.getMessage(), "File Reading Error", JOptionPane.ERROR_MESSAGE);
        }

        return properties; // Return the list of properties
    }

    /**
     * Reads properties from a CSV file and returns them as a list of {@link Property} objects.
     * Logs the properties for verification and debugging.
     *
     * @return A list of {@link Property} objects read from the CSV file.
     */
    public static List<Property> Exercise_1() {
        List<Property> properties = readPropertiesFromCsv();

        // Log the properties if any are found
        if (properties.isEmpty()) {
            LOGGER.info("No properties found in the CSV file.");
        } else {
            LOGGER.info(properties.size() + " properties successfully loaded.");
        }
        return properties;
    }

    /**
     * Filters properties by a specific "Freguesia".
     *
     * @param properties The list of {@link Property} objects to filter.
     * @param freguesia  The name of the "Freguesia" to filter by.
     * @return A list of properties that belong to the specified "Freguesia".
     * @throws IllegalArgumentException If no properties are found for the specified "Freguesia".
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
     * Filters properties by a specific "Municipio".
     *
     * @param properties The list of {@link Property} objects to filter.
     * @param municipio  The name of the "Municipio" to filter by.
     * @return A list of properties that belong to the specified "Municipio".
     * @throws IllegalArgumentException If no properties are found for the specified "Municipio".
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
     * Filters properties by a specific "Ilha".
     *
     * @param properties The list of {@link Property} objects to filter.
     * @param ilha       The name of the "Ilha" to filter by.
     * @return A list of properties that belong to the specified "Ilha".
     * @throws IllegalArgumentException If no properties are found for the specified "Ilha".
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
     * Main method to test the functionality of reading properties from a CSV file.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        Exercise_1(); // Read and log properties from the selected CSV file
    }
}
