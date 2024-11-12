package com.example;

import java.util.List;

public class PropertyTest {
    public static void main(String[] args) {
        String csvFilePath = "src/main/resources/Madeira-Moodle.csv";
        List<Property> properties = CSVReader.readProperties(csvFilePath);

        // Check and print all adjacent properties
        findAndPrintAdjacentProperties(properties);

        // Test if two specific properties are adjacent
        Property property1 = properties.get(7);
        Property property2 = properties.get(14);
        testIfAdjacent(property1, property2);
    }

    public static void findAndPrintAdjacentProperties(List<Property> properties) {
        for (int i = 0; i < properties.size(); i++) {
            Property property1 = properties.get(i);
            for (int j = i + 1; j < properties.size(); j++) {
                Property property2 = properties.get(j);
                if (property1.isAdjacent(property2)) {
                    System.out.println("Property " + property1.getObjectId() + " is adjacent to Property "
                            + property2.getObjectId());
                }
            }
        }
    }

    public static void testIfAdjacent(Property property1, Property property2) {
        if (property1.isAdjacent(property2)) {
            System.out.println(
                    "Property " + property1.getObjectId() + " is adjacent to Property " + property2.getObjectId());
        } else {
            System.out.println(
                    "Property " + property1.getObjectId() + " is not adjacent to Property " + property2.getObjectId());
        }
    }
}