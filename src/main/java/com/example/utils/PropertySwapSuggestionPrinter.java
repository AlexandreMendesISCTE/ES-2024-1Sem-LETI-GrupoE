package com.example.utils;

import java.util.List;
import java.util.Map;
import com.example.Property;
import com.example.PropertySwapSuggestion;

/**
 * Utility class to handle printing of property swap suggestions and related metrics.
 */
public class PropertySwapSuggestionPrinter {

    /**
     * Print the list of property swap suggestions.
     *
     * @param suggestions The list of PropertySwapSuggestion objects to be printed.
     */
    public static void printSuggestions(List<PropertySwapSuggestion> suggestions) {
        System.out.println("Top property swap suggestions with potential >= 0.75:");
        for (PropertySwapSuggestion suggestion : suggestions) {
            System.out.println(suggestion);
        }
    }

    /**
     * Print the average area of properties for each owner before and after swaps.
     *
     * @param properties The list of properties.
     * @param suggestions The list of swap suggestions to consider.
     */
    public static void printAverageAreaBeforeAndAfterSwap(List<Property> properties, List<PropertySwapSuggestion> suggestions) {
        // Calculate and print average area per owner before swaps for involved owners
        System.out.println("Average area per owner before swaps (only involved owners):");
        Map<String, Double> averageAreaBefore = PropertyGeometryUtils.calculateAverageAreaPerOwner(properties);
        for (String owner : PropertySwapManager.getInvolvedOwners(suggestions)) {
            if (averageAreaBefore.containsKey(owner)) {
                System.out.println("Owner: " + owner + ", Average Area: " + averageAreaBefore.get(owner));
            }
        }

        // Apply swaps
        PropertySwapManager.applySwaps(properties, suggestions);

        // Calculate and print average area per owner after swaps for involved owners
        System.out.println("\nAverage area per owner after swaps (only involved owners):");
        Map<String, Double> averageAreaAfter = PropertyGeometryUtils.calculateAverageAreaPerOwner(properties);
        for (String owner : PropertySwapManager.getInvolvedOwners(suggestions)) {
            if (averageAreaAfter.containsKey(owner)) {
                System.out.println("Owner: " + owner + ", Average Area: " + averageAreaAfter.get(owner));
            }
        }
    }
}
