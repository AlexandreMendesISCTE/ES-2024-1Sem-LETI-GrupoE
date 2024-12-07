package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class PropertyTest {

    @Test
    public void testPropertyConstructorAndGetters() {
        Property property = new Property("1", "123", "456", "100.0", "200.0", "POINT(0 0)", "John Doe", "Arco da Calheta", "Calheta", "Madeira");

        assertEquals("1", property.getObjectId(), "Object ID should be '1'");
        assertEquals("123", property.getParId(), "Parcel ID should be '123'");
        assertEquals("456", property.getParNum(), "Parcel number should be '456'");
        assertEquals("100.0", property.getShapeLength(), "Shape length should be '100.0'");
        assertEquals("200.0", property.getShapeArea(), "Shape area should be '200.0'");
        assertEquals("POINT(0 0)", property.getGeometry(), "Geometry should be 'POINT(0 0)'");
        assertEquals("John Doe", property.getOwner(), "Owner should be 'John Doe'");
        assertEquals("Arco da Calheta", property.getFreguesia(), "Freguesia should be 'Arco da Calheta'");
        assertEquals("Calheta", property.getMunicipio(), "Municipio should be 'Calheta'");
        assertEquals("Madeira", property.getIlha(), "Ilha should be 'Madeira'");
    }

    @Test
    public void testPropertySetters() {
        Property property = new Property("1", "123", "456", "100.0", "200.0", "POINT(0 0)", "John Doe", "Arco da Calheta", "Calheta", "Madeira");

        property.setObjectId("2");
        property.setParId("789");
        property.setParNum("101");
        property.setShapeLength("150.0");
        property.setShapeArea("250.0");
        property.setGeometry("POINT(1 1)");
        property.setOwner("Jane Doe");
        property.setFreguesia("Ponta do Sol");
        property.setMunicipio("Ponta do Sol");
        property.setIlha("Porto Santo");

        assertEquals("2", property.getObjectId(), "Object ID should be '2'");
        assertEquals("789", property.getParId(), "Parcel ID should be '789'");
        assertEquals("101", property.getParNum(), "Parcel number should be '101'");
        assertEquals("150.0", property.getShapeLength(), "Shape length should be '150.0'");
        assertEquals("250.0", property.getShapeArea(), "Shape area should be '250.0'");
        assertEquals("POINT(1 1)", property.getGeometry(), "Geometry should be 'POINT(1 1)'");
        assertEquals("Jane Doe", property.getOwner(), "Owner should be 'Jane Doe'");
        assertEquals("Ponta do Sol", property.getFreguesia(), "Freguesia should be 'Ponta do Sol'");
        assertEquals("Ponta do Sol", property.getMunicipio(), "Municipio should be 'Ponta do Sol'");
        assertEquals("Porto Santo", property.getIlha(), "Ilha should be 'Porto Santo'");
    }
}