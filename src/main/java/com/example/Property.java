package com.example;

import java.util.List;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.WKTReader;

/**
 * The Property class represents a property with various attributes such as object ID, parcel ID,
 * geometry, owner, and location details. It also provides methods for calculating adjacency
 * with other properties and for calculating the centroid of the property's geometry.
 */
public class Property {
    // Private fields for storing property details
    private String objectId; // Unique identifier for the property
    private String parId; // Parcel ID of the property
    private String parNum; // Parcel number of the property
    private String shapeLength; // Length of the property boundary
    private String shapeArea; // Area of the property
    private String geometry; // Geometry of the property in WKT format
    private String owner; // Owner of the property
    private String freguesia; // Freguesia (parish) where the property is located
    private String municipio; // Municipality where the property is located
    private String ilha; // Island where the property is located

    /**
     * Constructor to initialize all fields of the Property class.
     *
     * @param objectId Unique identifier for the property
     * @param parId Parcel ID of the property
     * @param parNum Parcel number of the property
     * @param shapeLength Length of the property boundary
     * @param shapeArea Area of the property
     * @param geometry Geometry of the property in WKT format
     * @param owner Owner of the property
     * @param freguesia Freguesia (parish) where the property is located
     * @param municipio Municipality where the property is located
     * @param ilha Island where the property is located
     */
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

    /**
     * Gets the object ID of the property.
     *
     * @return The object ID of the property.
     */
    public String getObjectId() {
        return objectId;
    }

    /**
     * Sets the object ID of the property.
     *
     * @param objectId The new object ID of the property.
     */
    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    /**
     * Gets the parcel ID of the property.
     *
     * @return The parcel ID of the property.
     */
    public String getParId() {
        return parId;
    }

    /**
     * Sets the parcel ID of the property.
     *
     * @param parId The new parcel ID of the property.
     */
    public void setParId(String parId) {
        this.parId = parId;
    }

    /**
     * Gets the parcel number of the property.
     *
     * @return The parcel number of the property.
     */
    public String getParNum() {
        return parNum;
    }

    /**
     * Sets the parcel number of the property.
     *
     * @param parNum The new parcel number of the property.
     */
    public void setParNum(String parNum) {
        this.parNum = parNum;
    }

    /**
     * Gets the length of the property boundary.
     *
     * @return The length of the property boundary.
     */
    public String getShapeLength() {
        return shapeLength;
    }

    /**
     * Sets the length of the property boundary.
     *
     * @param shapeLength The new length of the property boundary.
     */
    public void setShapeLength(String shapeLength) {
        this.shapeLength = shapeLength;
    }

    /**
     * Gets the area of the property.
     *
     * @return The area of the property.
     */
    public String getShapeArea() {
        return shapeArea;
    }

    /**
     * Sets the area of the property.
     *
     * @param shapeArea The new area of the property.
     */
    public void setShapeArea(String shapeArea) {
        this.shapeArea = shapeArea;
    }

    /**
     * Gets the geometry of the property in WKT format.
     *
     * @return The geometry of the property.
     */
    public String getGeometry() {
        return geometry;
    }

    /**
     * Sets the geometry of the property in WKT format.
     *
     * @param geometry The new geometry of the property.
     */
    public void setGeometry(String geometry) {
        this.geometry = geometry;
    }

    /**
     * Gets the owner of the property.
     *
     * @return The owner of the property.
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Sets the owner of the property.
     *
     * @param owner The new owner of the property.
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     * Gets the freguesia (parish) where the property is located.
     *
     * @return The freguesia where the property is located.
     */
    public String getFreguesia() {
        return freguesia;
    }

    /**
     * Sets the freguesia (parish) where the property is located.
     *
     * @param freguesia The new freguesia where the property is located.
     */
    public void setFreguesia(String freguesia) {
        this.freguesia = freguesia;
    }

    /**
     * Gets the municipality where the property is located.
     *
     * @return The municipality where the property is located.
     */
    public String getMunicipio() {
        return municipio;
    }

    /**
     * Sets the municipality where the property is located.
     *
     * @param municipio The new municipality where the property is located.
     */
    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    /**
     * Gets the island where the property is located.
     *
     * @return The island where the property is located.
     */
    public String getIlha() {
        return ilha;
    }

    /**
     * Sets the island where the property is located.
     *
     * @param ilha The new island where the property is located.
     */
    public void setIlha(String ilha) {
        this.ilha = ilha;
    }

