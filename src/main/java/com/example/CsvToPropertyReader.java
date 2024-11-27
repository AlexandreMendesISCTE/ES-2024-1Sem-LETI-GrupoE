package com.example;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class CsvToPropertyReader {

    // Method to read a CSV file and return a list of Property objects
    public static List<Property> readPropertiesFromCsv(File csvFile) {
        List<Property> properties = new ArrayList<>();

        try (FileReader reader = new FileReader(csvFile);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim().withDelimiter(';'))) {

            for (CSVRecord record : csvParser) {
                // Check if record is not empty
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
                    properties.add(property);
                }
            }

        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
            e.printStackTrace();
        }

        return properties;
    }


    // Exercise_1 function
    public static List<Property> Exercise_1() {
        File csvFile = new File("src/main/resources/Madeira-Moodle-1.1.csv");
        List<Property> properties = readPropertiesFromCsv(csvFile);
        
        // Printing the properties to verify the results
        if (properties.isEmpty()) {
            System.out.println("No properties found in the CSV file.");
        } else {
            for (Property property : properties) {
                //System.out.println(property);
            }
        }
        return properties;
    }

    public static void main(String[] args) {
        Exercise_1();
    }
}