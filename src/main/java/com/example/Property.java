package com.example;

import java.util.List;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.io.WKTReader;

// Class representing a Property
public class Property {
    // Private fields for storing property details
    private String objectId;
    private String parId;
    private String parNum;
    private String shapeLength;
    private String shapeArea;
    private String geometry;
    private String owner;
    private String freguesia;
    private String municipio;
    private String ilha;

    // Constructor to initialize all fields
    public Property(String objectId, String parId, String parNum, String shapeLength, String shapeArea, String geometry, String owner, String freguesia, String municipio, String ilha) {
        this.objectId = objectId;
        this.parId = parId;
        this.parNum = parNum;
        this.shapeLength = shapeLength;
        this.shapeArea = shapeArea;
        this.geometry = geometry;
        this.owner = owner;
        this.freguesia = freguesia;
        this.municipio = municipio;
        this.ilha = ilha;
    }

    // Getter and setter methods

    // Getter and setter methods for objectId
    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    // Getter and setter methods for parId
    public String getParId() {
        return parId;
    }

    public void setParId(String parId) {
        this.parId = parId;
    }

    // Getter and setter methods for parNum
    public String getParNum() {
        return parNum;
    }

    public void setParNum(String parNum) {
        this.parNum = parNum;
    }

    // Getter and setter methods for shapeLength
    public String getShapeLength() {
        return shapeLength;
    }

    public void setShapeLength(String shapeLength) {
        this.shapeLength = shapeLength;
    }

    // Getter and setter methods for shapeArea
    public String getShapeArea() {
        return shapeArea;
    }

    public void setShapeArea(String shapeArea) {
        this.shapeArea = shapeArea;
    }

    // Getter and setter methods for geometry
    public String getGeometry() {
        return geometry;
    }

    public void setGeometry(String geometry) {
        this.geometry = geometry;
    }

    // Getter and setter methods for owner
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    // Getter and setter methods for freguesia
    public String getFreguesia() {
        return freguesia;
    }

    public void setFreguesia(String freguesia) {
        this.freguesia = freguesia;
    }

    // Getter and setter methods for municipio
    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    // Getter and setter methods for ilha
    public String getIlha() {
        return ilha;
    }

    public void setIlha(String ilha) {
        this.ilha = ilha;
    }

    // Method to check if this property is adjacent to another property using JTS library
    public boolean isAdjacentTo(Property other) {
        try {
            GeometryFactory geometryFactory = new GeometryFactory();
            WKTReader reader = new WKTReader(geometryFactory);
            Geometry thisGeometry = reader.read(this.geometry);
            Geometry otherGeometry = reader.read(other.getGeometry());

            // Check if the two geometries touch each other
            return thisGeometry.touches(otherGeometry);
        } catch (Exception e) {
            System.err.println("Error reading geometries: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Overridden toString method for representing the property details as a String
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
                ", freguesia='" + freguesia + '\'' +
                ", municipio='" + municipio + '\'' +
                ", ilha='" + ilha + '\'' +
                '}';
    }

    // Main method to check adjacency between properties
    public static void main(String[] args) {
        List<Property> properties = CsvToPropertyReader.Exercise_1();
        // Iterate over the list of properties and check if they are adjacent
        for (int i = 0; i < properties.size(); i++) {
            for (int j = i + 1; j < properties.size(); j++) {
                Property property1 = properties.get(i);
                Property property2 = properties.get(j);
                boolean adjacent = property1.isAdjacentTo(property2);
                System.out.println("Are properties " + property1.getObjectId() + " and " + property2.getObjectId() + " adjacent? " + adjacent);
            }
        }
    }
}
