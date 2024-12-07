package com.example.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.example.Property;
import com.example.PropertySwapSuggestion;

/**
 * Utility class to handle property swap suggestions.
 * Provides methods to generate, apply, and analyze potential property swaps between owners.
 */
public class PropertySwapManager {

    /**
     * Generates suggestions for property swaps between owners to maximize the average area per owner.
     *
     * @param properties The list of Property objects to be analyzed for potential swaps.
     * @return A list of PropertySwapSuggestion objects representing potential swaps, sorted by potential.
     */
    public static List<PropertySwapSuggestion> generateSwapSuggestions(List<Property> properties) {
        List<PropertySwapSuggestion> suggestions = new ArrayList<>();

        // Iterate over all pairs of properties
        for (int i = 0; i < properties.size(); i++) {
            Property property1 = properties.get(i);
            for (int j = i + 1; j < properties.size(); j++) {
                Property property2 = properties.get(j);

                // Skip pairs with the same owner
                if (property1.getOwner().equalsIgnoreCase(property2.getOwner())) {
                    continue;
                }

                // Check if property2 is adjacent to any property owned by property1's owner
                boolean hasAdjacentToOwner = false;
                for (Property property : properties) {
                    if (property.getOwner().equalsIgnoreCase(property1.getOwner()) &&
                        PropertyAdjacencyUtils.areAdjacent(property, property2)) {
                        hasAdjacentToOwner = true;
                        break;
                    }
                }

                // Skip if no adjacency to the owner
                if (!hasAdjacentToOwner) {
                    continue;
                }

                try {
                    // Calculate swap potential based on the areas of the two properties
                    double area1 = Double.parseDouble(property1.getShapeArea());
                    double area2 = Double.parseDouble(property2.getShapeArea());
                    double maiorArea = Math.max(area1, area2);
                    double menorArea = Math.min(area1, area2);
                    double potential = menorArea / maiorArea;

                    // Add the swap suggestion if the potential meets the threshold (>= 0.75)
                    if (potential >= 0.75) {
                        suggestions.add(new PropertySwapSuggestion(property1, property2, potential));
                    }
                } catch (NumberFormatException e) {
                    // Log error for invalid area values
                    System.err.println("Invalid area value for properties " + property1.getObjectId() +
                                       " or " + property2.getObjectId());
                }
            }
        }

        // Sort suggestions by potential in descending order
        suggestions.sort((s1, s2) -> Double.compare(s2.getPotential(), s1.getPotential()));

        return suggestions;
    }

    /**
     * Applies the swaps suggested by {@code generateSwapSuggestions} to the list of properties.
     * Updates the owner of the properties involved in the swaps.
     *
     * @param properties  The original list of properties.
     * @param suggestions The list of PropertySwapSuggestion objects representing potential swaps.
     */
    public static void applySwaps(List<Property> properties, List<PropertySwapSuggestion> suggestions) {
        // Iterate through the list of swap suggestions
        for (PropertySwapSuggestion suggestion : suggestions) {
            String newOwner = suggestion.getProperty2().getOwner(); // New owner for property1
            for (Property property : properties) {
                // Update the owner of property1 to the owner of property2
                if (property.getObjectId().equals(suggestion.getProperty1().getObjectId())) {
                    property.setOwner(newOwner);
                }
            }
        }
    }

    /**
     * Retrieves the set of owner IDs involved in the swap suggestions.
     *
     * @param suggestions The list of PropertySwapSuggestion objects representing potential swaps.
     * @return A set of owner IDs involved in the swaps.
     */
    public static Set<String> getInvolvedOwners(List<PropertySwapSuggestion> suggestions) {
        Set<String> owners = new HashSet<>();
        // Collect unique owner IDs from the suggestions
        for (PropertySwapSuggestion suggestion : suggestions) {
            owners.add(suggestion.getProperty1().getOwner());
            owners.add(suggestion.getProperty2().getOwner());
        }
        return owners;
    }
}