    /**
     * Method to check if this property is adjacent to another property using JTS library.
     *
     * @param other The other property to check adjacency with.
     * @return True if the properties are adjacent, false otherwise.
     */
    public boolean isAdjacentTo(Property other) {
        try {
            GeometryFactory geometryFactory = new GeometryFactory();
            WKTReader reader = new WKTReader(geometryFactory);
            Geometry thisGeometry = reader.read(this.geometry);
            Geometry otherGeometry = reader.read(other.getGeometry());

            // Check if the two geometries touch each other, intersect, or one contains the other
            return thisGeometry.touches(otherGeometry) || thisGeometry.intersects(otherGeometry) || thisGeometry.contains(otherGeometry) || otherGeometry.contains(thisGeometry);
        } catch (Exception e) {
            System.err.println("Error reading geometries: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Method to calculate the centroid (midpoint) of the property's geometry.
     *
     * @return The centroid of the property's geometry as a Point.
     */
    public Point getCentroid() {
        try {
            GeometryFactory geometryFactory = new GeometryFactory();
            WKTReader reader = new WKTReader(geometryFactory);
            Geometry thisGeometry = reader.read(this.geometry);

            // Calculate and return the centroid
            return thisGeometry.getCentroid();
        } catch (Exception e) {
            System.err.println("Error reading geometry for centroid calculation: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Method to calculate the average area of a list of properties.
     *
     * @param properties The list of Property objects.
     * @return The average area as a double.
     * @throws IllegalArgumentException if the property list is null or empty, or if no valid areas are found.
     */
    public static double calculateAverageArea(List<Property> properties) {
        if (properties == null || properties.isEmpty()) {
            throw new IllegalArgumentException("Property list cannot be null or empty");
        }

        double totalArea = 0.0;
        int count = 0;

        for (Property property : properties) {
            try {
                double area = Double.parseDouble(property.getShapeArea());
                totalArea += area;
                count++;
            } catch (NumberFormatException e) {
                System.err.println("Invalid area value for property " + property.getObjectId() + ": " + property.getShapeArea());
            }
        }

        if (count == 0) {
            throw new IllegalArgumentException("No valid property areas found");
        }

        return totalArea / count;
    }


    /**
     * Exercise_3 function calculates the average area of the properties filtered by "Freguesia", "Municipio", and "Ilha".
     *
     * @param properties The list of Property objects to be filtered and averaged.
     * @param freguesia The name of the "Freguesia" to filter by. If null or empty, a default value will be used.
     * @param municipio The name of the "Municipio" to filter by. If null or empty, a default value will be used.
     * @param ilha The name of the "Ilha" to filter by. If null or empty, a default value will be used.
     */
    public static void Exercise_3(List<Property> properties, String freguesia, String municipio, String ilha) {
        // If any of the input parameters are null or empty, set default values.
        if (freguesia == null || freguesia.isEmpty() || municipio == null || municipio.isEmpty() || ilha == null || ilha.isEmpty()) {
            freguesia = "Arco da Calheta";
            municipio = "Calheta";
            ilha = "Ilha da Madeira (Madeira)";
        }

        // Filter the properties by the specified "Freguesia".
        List<Property> filteredByFreguesia = CsvToPropertyReader.filterPropertiesByFreguesia(properties, freguesia);
        // Filter the properties by the specified "Municipio".
        List<Property> filteredByMunicipio = CsvToPropertyReader.filterPropertiesByMunicipio(properties, municipio);
        // Filter the properties by the specified "Ilha".
        List<Property> filteredByIlha = CsvToPropertyReader.filterPropertiesByIlha(properties, ilha);

        try {
            // Calculate the average area of properties in the specified "Freguesia".
            double averageAreaFreguesia = calculateAverageArea(filteredByFreguesia);
            System.out.println("Average area of properties in Freguesia '" + freguesia + "': " + averageAreaFreguesia);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }

        try {
            // Calculate the average area of properties in the specified "Municipio".
            double averageAreaMunicipio = calculateAverageArea(filteredByMunicipio);
            System.out.println("Average area of properties in Municipio '" + municipio + "': " + averageAreaMunicipio);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }

        try {
            // Calculate the average area of properties in the specified "Ilha".
            double averageAreaIlha = calculateAverageArea(filteredByIlha);
            System.out.println("Average area of properties in Ilha '" + ilha + "': " + averageAreaIlha);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }


    /**
     * Overridden toString method for representing the property details as a String.
     *
     * @return A string representation of the property details.
     */
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

    /**
     * Main method for testing the Property class.
     * This method loads properties from a CSV file and checks adjacency between them.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        // isAdjacentTo method is used in the following code
        /* 
        List<Property> properties = CsvToPropertyReader.Exercise_1();
        if (properties.size() > 120) {
            properties = properties.subList(0, 120); // Limit the number of properties to 120
        }
        for (int i = 0; i < properties.size(); i++) {
            for (int j = i + 1; j < properties.size(); j++) {
                Property property1 = properties.get(i);
                Property property2 = properties.get(j);
                if (property1.isAdjacentTo(property2)) {
                    System.out.println(property1.getObjectId() + " is adjacent to " + property2.getObjectId());
                }
            }
        }
        */

        // Exercise_3 method is used in the following code
        Exercise_3(CsvToPropertyReader.Exercise_1(), "Arco da Calheta", "Calheta", "Ilha da Madeira (Madeira)");
    }
}
