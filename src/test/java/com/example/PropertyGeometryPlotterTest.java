package com.example;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import org.junit.jupiter.api.Test;

public class PropertyGeometryPlotterTest {

    @Test
    public void testDrawProperty() {
        PropertyGeometryPlotter plotter = new PropertyGeometryPlotter("someString");
        List<Property> properties = new ArrayList<>();
        properties.add(new Property("1", "123", "456", "100.0", "200.0", "POINT(0 0)", "John Doe", "Arco da Calheta", "Calheta", "Madeira"));

        assertDoesNotThrow(() -> {
            plotter.drawProperty(properties, "Arco da Calheta", 1);
        });
    }

    @Test
    public void testDrawAllProperties() {
        PropertyGeometryPlotter plotter = new PropertyGeometryPlotter("someString");
        List<Property> properties = new ArrayList<>();
        properties.add(new Property("1", "123", "456", "100.0", "200.0", "POINT(0 0)", "John Doe", "Arco da Calheta", "Calheta", "Madeira"));
        properties.add(new Property("2", "789", "101", "150.0", "250.0", "POINT(1 1)", "Jane Doe", "Ponta do Sol", "Ponta do Sol", "Porto Santo"));

        assertDoesNotThrow(() -> {
            plotter.drawAllProperties(properties);
        });
    }
}