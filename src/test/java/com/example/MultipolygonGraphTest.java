package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MultipolygonGraphTest {

    private List<Property> properties;

    @BeforeEach
    public void setUp() {
        properties = new ArrayList<>();
        Property property1 = new Property();
        property1.setObjectId("1");
        property1.setCoordinates(new double[][] {
                { 299218.5203999998, 3623637.4791 },
                { 299218.5033999998, 3623637.4715 },
                { 299218.04000000004, 3623638.4800000004 },
                { 299232.7400000002, 3623644.6799999997 },
                { 299236.6233999999, 3623637.1974 },
                { 299236.93709999975, 3623636.7885999996 },
                { 299238.04000000004, 3623633.4800000004 },
                { 299222.63999999966, 3623627.1799999997 },
                { 299218.5203999998, 3623637.4791 } // Fechando o loop
        });

        Property property2 = new Property();
        property2.setObjectId("2");
        property2.setCoordinates(new double[][] {
                { 298724.1991999997, 3623192.6094000004 },
                { 298724.3200000003, 3623192.619999999 },
                { 298724.26999999955, 3623185.7200000007 },
                { 298723.8854, 3623185.681500001 },
                { 298723.8854, 3623185.6338 },
                { 298717.2167999996, 3623184.6405999996 },
                { 298717.2167999996, 3623184.6405999996 },
                { 298717.2167999996, 3623184.6405999996 },
                { 298717.2167999996, 3623184.6405999996 }
        });

        properties.add(property1);
        properties.add(property2);
    }

    @Test
    public void testSetProperties() {
        MultipolygonGraph.setProperties(properties);
        assertNotNull(MultipolygonGraph.properties);
        assertEquals(2, MultipolygonGraph.properties.size());
    }

    @Test
    public void testSetN() {
        MultipolygonGraph.setN(1);
        assertEquals(1, MultipolygonGraph.n);
    }

    @Test
    public void testSetM() {
        MultipolygonGraph.setM(2);
        assertEquals(2, MultipolygonGraph.m);
    }

    @Test
    public void testStart() {
        MultipolygonGraph.setProperties(properties);
        MultipolygonGraph.setN(1);
        MultipolygonGraph.setM(2);

        // This test will launch the JavaFX application
        // Note: JavaFX tests require a graphical environment to run
        // You might need to configure your CI/CD pipeline to support this
        MultipolygonGraph graph = new MultipolygonGraph();
        assertNotNull(graph);
    }
}