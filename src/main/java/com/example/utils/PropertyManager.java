package com.example.utils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.example.CsvToPropertyReader;
import com.example.Property;

/**
 * Utility class to manage property-related operations.
 */
public class PropertyManager {

    private List<Property> properties;

    /**
     * Constructor for PropertyManager.
     * Initializes the list of properties by reading from the CSV.
     */
    public PropertyManager() {
        properties = CsvToPropertyReader.Exercise_1();
    }

    /**
     * Filters properties by a given type and location.
     *
     * @param filterType The type of filter: "freguesia", "municipio", or "ilha".
     * @param location   The location name.
     * @return A filtered list of Property objects.
     */
    public List<Property> filterProperties(String filterType, String location) {
        switch (filterType.toLowerCase()) {
            case "freguesia":
                return CsvToPropertyReader.filterPropertiesByFreguesia(properties, location);
            case "municipio":
                return CsvToPropertyReader.filterPropertiesByMunicipio(properties, location);
            case "ilha":
                return CsvToPropertyReader.filterPropertiesByIlha(properties, location);
            default:
                throw new IllegalArgumentException("Invalid filter type");
        }
    }

    /**
     * Gets unique locations based on the given filter type.
     *
     * @param filterType The type of filter: "freguesia", "municipio", or "ilha".
     * @return A set of unique location names.
     */
    public Set<String> getUniqueLocations(String filterType) {
        switch (filterType.toLowerCase()) {
            case "freguesia":
                return properties.stream().map(Property::getFreguesia).collect(Collectors.toSet());
            case "municipio":
                return properties.stream().map(Property::getMunicipio).collect(Collectors.toSet());
            case "ilha":
                return properties.stream().map(Property::getIlha).collect(Collectors.toSet());
            default:
                throw new IllegalArgumentException("Invalid filter type");
        }
    }

    /**
     * Gets the list of properties.
     *
     * @return List of properties.
     */
    public List<Property> getProperties() {
        return properties;
    }

    /**
     * Gets adjacent properties for a given property.
     *
     * @param property The property to find adjacent properties for.
     * @return A set of adjacent properties.
     */
    public Set<Property> getAdjacentProperties(Property property) {
        return properties.stream()
                .filter(otherProperty -> !property.equals(otherProperty) && PropertyAdjacencyUtils.areAdjacent(property, otherProperty))
                .collect(Collectors.toSet());
    }
}
