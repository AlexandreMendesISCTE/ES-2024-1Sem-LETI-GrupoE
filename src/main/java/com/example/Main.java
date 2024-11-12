package com.example;

import javafx.application.Application;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String csvFilePath = "src/main/resources/Madeira-Moodle.csv";
        List<Property> properties = CSVReader.readProperties(csvFilePath);
        MultipolygonGraph.setProperties(properties);
        MultipolygonGraph.setN(1);
        MultipolygonGraph.setM(2);

        Application.launch(MultipolygonGraph.class, args);
    }
}
