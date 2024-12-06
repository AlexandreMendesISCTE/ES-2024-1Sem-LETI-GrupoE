package com.example;

import java.util.List;

import com.example.utils.PropertySwapManager;
import com.example.utils.PropertySwapSuggestionPrinter;

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
     * Exercise 6 - Generate swap suggestions and print owner and area for specific properties.
     */
    public static void Exercise_6(String freguesia) {
        List<Property> properties = CsvToPropertyReader.Exercise_1();
        if (freguesia == null) {
            freguesia = "Arco da Calheta";
        }
        List<Property> filteredProperties = CsvToPropertyReader.filterPropertiesByFreguesia(properties, freguesia);

        // Generate swap suggestions for filtered properties
        List<PropertySwapSuggestion> suggestions = PropertySwapManager.generateSwapSuggestions(filteredProperties);

        // Print swap suggestions and metrics
        PropertySwapSuggestionPrinter.printSuggestions(suggestions);
        PropertySwapSuggestionPrinter.printAverageAreaBeforeAndAfterSwap(filteredProperties, suggestions);
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
