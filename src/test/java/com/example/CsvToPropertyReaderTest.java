package com.example;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

public class CsvToPropertyReaderTest {

    @Test
    public void testReadPropertiesFromCsv() {
        List<Property> properties = CsvToPropertyReader.readPropertiesFromCsv();
        assertNotNull(properties, "The properties list should not be null");
        assertFalse(properties.isEmpty(), "The properties list should not be empty");
    }

    @Test
    public void testFilterPropertiesByFreguesia() {
        List<Property> properties = CsvToPropertyReader.readPropertiesFromCsv();
        List<Property> filteredProperties = CsvToPropertyReader.filterPropertiesByFreguesia(properties, "Arco da Calheta");
        assertNotNull(filteredProperties, "The filtered properties list should not be null");
        assertFalse(filteredProperties.isEmpty(), "The filtered properties list should not be empty");
        for (Property property : filteredProperties) {
            assertEquals("Arco da Calheta", property.getFreguesia(), "The property should be in the specified freguesia");
        }
    }

    @Test
    public void testFilterPropertiesByMunicipio() {
        List<Property> properties = CsvToPropertyReader.readPropertiesFromCsv();
        List<Property> filteredProperties = CsvToPropertyReader.filterPropertiesByMunicipio(properties, "Calheta");
        assertNotNull(filteredProperties, "The filtered properties list should not be null");
        assertFalse(filteredProperties.isEmpty(), "The filtered properties list should not be empty");
        for (Property property : filteredProperties) {
            assertEquals("Calheta", property.getMunicipio(), "The property should be in the specified municipio");
        }
    }

    @Test
    public void testFilterPropertiesByIlha() {
        List<Property> properties = CsvToPropertyReader.readPropertiesFromCsv();
        List<Property> filteredProperties = CsvToPropertyReader.filterPropertiesByIlha(properties, "Ilha da Madeira (Madeira)");
        assertNotNull(filteredProperties, "The filtered properties list should not be null");
        assertFalse(filteredProperties.isEmpty(), "The filtered properties list should not be empty");
        for (Property property : filteredProperties) {
            assertEquals("Ilha da Madeira (Madeira)", property.getIlha(), "The property should be in the specified ilha");
        }
    }
}
