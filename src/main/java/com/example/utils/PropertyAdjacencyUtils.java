package com.example.utils;

import java.util.List;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.io.WKTReader;

import com.example.Property;

/**
 * Utility class for handling property adjacencies.
 */
public class PropertyAdjacencyUtils {

    /**
     * Method to check if two properties are adjacent.
     *
     * @param property1 The first property.
     * @param property2 The second property.
     * @return True if the properties are adjacent, false otherwise.
     */
    public static boolean areAdjacent(Property property1, Property property2) {
        try {
            GeometryFactory geometryFactory = new GeometryFactory();
            WKTReader reader = new WKTReader(geometryFactory);
            Geometry geometry1 = reader.read(property1.getGeometry());
            Geometry geometry2 = reader.read(property2.getGeometry());

            return geometry1.touches(geometry2) || geometry1.intersects(geometry2) || geometry1.contains(geometry2) || geometry2.contains(geometry1);
        } catch (Exception e) {
            System.err.println("Error reading geometries: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Shows all adjacencies between properties.
     *
     * @param properties The list of properties.
     * @param limit      The number of properties to consider.
     */
    public static void showAllAdjacencies(List<Property> properties, int limit) {
        properties = properties.subList(0, Math.min(limit, properties.size()));
        for (int i = 0; i < properties.size(); i++) {
            for (int j = i + 1; j < properties.size(); j++) {
                if (areAdjacent(properties.get(i), properties.get(j))) {
                    System.out.println(properties.get(i).getObjectId() + " is adjacent to " + properties.get(j).getObjectId());
                }
            }
        }
    }
}
