package com.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The PropertySwapSuggestion class is responsible for generating suggestions for property swaps between owners.
 * The goal is to maximize the average area of properties per owner, while considering the minimal cost for the owners
 * to perform the swap.
 */
public class PropertySwapSuggestion {
    private final Property property1;
    private final Property property2;
    private final double potential;

    public PropertySwapSuggestion(Property property1, Property property2, double potential) {
        this.property1 = property1;
        this.property2 = property2;
        this.potential = potential;
    }

    public Property getProperty1() {
        return property1;
    }

    public Property getProperty2() {
        return property2;
    }

    public double getPotential() {
        return potential;
    }

    @Override
    public String toString() {
        return "Swap between Property " + property1.getObjectId() + " and Property " + property2.getObjectId() + " with potential " + potential;
    }

    /**
     * Generate suggestions for property swaps between owners to maximize the average area per owner.
     * This method aims to generate suggestions that would increase the average property area per owner,
     * considering the minimal cost for the owners to perform the swap and ensuring adjacency.
     *
     * @param properties The list of Property objects to be analyzed for potential swaps.
     */
    public static void generateSwapSuggestions(List<Property> properties) {
        List<PropertySwapSuggestion> suggestions = new ArrayList<>();

        // Iterate over each pair of properties owned by different owners
        for (int i = 0; i < properties.size(); i++) {
            Property property1 = properties.get(i);
            for (int j = i + 1; j < properties.size(); j++) {
                Property property2 = properties.get(j);

                // Skip if both properties have the same owner
                if (property1.getOwner().equalsIgnoreCase(property2.getOwner())) {
                    continue;
                }

                // Check if property2 is adjacent to any other property owned by property1's owner
                boolean hasAdjacentToOwner = false;
                for (Property property : properties) {
                    if (property.getOwner().equalsIgnoreCase(property1.getOwner()) && property.isAdjacentTo(property2)) {
                        hasAdjacentToOwner = true;
                        break;
                    }
                }

                if (!hasAdjacentToOwner) {
                    continue;
                }

                try {
                    double area1 = Double.parseDouble(property1.getShapeArea());
                    double area2 = Double.parseDouble(property2.getShapeArea());
                    double areaDifference = Math.abs(area1 - area2);

                    // Calculate the potential of the swap based on area difference
                    double potential = 1.0 / (1.0 + areaDifference);

                    // Create a suggestion if the potential is above a threshold (e.g., 0.75)
                    if (potential >= 0.75) {
                        suggestions.add(new PropertySwapSuggestion(property1, property2, potential));
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Invalid area value for properties " + property1.getObjectId() + " or " + property2.getObjectId());
                }
            }
        }

        // Sort suggestions by potential in descending order
        suggestions.sort((s1, s2) -> Double.compare(s2.getPotential(), s1.getPotential()));

        // Print the top suggestions
        System.out.println("Top property swap suggestions with potential >= 0.75:");
        for (PropertySwapSuggestion suggestion : suggestions) {
            System.out.println(suggestion);
        }

        // Calculate and print average area before and after swap for owners involved in swaps
        printAverageAreaBeforeAndAfterSwap(properties, suggestions);

        // Apply swaps and merge properties
        List<Property> swappedProperties = applySwaps(properties, suggestions);
        List<Property> mergedProperties = mergeProperties(swappedProperties);

        // Calculate and print average area per owner after merging for involved owners
        System.out.println("\nAverage area per owner after merging (only involved owners):");
        Map<String, Double> averageAreaAfterMerging = calculateAverageAreaPerOwner(mergedProperties);
        for (String owner : involvedOwners(suggestions)) {
            if (averageAreaAfterMerging.containsKey(owner)) {
                System.out.println("Owner: " + owner + ", Average Area: " + averageAreaAfterMerging.get(owner));
            }
        }
    }

    /**
     * Calculate and print the average area of properties for each owner before and after the swap.
     * Only prints the areas for owners involved in the swaps.
     *
     * @param properties The list of Property objects to calculate average areas for.
     * @param suggestions The list of PropertySwapSuggestion objects representing potential swaps.
     */
    public static void printAverageAreaBeforeAndAfterSwap(List<Property> properties, List<PropertySwapSuggestion> suggestions) {
        // Calculate and print average area per owner before swaps for involved owners
        System.out.println("Average area per owner before swaps (only involved owners):");
        Map<String, Double> averageAreaBefore = calculateAverageAreaPerOwner(properties);
        for (String owner : involvedOwners(suggestions)) {
            if (averageAreaBefore.containsKey(owner)) {
                System.out.println("Owner: " + owner + ", Average Area: " + averageAreaBefore.get(owner));
            }
        }

        // Apply swaps
        for (PropertySwapSuggestion suggestion : suggestions) {
            swapProperties(suggestion);
        }

        // Calculate and print average area per owner after swaps for involved owners
        System.out.println("\nAverage area per owner after swaps (only involved owners):");
        Map<String, Double> averageAreaAfter = calculateAverageAreaPerOwner(properties);
        for (String owner : involvedOwners(suggestions)) {
            if (averageAreaAfter.containsKey(owner)) {
                System.out.println("Owner: " + owner + ", Average Area: " + averageAreaAfter.get(owner));
            }
        }
    }

    /**
     * Apply the swaps and create a new list of properties with updated ownership.
     *
     * @param properties The original list of properties.
     * @param suggestions The list of PropertySwapSuggestion objects representing potential swaps.
     * @return A new list of properties with updated ownership after applying the swaps.
     */
    private static List<Property> applySwaps(List<Property> properties, List<PropertySwapSuggestion> suggestions) {
        List<Property> swappedProperties = new ArrayList<>(properties);

        for (PropertySwapSuggestion suggestion : suggestions) {
            String newOwner = suggestion.getProperty2().getOwner();
            for (Property property : swappedProperties) {
                if (property.getObjectId().equals(suggestion.getProperty1().getObjectId())) {
                    property.setOwner(newOwner);
                }
            }
        }

        return swappedProperties;
    }

    /**
     * Merge adjacent properties with the same owner.
     *
     * @param properties The list of properties to be merged.
     * @return A new list of properties after merging adjacent properties with the same owner.
     */
    private static List<Property> mergeProperties(List<Property> properties) {
        List<Property> mergedProperties = new ArrayList<>(properties);

        // Logic to merge adjacent properties with the same owner
        for (int i = 0; i < mergedProperties.size(); i++) {
            Property property1 = mergedProperties.get(i);
            for (int j = i + 1; j < mergedProperties.size(); j++) {
                Property property2 = mergedProperties.get(j);
                if (property1.getOwner().equalsIgnoreCase(property2.getOwner()) && property1.isAdjacentTo(property2)) {
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

    /**
     * Calculate the average area of properties for each owner.
     *
     * @param properties The list of Property objects to be analyzed.
     * @return A map containing the average area per owner.
     */
    private static Map<String, Double> calculateAverageAreaPerOwner(List<Property> properties) {
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

    /**
     * Swap the ownership of two properties as suggested.
     *
     * @param suggestion The PropertySwapSuggestion representing the swap.
     */
    private static void swapProperties(PropertySwapSuggestion suggestion) {
        String tempOwner = suggestion.getProperty1().getOwner();
        suggestion.getProperty1().setOwner(suggestion.getProperty2().getOwner());
        suggestion.getProperty2().setOwner(tempOwner);
    }

    /**
     * Get the set of owners involved in the swap suggestions.
     *
     * @param suggestions The list of PropertySwapSuggestion objects representing potential swaps.
     * @return A set of owner IDs involved in the swaps.
     */
    private static Set<String> involvedOwners(List<PropertySwapSuggestion> suggestions) {
        Set<String> owners = new HashSet<>();
        for (PropertySwapSuggestion suggestion : suggestions) {
            owners.add(suggestion.getProperty1().getOwner());
            owners.add(suggestion.getProperty2().getOwner());
        }
        return owners;
    }

    /**
     * Print the owner and area of specific properties by their object IDs.
     * This method filters properties by a default freguesia and checks if specific object IDs are present.
     *
     * The filtered properties are iterated over to find the specified properties, and their owner IDs and areas are printed.
     */
    public static void printOwnerAndAreaForProperties() {
        // Load properties from CSV file
        List<Property> properties = CsvToPropertyReader.Exercise_1();
        String defaultFreguesia = "Arco da Calheta";
        List<Property> filteredProperties = CsvToPropertyReader.filterPropertiesByFreguesia(properties, defaultFreguesia);

        // IDs of the target properties
        List<String> targetObjectIds = Arrays.asList("557", "664", "158", "698");

        // Iterate through the filtered list and print owner ID and area for target properties
        for (Property property : filteredProperties) {
            String objectId = String.valueOf(property.getObjectId());
            if (targetObjectIds.contains(objectId)) {
                System.out.println("Object ID: " + objectId + " | Owner ID: " + property.getOwner() + " | Area: " + property.getShapeArea() + " m²");
            }
        }
    }

    /**
     * Exercise 6 - Generate swap suggestions and print owner and area for specific properties.
     */
    public static void Exercise_6(String freguesia) {
        List<Property> properties = CsvToPropertyReader.Exercise_1();
        if(freguesia == null) {
            freguesia = "Arco da Calheta";
        }
        List<Property> filteredProperties = CsvToPropertyReader.filterPropertiesByFreguesia(properties, freguesia);

        // Generate swap suggestions for filtered properties
        generateSwapSuggestions(filteredProperties);

        // Print owner and area for specific properties
        // TEST
        //printOwnerAndAreaForProperties();
    }

    /**
     * Main method to run swap suggestions on filtered properties.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        Exercise_6("Arco da Calheta");
    }
}
