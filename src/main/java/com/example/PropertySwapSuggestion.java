package com.example;

import java.util.List;

import com.example.utils.PropertySwapManager;
import com.example.utils.PropertySwapSuggestionPrinter;

/**
 * The PropertySwapSuggestion class represents a suggestion for swapping two properties
 * between different owners. The objective is to maximize the average property area per owner
 * while minimizing the cost and effort of the swap.
 *
 * This class also contains utility methods for generating and printing swap suggestions
 * based on property data.
 */
public class PropertySwapSuggestion {

    private final Property property1; // The first property in the swap
    private final Property property2; // The second property in the swap
    private final double potential;   // The potential of the swap, indicating its effectiveness

    /**
     * Constructor to create a PropertySwapSuggestion instance.
     *
     * @param property1 The first property involved in the swap.
     * @param property2 The second property involved in the swap.
     * @param potential The potential effectiveness of the swap.
     */
    public PropertySwapSuggestion(Property property1, Property property2, double potential) {
        this.property1 = property1;
        this.property2 = property2;
        this.potential = potential;
    }

    /**
     * Gets the first property involved in the swap.
     *
     * @return The first property.
     */
    public Property getProperty1() {
        return property1;
    }

    /**
     * Gets the second property involved in the swap.
     *
     * @return The second property.
     */
    public Property getProperty2() {
        return property2;
    }

    /**
     * Gets the potential effectiveness of the swap.
     *
     * @return The potential value.
     */
    public double getPotential() {
        return potential;
    }

    /**
     * Returns a string representation of the swap suggestion, including property IDs and potential.
     *
     * @return A string describing the swap suggestion.
     */
    @Override
    public String toString() {
        return "Swap between Property " + property1.getObjectId() + " and Property " + property2.getObjectId() +
               " with potential " + potential;
    }

    /**
     * Exercise_6 - Generates and prints swap suggestions for properties in a specified freguesia.
     * The method also displays average property area per owner before and after the swaps.
     *
     * @param freguesia The name of the freguesia to filter properties for generating swap suggestions.
     */
    public static void Exercise_6(String freguesia) {
        // Load all properties from the CSV
        List<Property> properties = CsvToPropertyReader.Exercise_1();

        // Default to "Arco da Calheta" if no freguesia is provided
        if (freguesia == null) {
            freguesia = "Arco da Calheta";
        }

        // Filter properties based on the specified freguesia
        List<Property> filteredProperties = CsvToPropertyReader.filterPropertiesByFreguesia(properties, freguesia);

        // Generate swap suggestions for the filtered properties
        List<PropertySwapSuggestion> suggestions = PropertySwapManager.generateSwapSuggestions(filteredProperties);

        // Print the swap suggestions
        PropertySwapSuggestionPrinter.printSuggestions(suggestions);

        // Print the average area per owner before and after applying the swaps
        PropertySwapSuggestionPrinter.printAverageAreaBeforeAndAfterSwap(filteredProperties, suggestions);
    }

    /**
     * Main method to run the property swap suggestion generation for a specific freguesia.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        Exercise_6("Arco da Calheta"); // Execute the swap suggestion logic for "Arco da Calheta"
    }
}
