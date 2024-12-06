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
 */
public class PropertyGeometryUtils {

    /**
     * Calculates the centroid (midpoint) of a property's geometry.
     *
     * @param property The property for which the centroid is to be calculated.
     * @return The centroid of the property's geometry as a Point.
     */
    public static Point getCentroid(Property property) {
        try {
            GeometryFactory geometryFactory = new GeometryFactory();
            WKTReader reader = new WKTReader(geometryFactory);
            Geometry geometry = reader.read(property.getGeometry());
            return geometry.getCentroid();
        } catch (Exception e) {
            System.err.println("Error reading geometry for centroid calculation: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Calculates the average area of a list of properties.
     *
     * @param properties The list of Property objects.
     * @return The average area as a double.
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
     * Calculate the average area of properties for each owner.
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
                System.err.println("Invalid area value for property " + property.getObjectId() + ": " + property.getShapeArea());
            }
        }

        // Calculate the average area for each owner
        Map<String, Double> averageAreaPerOwner = new HashMap<>();
        for (Map.Entry<String, List<Double>> entry : ownerAreas.entrySet()) {
            List<Double> areas = entry.getValue();
            double totalArea = areas.stream().mapToDouble(Double::doubleValue).sum();
            double averageArea = totalArea / areas.size();
            averageAreaPerOwner.put(entry.getKey(), averageArea);
        }

        return averageAreaPerOwner;
    }
}
