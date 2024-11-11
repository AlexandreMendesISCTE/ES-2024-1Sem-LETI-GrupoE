package com.example;

import java.util.List;

public class App {
    public static void main(String[] args) {
        CSVReader csvReader = new CSVReader();
        List<Property> properties = csvReader.readCSV("Madeira-Moodle.csv");

        // Processar os dados conforme necessário
        for (Property property : properties) {
            System.out.println("OBJECTID: " + property.getObjectId());
            System.out.println("PAR_ID: " + property.getParId());
            System.out.println("PAR_NUM: " + property.getParNum());
            System.out.println("Shape_Length: " + property.getShapeLength());
            System.out.println("Shape_Area: " + property.getShapeArea());
            System.out.println("Geometry: " + property.getGeometry());
            System.out.println("OWNER: " + property.getOwner());
            System.out.println();
        }
    }
}
