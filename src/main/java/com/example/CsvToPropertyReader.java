package com.example;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

/**
 * The CsvToPropertyReader class is responsible for reading property data from a CSV file
 * and converting it into a list of Property objects. This class uses Apache Commons CSV
 * to parse the CSV file and create Property instances from each record.
 */
public class CsvToPropertyReader {

    /**
     * Reads a CSV file and returns a list of Property objects.
     *
     * @param csvFile The CSV file to read from.
     * @return A list of Property objects representing the data in the CSV file.
     */
    public static List<Property> readPropertiesFromCsv(File csvFile) {
        List<Property> properties = new ArrayList<>();

        try (FileReader reader = new FileReader(csvFile);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim().withDelimiter(';'))) {

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
            System.err.println("Error reading CSV file: " + e.getMessage());
            e.printStackTrace();
        }

        return properties; // Return the list of properties
    }

    /**
     * Exercise_1 function reads a specific CSV file and returns the list of Property objects.
     * This method also prints the properties to verify the results.
     *
     * @return A list of Property objects read from the CSV file.
     */
    public static List<Property> Exercise_1() {
        // Define the CSV file to be read
        File csvFile = new File("src/main/resources/Madeira-Moodle-1.1.csv");
        List<Property> properties = readPropertiesFromCsv(csvFile);
        
        // Print the properties to verify the results
        if (properties.isEmpty()) {
            System.out.println("No properties found in the CSV file.");
        } else {
            for (Property property : properties) {
                // Uncomment the following line to print each property
                // System.out.println(property);
            }
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
        Exercise_1(); // Call the Exercise_1 method to read properties from the CSV file
        filterPropertiesByFreguesia(Exercise_1(), "Arco da Calheta"); // Filter properties by "Freguesia"
        filterPropertiesByMunicipio(Exercise_1(), "Calheta"); // Filter properties by "Municipio"
        filterPropertiesByIlha(Exercise_1(), "Ilha da Madeira (Madeira)"); // Filter properties by "Ilha"
    }
}