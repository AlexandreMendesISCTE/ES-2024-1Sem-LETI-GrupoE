package com.example;

import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

public class PropertySwapSuggestionTest {

    @Test
    public void testConstructorAndGetters() {
        Property property1 = new Property("1", "123", "456", "789", "100", "geometry", "Owner1", "Freguesia1", "Municipio1", "Ilha1");
        Property property2 = new Property("2", "124", "457", "790", "110", "geometry2", "Owner2", "Freguesia2", "Municipio2", "Ilha2");

        PropertySwapSuggestion suggestion = new PropertySwapSuggestion(property1, property2, 0.85);
        assertEquals(property1, suggestion.getProperty1());
        assertEquals(property2, suggestion.getProperty2());
        assertEquals(0.85, suggestion.getPotential(), 0.01);
    }
}
