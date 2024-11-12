package com.example;

import java.util.HashSet;
import java.util.Set;

public class Property {
    private String objectId;
    private String parId;
    private String parNum;
    private String shapeLength;
    private String shapeArea;
    private String geometry;
    private String owner;
    private double[][] coordinates;

    // Getters and Setters
    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getParId() {
        return parId;
    }

    public void setParId(String parId) {
        this.parId = parId;
    }

    public String getParNum() {
        return parNum;
    }

    public void setParNum(String parNum) {
        this.parNum = parNum;
    }

    public String getShapeLength() {
        return shapeLength;
    }

    public void setShapeLength(String shapeLength) {
        this.shapeLength = shapeLength;
    }

    public String getShapeArea() {
        return shapeArea;
    }

    public void setShapeArea(String shapeArea) {
        this.shapeArea = shapeArea;
    }

    public String getGeometry() {
        return geometry;
    }

    public void setGeometry(String geometry) {
        this.geometry = geometry;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public double[][] convertGeometryToCoordinates() {
        return coordinates;
    }

    public void setCoordinates(double[][] coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public String toString() {
        return "Property{" +
                "objectId='" + objectId + '\'' +
                ", parId='" + parId + '\'' +
                ", parNum='" + parNum + '\'' +
                ", shapeLength='" + shapeLength + '\'' +
                ", shapeArea='" + shapeArea + '\'' +
                ", geometry='" + geometry + '\'' +
                ", owner='" + owner + '\'' +
                '}';
    }

    // Method to check if two properties are adjacent
    public boolean isAdjacent(Property other) {
        String[] thisCoordinates = this.geometry.split(",");
        String[] otherCoordinates = other.geometry.split(",");

        Set<String> commonCoordinates = new HashSet<>();
        for (String coord : thisCoordinates) {
            for (String otherCoord : otherCoordinates) {
                if (coord.trim().equals(otherCoord.trim())) {
                    commonCoordinates.add(coord.trim());
                }
            }
        }

        return commonCoordinates.size() >= 2;
    }

    // Method to convert geometry to a 2xN matrix
    public double[][] getCoordinates() {
        // Remove the "MULTIPOLYGON (((" prefix and ")))" suffix
        String cleanedGeometry = this.geometry.replace("MULTIPOLYGON (((", "").replace(")))", "");

        // Split the cleaned string into coordinate pairs
        String[] coordinatePairs = cleanedGeometry.split(", ");
        int numPairs = coordinatePairs.length;
        double[][] coordinates = new double[numPairs][2];

        for (int i = 0; i < numPairs; i++) {
            String[] coords = coordinatePairs[i].split(" ");
            coordinates[i][0] = Double.parseDouble(coords[0].trim());
            coordinates[i][1] = Double.parseDouble(coords[1].trim());
        }

        return coordinates;
    }
}