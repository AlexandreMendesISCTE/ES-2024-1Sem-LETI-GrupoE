package com.example;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {
    public static List<Property> readProperties(String csvFilePath) {
        List<Property> properties = new ArrayList<>();

        try (Reader reader = new FileReader(csvFilePath);
                CSVParser csvParser = new CSVParser(reader,
                        CSVFormat.DEFAULT.withDelimiter(';').withFirstRecordAsHeader())) {

            // Iterate through the records
            for (CSVRecord csvRecord : csvParser) {
                // Create a new Property object and set its fields
                Property property = new Property();
                property.setObjectId(csvRecord.get("OBJECTID"));
                property.setParId(csvRecord.get("PAR_ID"));
                property.setParNum(csvRecord.get("PAR_NUM"));
                property.setShapeLength(csvRecord.get("Shape_Length"));
                property.setShapeArea(csvRecord.get("Shape_Area"));
                property.setGeometry(csvRecord.get("geometry"));
                property.setOwner(csvRecord.get("OWNER"));

                // Add the Property object to the list
                properties.add(property);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return properties;
    }

    public static void main(String[] args) {
        String csvFilePath = "src/main/resources/Madeira-Moodle.csv";
        List<Property> properties = readProperties(csvFilePath);

        // Print all properties
        for (Property property : properties) {
            System.out.println(property);
        }
    }
}
