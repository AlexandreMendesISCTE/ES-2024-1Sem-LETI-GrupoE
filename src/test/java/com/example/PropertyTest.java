package com.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class PropertyTest {

    @Test
    public void testPropertyConstructorAndGetters() {
        Property property = new Property("1", "123", "456", "789", "100", "geometry", "Owner1", "Freguesia1", "Municipio1", "Ilha1");

        assertEquals("1", property.getObjectId());
        assertEquals("123", property.getParId());
        assertEquals("456", property.getParNum());
        assertEquals("789", property.getShapeLength());
        assertEquals("100", property.getShapeArea());
        assertEquals("geometry", property.getGeometry());
        assertEquals("Owner1", property.getOwner());
        assertEquals("Freguesia1", property.getFreguesia());
        assertEquals("Municipio1", property.getMunicipio());
        assertEquals("Ilha1", property.getIlha());
    }

    @Test
    public void testSetOwner() {
        Property property = new Property("1", "123", "456", "789", "100", "geometry", "Owner1", "Freguesia1", "Municipio1", "Ilha1");
        property.setOwner("NewOwner");
        assertEquals("NewOwner", property.getOwner());
    }

    @Test
    public void testIsAdjacentTo() {
        Property property1 = new Property("1", "123", "456", "789", "100", "POINT (1 1)", "Owner1", "Freguesia1", "Municipio1", "Ilha1");
        Property property2 = new Property("2", "124", "457", "790", "101", "POINT (1 1)", "Owner2", "Freguesia2", "Municipio2", "Ilha2");

        assertTrue(property1.isAdjacentTo(property2));
    }
}
