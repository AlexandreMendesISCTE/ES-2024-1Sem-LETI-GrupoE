package com.example.utils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.example.CsvToPropertyReader;
import com.example.Property;

/**
 * Utility class to manage property-related operations.
 * Provides methods for filtering, retrieving unique locations, and finding adjacent properties.
 */
public class PropertyManager {

    private final List<Property> properties;

    /**
     * Constructor for PropertyManager.
     * Initializes the list of properties by reading data from a CSV file using {@link CsvToPropertyReader}.
     */
    public PropertyManager() {
        // Load properties from the CSV file
        properties = CsvToPropertyReader.Exercise_1();
    }

    /**
     * Filters properties based on a given type and location.
     *
     * @param filterType The type of filter: "freguesia", "municipio", or "ilha".
     * @param location   The location name to filter by.
     * @return A filtered list of Property objects matching the given criteria.
     * @throws IllegalArgumentException If the filter type is invalid.
     */
    public List<Property> filterProperties(String filterType, String location) {
        switch (filterType.toLowerCase()) {
            case "freguesia":
                // Filter properties by "Freguesia"
                return CsvToPropertyReader.filterPropertiesByFreguesia(properties, location);
            case "municipio":
                // Filter properties by "Municipio"
                return CsvToPropertyReader.filterPropertiesByMunicipio(properties, location);
            case "ilha":
                // Filter properties by "Ilha"
                return CsvToPropertyReader.filterPropertiesByIlha(properties, location);
            default:
                // Throw exception for unsupported filter types
                throw new IllegalArgumentException("Invalid filter type");
        }
    }

    /**
     * Retrieves unique locations based on a given filter type.
     *
     * @param filterType The type of filter: "freguesia", "municipio", or "ilha".
     * @return A set of unique location names.
     * @throws IllegalArgumentException If the filter type is invalid.
     */
    public Set<String> getUniqueLocations(String filterType) {
        switch (filterType.toLowerCase()) {
            case "freguesia":
                // Get unique "Freguesia" values
                return properties.stream().map(Property::getFreguesia).collect(Collectors.toSet());
            case "municipio":
                // Get unique "Municipio" values
                return properties.stream().map(Property::getMunicipio).collect(Collectors.toSet());
            case "ilha":
                // Get unique "Ilha" values
                return properties.stream().map(Property::getIlha).collect(Collectors.toSet());
            default:
                // Throw exception for unsupported filter types
                throw new IllegalArgumentException("Invalid filter type");
        }
    }

    /**
     * Retrieves the list of all properties.
     *
     * @return The complete list of properties managed by this instance.
     */
    public List<Property> getProperties() {
        // Return the loaded list of properties
        return properties;
    }

    /**
     * Finds and retrieves adjacent properties for a given property.
     *
     * @param property The property for which adjacent properties are to be found.
     * @return A set of adjacent properties.
     */
    public Set<Property> getAdjacentProperties(Property property) {
        // Find properties that are adjacent to the given property
        return properties.stream()
                .filter(otherProperty -> 
                        !property.equals(otherProperty) && 
                        PropertyAdjacencyUtils.areAdjacent(property, otherProperty))
                .collect(Collectors.toSet());
    }
}
