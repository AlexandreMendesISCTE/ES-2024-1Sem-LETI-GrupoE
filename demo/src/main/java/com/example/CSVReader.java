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
    public List<Property> readCSV(String filePath) {
        List<Property> properties = new ArrayList<>();
        try (Reader reader = new FileReader(filePath);
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader())) {
            for (CSVRecord csvRecord : csvParser) {
                Property property = new Property();
                property.setObjectId(Integer.parseInt(csvRecord.get("OBJECTID")));
                property.setParId(Double.parseDouble(csvRecord.get("PAR_ID")));
                property.setParNum(csvRecord.get("PAR_NUM"));
                property.setShapeLength(Double.parseDouble(csvRecord.get("Shape_Length")));
                property.setShapeArea(Double.parseDouble(csvRecord.get("Shape_Area")));
                property.setGeometry(csvRecord.get("geometry"));
                property.setOwner(Integer.parseInt(csvRecord.get("OWNER")));
                properties.add(property);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}
