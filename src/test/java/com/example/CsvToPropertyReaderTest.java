package com.example;

import org.junit.Test;
import java.util.List;
import static org.junit.Assert.*;

public class CsvToPropertyReaderTest {

    @Test
    public void testExercise_1() {
        List<Property> properties = CsvToPropertyReader.Exercise_1();
        assertNotNull(properties);
        assertFalse(properties.isEmpty());
    }

    @Test
    public void testFilterPropertiesByFreguesia() {
        List<Property> properties = CsvToPropertyReader.Exercise_1();
        List<Property> filtered = CsvToPropertyReader.filterPropertiesByFreguesia(properties, "Arco da Calheta");

        assertNotNull(filtered);
        assertFalse(filtered.isEmpty());
        assertTrue(filtered.stream().allMatch(p -> p.getFreguesia().equalsIgnoreCase("Arco da Calheta")));
    }
}
