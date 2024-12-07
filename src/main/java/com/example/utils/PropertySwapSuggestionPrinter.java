package com.example.utils;

import java.util.List;
import java.util.Map;

import com.example.Property;
import com.example.PropertySwapSuggestion;

/**
 * Utility class to handle printing of property swap suggestions and related metrics.
 * This class provides methods to print swap suggestions and compare average property areas per owner before and after swaps.
 */
public class PropertySwapSuggestionPrinter {

    /**
     * Prints a list of property swap suggestions.
     * Only suggestions with a potential value of 0.75 or greater are displayed.
     *
     * @param suggestions The list of PropertySwapSuggestion objects to be printed.
     */
    public static void printSuggestions(List<PropertySwapSuggestion> suggestions) {
        System.out.println("Top property swap suggestions with potential >= 0.75:");
        // Iterate through the list of suggestions and print each one
        for (PropertySwapSuggestion suggestion : suggestions) {
            System.out.println(suggestion);
        }
    }

    /**
     * Prints the average property area per owner before and after applying the swaps.
     * Focuses on owners involved in the swap suggestions.
     *
     * @param properties  The list of Property objects.
     * @param suggestions The list of PropertySwapSuggestion objects representing potential swaps.
     */
    public static void printAverageAreaBeforeAndAfterSwap(List<Property> properties, List<PropertySwapSuggestion> suggestions) {
        // Print average area per owner before applying swaps
        System.out.println("Average area per owner before swaps (only involved owners):");
        Map<String, Double> averageAreaBefore = PropertyGeometryUtils.calculateAverageAreaPerOwner(properties);

        // Print averages only for owners involved in the swaps
        for (String owner : PropertySwapManager.getInvolvedOwners(suggestions)) {
            if (averageAreaBefore.containsKey(owner)) {
                System.out.println("Owner: " + owner + ", Average Area: " + averageAreaBefore.get(owner));
            }
        }

        // Apply the swap suggestions
        PropertySwapManager.applySwaps(properties, suggestions);

        // Print average area per owner after applying swaps
        System.out.println("\nAverage area per owner after swaps (only involved owners):");
        Map<String, Double> averageAreaAfter = PropertyGeometryUtils.calculateAverageAreaPerOwner(properties);

        // Print averages for the same set of involved owners
        for (String owner : PropertySwapManager.getInvolvedOwners(suggestions)) {
            if (averageAreaAfter.containsKey(owner)) {
                System.out.println("Owner: " + owner + ", Average Area: " + averageAreaAfter.get(owner));
            }
        }
    }
}
