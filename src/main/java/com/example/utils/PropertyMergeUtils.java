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
 */
public class PropertyMergeUtils {

    /**
     * Merges adjacent properties with the same owner.
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
            if (mergedObjectIds.contains(property.getObjectId())) {
                continue;
            }

            Property currentProperty = property;

            for (Property otherProperty : properties) {
                if (currentProperty.equals(otherProperty) || mergedObjectIds.contains(otherProperty.getObjectId())) {
                    continue;
                }

                if (PropertyAdjacencyUtils.areAdjacent(currentProperty, otherProperty) && currentProperty.getOwner().equalsIgnoreCase(otherProperty.getOwner())) {
                    try {
                        Geometry geometry1 = reader.read(currentProperty.getGeometry());
                        Geometry geometry2 = reader.read(otherProperty.getGeometry());

                        Geometry mergedGeometry = geometry1.union(geometry2);
                        String mergedGeometryWKT = writer.write(mergedGeometry);

                        String mergedShapeArea = String.valueOf(mergedGeometry.getArea());
                        currentProperty = new Property(
                                currentProperty.getObjectId() + "_" + otherProperty.getObjectId(),
                                currentProperty.getParId(),
                                currentProperty.getParNum(),
                                String.valueOf(mergedGeometry.getLength()),
                                mergedShapeArea,
                                mergedGeometryWKT,
                                currentProperty.getOwner(),
                                currentProperty.getFreguesia(),
                                currentProperty.getMunicipio(),
                                currentProperty.getIlha()
                        );

                        mergedObjectIds.add(otherProperty.getObjectId());
                    } catch (Exception e) {
                        System.err.println("Error merging properties: " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            }

            mergedProperties.add(currentProperty);
            mergedObjectIds.add(currentProperty.getObjectId());
        }

        return mergedProperties;
    }

    /**
     * Merge adjacent properties with the same owner.
     *
     * @param properties The list of properties to be merged.
     * @return A new list of properties after merging adjacent properties with the same owner.
     */
    public static List<Property> mergeProperties(List<Property> properties) {
        List<Property> mergedProperties = new ArrayList<>(properties);

        // Logic to merge adjacent properties with the same owner
        for (int i = 0; i < mergedProperties.size(); i++) {
            Property property1 = mergedProperties.get(i);
            for (int j = i + 1; j < mergedProperties.size(); j++) {
                Property property2 = mergedProperties.get(j);
                if (property1.getOwner().equalsIgnoreCase(property2.getOwner()) && PropertyAdjacencyUtils.areAdjacent(property1, property2)) {
                    // Merge property2 into property1
                    double newArea = Double.parseDouble(property1.getShapeArea()) + Double.parseDouble(property2.getShapeArea());
                    property1.setShapeArea(String.valueOf(newArea));
                    mergedProperties.remove(j);
                    j--; // Adjust index after removal
                }
            }
        }

        return mergedProperties;
    }
}
