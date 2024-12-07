package com.example.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.io.WKTReader;
import org.locationtech.jts.io.WKTWriter;

import com.example.Property;

/**
 * Utility class for merging adjacent properties with the same owner.
 * Provides methods for merging properties based on adjacency and ownership.
 */
public class PropertyMergeUtils {

    /**
     * Merges adjacent properties with the same owner into a single property.
     * Uses geometry operations to merge the shapes of adjacent properties.
     *
     * @param properties The list of Property objects to be merged.
     * @return A new list of Property objects after merging adjacent properties with the same owner.
     */
    public static List<Property> mergePropertiesByAdjacencyAndOwner(List<Property> properties) {
        List<Property> mergedProperties = new ArrayList<>();
        Set<String> mergedObjectIds = new HashSet<>(); // Tracks merged properties by their Object IDs
        GeometryFactory geometryFactory = new GeometryFactory();
        WKTReader reader = new WKTReader(geometryFactory);
        WKTWriter writer = new WKTWriter();

        // Iterate through each property
        for (Property property : properties) {
            if (mergedObjectIds.contains(property.getObjectId())) {
                // Skip properties that are already merged
                continue;
            }

            Property currentProperty = property; // Start with the current property

            // Check adjacency and ownership for merging
            for (Property otherProperty : properties) {
                if (currentProperty.equals(otherProperty) || mergedObjectIds.contains(otherProperty.getObjectId())) {
                    continue; // Skip identical or already merged properties
                }

                // Merge if the properties are adjacent and share the same owner
                if (PropertyAdjacencyUtils.areAdjacent(currentProperty, otherProperty) &&
                    currentProperty.getOwner().equalsIgnoreCase(otherProperty.getOwner())) {
                    try {
                        // Read and merge the geometries of the properties
                        Geometry geometry1 = reader.read(currentProperty.getGeometry());
                        Geometry geometry2 = reader.read(otherProperty.getGeometry());

                        Geometry mergedGeometry = geometry1.union(geometry2);
                        String mergedGeometryWKT = writer.write(mergedGeometry);

                        // Update the properties with the merged geometry and attributes
                        String mergedShapeArea = String.valueOf(mergedGeometry.getArea());
                        currentProperty = new Property(
                                currentProperty.getObjectId() + "_" + otherProperty.getObjectId(), // Combine IDs
                                currentProperty.getParId(),
                                currentProperty.getParNum(),
                                String.valueOf(mergedGeometry.getLength()), // Update shape length
                                mergedShapeArea, // Update shape area
                                mergedGeometryWKT, // Merged geometry in WKT format
                                currentProperty.getOwner(),
                                currentProperty.getFreguesia(),
                                currentProperty.getMunicipio(),
                                currentProperty.getIlha()
                        );

                        mergedObjectIds.add(otherProperty.getObjectId()); // Mark the other property as merged
                    } catch (Exception e) {
                        // Handle errors during merging
                        System.err.println("Error merging properties: " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            }

            // Add the updated property to the merged list
            mergedProperties.add(currentProperty);
            mergedObjectIds.add(currentProperty.getObjectId()); // Mark the current property as merged
        }

        return mergedProperties;
    }

    /**
     * Simplifies merging of adjacent properties with the same owner.
     * Combines areas of properties that are adjacent and belong to the same owner.
     *
     * @param properties The list of properties to be merged.
     * @return A new list of properties after merging adjacent properties with the same owner.
     */
    public static List<Property> mergeProperties(List<Property> properties) {
        List<Property> mergedProperties = new ArrayList<>(properties); // Copy of the properties list

        // Iterate through the properties for merging
        for (int i = 0; i < mergedProperties.size(); i++) {
            Property property1 = mergedProperties.get(i);
            for (int j = i + 1; j < mergedProperties.size(); j++) {
                Property property2 = mergedProperties.get(j);

                // Check if the properties have the same owner and are adjacent
                if (property1.getOwner().equalsIgnoreCase(property2.getOwner()) &&
                    PropertyAdjacencyUtils.areAdjacent(property1, property2)) {
                    // Merge property2 into property1
                    double newArea = Double.parseDouble(property1.getShapeArea()) +
                                     Double.parseDouble(property2.getShapeArea());
                    property1.setShapeArea(String.valueOf(newArea)); // Update the area of property1
                    mergedProperties.remove(j); // Remove property2 after merging
                    j--; // Adjust index after removal
                }
            }
        }

        return mergedProperties;
    }
}
