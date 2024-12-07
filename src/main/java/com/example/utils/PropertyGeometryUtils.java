package com.example.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.WKTReader;

import com.example.Property;

/**
 * Utility class for handling geometric operations related to properties.
 * Provides methods for calculating centroids, average areas, and area statistics grouped by owners.
 */
public class PropertyGeometryUtils {

    /**
     * Calculates the centroid (midpoint) of a property's geometry.
     *
     * @param property The property for which the centroid is to be calculated.
     * @return The centroid of the property's geometry as a Point, or null if an error occurs.
     */
    public static Point getCentroid(Property property) {
        try {
            // Create a GeometryFactory and parse the property's geometry
            GeometryFactory geometryFactory = new GeometryFactory();
            WKTReader reader = new WKTReader(geometryFactory);
            Geometry geometry = reader.read(property.getGeometry());

            // Return the calculated centroid of the geometry
            return geometry.getCentroid();
        } catch (org.locationtech.jts.io.ParseException e) {
            // Handle parsing errors and return null
            System.err.println("Parse error reading geometry for centroid calculation: " + e.getMessage());
            return null;
        } catch (NullPointerException e) {
            // Handle cases where geometry is null
            System.err.println("Null pointer error reading geometry for centroid calculation: " + e.getMessage());
            return null;
        }
    }

    /**
     * Calculates the average area of a list of properties.
     *
     * @param properties The list of Property objects.
     * @return The average area as a double.
     * @throws IllegalArgumentException If the property list is null, empty, or contains no valid areas.
     */
    public static double calculateAverageArea(List<Property> properties) {
        if (properties == null || properties.isEmpty()) {
            throw new IllegalArgumentException("Property list cannot be null or empty");
        }

        double totalArea = 0.0; // Accumulator for total area
        int count = 0; // Counter for valid properties

        // Iterate through properties to calculate the total area
        for (Property property : properties) {
            try {
                double area = Double.parseDouble(property.getShapeArea());
                totalArea += area;
                count++;
            } catch (NumberFormatException e) {
                // Log invalid area values and continue
                System.err.println("Invalid area value for property " + property.getObjectId() + ": " + property.getShapeArea());
            }
        }

        if (count == 0) {
            throw new IllegalArgumentException("No valid property areas found");
        }

        return totalArea / count; // Calculate and return the average area
    }

    /**
     * Calculates the average area of properties for each owner.
     *
     * @param properties The list of Property objects to be analyzed.
     * @return A map containing the average area per owner.
     */
    public static Map<String, Double> calculateAverageAreaPerOwner(List<Property> properties) {
        Map<String, List<Double>> ownerAreas = new HashMap<>();

        // Group property areas by owner
        for (Property property : properties) {
            try {
                double area = Double.parseDouble(property.getShapeArea());
                ownerAreas.computeIfAbsent(property.getOwner(), k -> new ArrayList<>()).add(area);
            } catch (NumberFormatException e) {
                // Log invalid area values and continue
                System.err.println("Invalid area value for property " + property.getObjectId() + ": " + property.getShapeArea());
            }
        }

        // Calculate the average area for each owner
        Map<String, Double> averageAreaPerOwner = new HashMap<>();
        for (Map.Entry<String, List<Double>> entry : ownerAreas.entrySet()) {
            List<Double> areas = entry.getValue();

            // Calculate the total and average areas for the current owner
            double totalArea = areas.stream().mapToDouble(Double::doubleValue).sum();
            double averageArea = totalArea / areas.size();
            averageAreaPerOwner.put(entry.getKey(), averageArea);
        }

        return averageAreaPerOwner;
    }
}
