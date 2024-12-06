package com.example.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.example.Property;
import com.example.PropertySwapSuggestion;

/**
 * Utility class to handle property swap suggestions.
 */
public class PropertySwapManager {

    /**
     * Generate suggestions for property swaps between owners to maximize the average area per owner.
     *
     * @param properties The list of Property objects to be analyzed for potential swaps.
     * @return A list of PropertySwapSuggestion objects representing potential swaps.
     */
    public static List<PropertySwapSuggestion> generateSwapSuggestions(List<Property> properties) {
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
                    if (property.getOwner().equalsIgnoreCase(property1.getOwner()) && PropertyAdjacencyUtils.areAdjacent(property, property2)) {
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

        return suggestions;
    }

    /**
     * Apply the swaps and create a new list of properties with updated ownership.
     *
     * @param properties The original list of properties.
     * @param suggestions The list of PropertySwapSuggestion objects representing potential swaps.
     */
    public static void applySwaps(List<Property> properties, List<PropertySwapSuggestion> suggestions) {
        for (PropertySwapSuggestion suggestion : suggestions) {
            String newOwner = suggestion.getProperty2().getOwner();
            for (Property property : properties) {
                if (property.getObjectId().equals(suggestion.getProperty1().getObjectId())) {
                    property.setOwner(newOwner);
                }
            }
        }
    }

    /**
     * Get the set of owners involved in the swap suggestions.
     *
     * @param suggestions The list of PropertySwapSuggestion objects representing potential swaps.
     * @return A set of owner IDs involved in the swaps.
     */
    public static Set<String> getInvolvedOwners(List<PropertySwapSuggestion> suggestions) {
        Set<String> owners = new HashSet<>();
        for (PropertySwapSuggestion suggestion : suggestions) {
            owners.add(suggestion.getProperty1().getOwner());
            owners.add(suggestion.getProperty2().getOwner());
        }
        return owners;
    }
}
