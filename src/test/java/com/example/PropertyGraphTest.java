package com.example;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PropertyGraphTest {

    @Test
    public void testVisualizeGraph() {
        // Create a sample graph
        Graph<Property, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);
        
        Property property1 = new Property("1", "123", "456", "100.0", "200.0", "POINT(0 0)", "John Doe", "Arco da Calheta", "Calheta", "Madeira");
        Property property2 = new Property("2", "789", "101", "150.0", "250.0", "POINT(1 1)", "Jane Doe", "Ponta do Sol", "Ponta do Sol", "Porto Santo");
        
        graph.addVertex(property1);
        graph.addVertex(property2);
        graph.addEdge(property1, property2);

        // Test if the visualizeGraph method runs without throwing any exceptions
        assertDoesNotThrow(() -> {
            PropertyGraph.visualizeGraph(graph, "1");
        });
    }
}