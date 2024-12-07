package com.example.utils;

import java.util.List;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.io.WKTReader;

import com.example.Property;

/**
 * Utility class for handling property adjacencies.
 * Provides methods to determine if two properties are adjacent and to display all adjacencies in a list of properties.
 */
public class PropertyAdjacencyUtils {

    /**
     * Determines if two properties are adjacent based on their geometries.
     * Adjacency is defined as the geometries touching, intersecting, or one containing the other.
     *
     * @param property1 The first property.
     * @param property2 The second property.
     * @return True if the properties are adjacent, false otherwise.
     */
    public static boolean areAdjacent(Property property1, Property property2) {
        try {
            // Create a GeometryFactory and WKTReader to parse the geometry strings
            GeometryFactory geometryFactory = new GeometryFactory();
            WKTReader reader = new WKTReader(geometryFactory);

            // Parse the geometries of both properties
            Geometry geometry1 = reader.read(property1.getGeometry());
            Geometry geometry2 = reader.read(property2.getGeometry());

            // Check if the geometries are adjacent based on the JTS rules
            return geometry1.touches(geometry2) ||
                   geometry1.intersects(geometry2) ||
                   geometry1.contains(geometry2) ||
                   geometry2.contains(geometry1);
        } catch (Exception e) {
            // Handle any errors during geometry parsing
            System.err.println("Error reading geometries: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Displays all adjacency relationships among a list of properties.
     * Iterates through pairs of properties and logs adjacency relationships.
     *
     * @param properties The list of properties to analyze.
     * @param limit      The maximum number of properties to consider (for performance reasons).
     */
    public static void showAllAdjacencies(List<Property> properties, int limit) {
        // Limit the number of properties to consider
        properties = properties.subList(0, Math.min(limit, properties.size()));

        // Iterate over all pairs of properties
        for (int i = 0; i < properties.size(); i++) {
            for (int j = i + 1; j < properties.size(); j++) {
                // Check if the current pair of properties are adjacent
                if (areAdjacent(properties.get(i), properties.get(j))) {
                    // Log the adjacency relationship
                    System.out.println(properties.get(i).getObjectId() +
                                       " is adjacent to " + properties.get(j).getObjectId());
                }
            }
        }
    }
}
