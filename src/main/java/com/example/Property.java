package com.example;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.WKTReader;
import org.locationtech.jts.io.WKTWriter;

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
     * Method to show all adjacencies between properties.
     */
    public static void showAllAdjacencies(List<Property> properties, int limit) {
        if (properties == null || limit <= 0) {
            properties = CsvToPropertyReader.Exercise_1();
            limit = 120;
        }
        properties = properties.subList(0, limit); // Limit the number of properties to 120
        for (int i = 0; i < properties.size(); i++) {
            for (int j = i + 1; j < properties.size(); j++) {
                Property property1 = properties.get(i);
                Property property2 = properties.get(j);
                if (property1.isAdjacentTo(property2)) {
                    System.out.println(property1.getObjectId() + " is adjacent to " + property2.getObjectId());
                }
            }
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
     * Exercise_3 function calculates the average area of the properties filtered by "Freguesia", "Municipio", "Ilha", or all.
     *
     * @param properties The list of Property objects to be filtered and averaged.
     * @param filterType The type of filter to apply: "freguesia", "municipio", "ilha", or "all".
     * @param location The name of the location to filter by, or null if filterType is "all".
     */
    public static void Exercise_3(List<Property> properties, String filterType, String location) {
        List<Property> filteredProperties;

        switch (filterType.toLowerCase()) {
            case "freguesia":
                filteredProperties = CsvToPropertyReader.filterPropertiesByFreguesia(properties, location);
                break;
            case "municipio":
                filteredProperties = CsvToPropertyReader.filterPropertiesByMunicipio(properties, location);
                break;
            case "ilha":
                filteredProperties = CsvToPropertyReader.filterPropertiesByIlha(properties, location);
                break;
            case "all":
                System.out.println("Calculating average area for default values of Freguesia, Municipio, and Ilha...");
                // Default values
                String defaultFreguesia = "Arco da Calheta";
                String defaultMunicipio = "Calheta";
                String defaultIlha = "Ilha da Madeira (Madeira)";

                try {
                    // Calculate the average area for default "Freguesia"
                    filteredProperties = CsvToPropertyReader.filterPropertiesByFreguesia(properties, defaultFreguesia);
                    double averageAreaFreguesia = calculateAverageArea(filteredProperties);
                    System.out.println("Average area of properties in Freguesia '" + defaultFreguesia + "': " + averageAreaFreguesia);
                } catch (IllegalArgumentException e) {
                    System.err.println(e.getMessage());
                }

                try {
                    // Calculate the average area for default "Municipio"
                    filteredProperties = CsvToPropertyReader.filterPropertiesByMunicipio(properties, defaultMunicipio);
                    double averageAreaMunicipio = calculateAverageArea(filteredProperties);
                    System.out.println("Average area of properties in Municipio '" + defaultMunicipio + "': " + averageAreaMunicipio);
                } catch (IllegalArgumentException e) {
                    System.err.println(e.getMessage());
                }

                try {
                    // Calculate the average area for default "Ilha"
                    filteredProperties = CsvToPropertyReader.filterPropertiesByIlha(properties, defaultIlha);
                    double averageAreaIlha = calculateAverageArea(filteredProperties);
                    System.out.println("Average area of properties in Ilha '" + defaultIlha + "': " + averageAreaIlha);
                } catch (IllegalArgumentException e) {
                    System.err.println(e.getMessage());
                }
                return;
            default:
                throw new IllegalArgumentException("Invalid filter type: " + filterType);
        }

        try {
            // Calculate the average area of the filtered properties.
            double averageArea = calculateAverageArea(filteredProperties);
            System.out.println("Average area of properties for " + filterType + " '" + (location != null ? location : "all") + "': " + averageArea);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }


    /**
     * Method to merge properties by adjacency and ownership.
     *
     * @param properties The list of Property objects to be merged.
     * @return A new list of Property objects after merging adjacent properties with the same owner.
     */
    public static List<Property> mergePropertiesByAdjacencyAndOwner(List<Property> properties) {
        List<Property> mergedProperties = new ArrayList<>();
        Set<String> mergedObjectIds = new HashSet<>();
        GeometryFactory geometryFactory = new GeometryFactory();
        WKTReader reader = new WKTReader(geometryFactory);
        WKTWriter writer = new WKTWriter();

        for (Property property : properties) {
            // Skip properties that have already been merged
            if (mergedObjectIds.contains(property.getObjectId())) {
                continue;
            }

            Property currentProperty = property;
            boolean merged = false;

            for (Property otherProperty : properties) {
                // Skip if the properties are the same or if the other property has already been merged
                if (currentProperty.equals(otherProperty) || mergedObjectIds.contains(otherProperty.getObjectId())) {
                    continue;
                }

                // Check if properties are adjacent and have the same owner
                if (currentProperty.isAdjacentTo(otherProperty) && currentProperty.getOwner().equalsIgnoreCase(otherProperty.getOwner())) {
                    try {
                        // Read the geometries of both properties
                        Geometry geometry1 = reader.read(currentProperty.getGeometry());
                        Geometry geometry2 = reader.read(otherProperty.getGeometry());

                        // Merge the geometries
                        Geometry mergedGeometry = geometry1.union(geometry2);

                        // Convert the merged geometry back to WKT format
                        String mergedGeometryWKT = writer.write(mergedGeometry);

                        // Create a new Property with merged information
                        String mergedShapeArea = String.valueOf(mergedGeometry.getArea());
                        currentProperty = new Property(
                                currentProperty.getObjectId() + "_" + otherProperty.getObjectId(), // New ID combining both IDs
                                currentProperty.getParId(), // Parcel ID remains the same
                                currentProperty.getParNum(), // Parcel number remains the same
                                String.valueOf(mergedGeometry.getLength()), // Updated length
                                mergedShapeArea, // Updated area
                                mergedGeometryWKT, // Merged geometry in WKT format
                                currentProperty.getOwner(), // Owner remains the same
                                currentProperty.getFreguesia(), // Freguesia remains the same
                                currentProperty.getMunicipio(), // Municipio remains the same
                                currentProperty.getIlha() // Ilha remains the same
                        );

                        // Mark the other property as merged
                        mergedObjectIds.add(otherProperty.getObjectId());
                        merged = true;
                    } catch (Exception e) {
                        System.err.println("Error merging properties: " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            }

            // Add the merged or original property to the new list
            mergedProperties.add(currentProperty);
            mergedObjectIds.add(currentProperty.getObjectId());
        }

        return mergedProperties;
    }

    /**
     * Method to count adjacent properties with the same owner.
     *
     * @param freguesia The name of the "Freguesia" to filter by.
     */
    public static void countAdjacentPropertiesWithSameOwner(String freguesia) {
        List<Property> properties = CsvToPropertyReader.filterPropertiesByFreguesia(CsvToPropertyReader.Exercise_1(), freguesia);
        int count = 0;
        for (int i = 0; i < properties.size(); i++) {
            for (int j = i + 1; j < properties.size(); j++) {
                Property property1 = properties.get(i);
                Property property2 = properties.get(j);
                if (property1.isAdjacentTo(property2)) {
                    System.out.println(property1.getObjectId() + " is adjacent to " + property2.getObjectId());
                    if (property1.getOwner().equalsIgnoreCase(property2.getOwner())) {
                        System.out.println("The owners of the properties are the same");
                        count++;
                    }
                }
            }
        }
        System.err.println("Number of adjacent properties with the same owner: " + count);
    }

    /**
     * Method to test merging of properties with the same owner and print results.
     */
    public static void testMergeWithSameOwner(String freguesia) {
        // Count adjacent properties with the same owner
        countAdjacentPropertiesWithSameOwner(freguesia);

        // Print the number of properties in the filtered list
        List<Property> filteredProperties = CsvToPropertyReader.filterPropertiesByFreguesia(CsvToPropertyReader.Exercise_1(), freguesia);
        System.out.println("Number of properties in '" + freguesia + "': " + filteredProperties.size());

        // Merge properties by adjacency and owner
        List<Property> mergedProperties = mergePropertiesByAdjacencyAndOwner(filteredProperties);

        // Print the number of properties after merging
        System.out.println("Number of properties after merging: " + mergedProperties.size());
    }

    /**
     * Exercise_4 function filters properties by "Freguesia", "Municipio", "Ilha", or all, then merges adjacent properties with the same owner.
     *
     * @param properties The list of Property objects to be filtered and merged.
     * @param filterType The type of filter to apply: "freguesia", "municipio", "ilha", or "all".
     * @param location The name of the location to filter by, or null if filterType is "all".
     */
    public static void Exercise_4(List<Property> properties, String filterType, String location) {
        List<Property> filteredProperties;

        switch (filterType.toLowerCase()) {
            case "freguesia":
                filteredProperties = CsvToPropertyReader.filterPropertiesByFreguesia(properties, location);
                break;
            case "municipio":
                filteredProperties = CsvToPropertyReader.filterPropertiesByMunicipio(properties, location);
                break;
            case "ilha":
                filteredProperties = CsvToPropertyReader.filterPropertiesByIlha(properties, location);
                break;
            case "all":
                System.out.println("Merging properties for default values of Freguesia, Municipio, and Ilha...");
                // Default values
                String defaultFreguesia = "Arco da Calheta";
                String defaultMunicipio = "Calheta";
                String defaultIlha = "Ilha da Madeira (Madeira)";

                // Merge properties for default "Freguesia"
                filteredProperties = CsvToPropertyReader.filterPropertiesByFreguesia(properties, defaultFreguesia);
                List<Property> mergedFreguesia = mergePropertiesByAdjacencyAndOwner(filteredProperties);
                System.out.println("Number of properties after merging in Freguesia '" + defaultFreguesia + "': " + mergedFreguesia.size());
                double averageAreaFreguesia = calculateAverageArea(mergedFreguesia);
                System.out.println("Average area of merged properties in Freguesia '" + defaultFreguesia + "': " + averageAreaFreguesia);

                // Merge properties for default "Municipio"
                filteredProperties = CsvToPropertyReader.filterPropertiesByMunicipio(properties, defaultMunicipio);
                List<Property> mergedMunicipio = mergePropertiesByAdjacencyAndOwner(filteredProperties);
                System.out.println("Number of properties after merging in Municipio '" + defaultMunicipio + "': " + mergedMunicipio.size());
                double averageAreaMunicipio = calculateAverageArea(mergedMunicipio);
                System.out.println("Average area of merged properties in Municipio '" + defaultMunicipio + "': " + averageAreaMunicipio);

                // Merge properties for default "Ilha"
                filteredProperties = CsvToPropertyReader.filterPropertiesByIlha(properties, defaultIlha);
                List<Property> mergedIlha = mergePropertiesByAdjacencyAndOwner(filteredProperties);
                System.out.println("Number of properties after merging in Ilha '" + defaultIlha + "': " + mergedIlha.size());
                double averageAreaIlha = calculateAverageArea(mergedIlha);
                System.out.println("Average area of merged properties in Ilha '" + defaultIlha + "': " + averageAreaIlha);
                return;
            default:
                throw new IllegalArgumentException("Invalid filter type: " + filterType);
        }

        // Merge adjacent properties with the same owner in the filtered list
        List<Property> mergedProperties = mergePropertiesByAdjacencyAndOwner(filteredProperties);
        System.out.println("Number of properties after merging for " + filterType + " '" + (location != null ? location : "all") + "': " + mergedProperties.size());

        // Calculate the average area of the merged properties
        try {
            double averageArea = calculateAverageArea(mergedProperties);
            System.out.println("Average area of merged properties for " + filterType + " '" + (location != null ? location : "all") + "': " + averageArea);
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
     * This method runs various operations including showing adjacencies,
     * calculating average areas, counting adjacent properties with the same owner,
     * and merging properties where applicable.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        // Show all adjacencies between properties
        showAllAdjacencies(CsvToPropertyReader.Exercise_1(), 120);

        // Exercise_3 method is used in the following code
        Exercise_3(CsvToPropertyReader.Exercise_1(), "freguesia", "Arco da Calheta");
        Exercise_3(CsvToPropertyReader.Exercise_1(), "municipio", "Calheta");
        Exercise_3(CsvToPropertyReader.Exercise_1(), "ilha", "Ilha da Madeira (Madeira)");
        Exercise_3(CsvToPropertyReader.Exercise_1(), "all", null);
        
        // Count adjacent properties with the same owner
        testMergeWithSameOwner("Arco da Calheta");

        // Exercise_4 method is used in the following code
        Exercise_4(CsvToPropertyReader.Exercise_1(), "freguesia", "Arco da Calheta");
        Exercise_4(CsvToPropertyReader.Exercise_1(), "municipio", "Calheta");
        Exercise_4(CsvToPropertyReader.Exercise_1(), "ilha", "Ilha da Madeira (Madeira)");
        Exercise_4(CsvToPropertyReader.Exercise_1(), "all", null);
    }
}
