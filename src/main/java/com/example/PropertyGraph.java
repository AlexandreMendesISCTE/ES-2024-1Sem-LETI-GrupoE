package com.example;

import java.util.HashMap;
import java.util.List;

import javax.swing.JFrame;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import com.mxgraph.layout.mxOrganicLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;

/**
 * The PropertyGraph class is responsible for creating and visualizing a graph of properties.
 * It loads property data from a CSV file, creates a graph representation with properties as vertices,
 * and visualizes the relationships between properties using JGraphX.
 */
public class PropertyGraph {
    /**
     * Exercise_2 - Loads properties, creates a graph, and visualizes it.
     */
    public static void Exercise_2() {
        // Load properties from CSV file
        List<Property> properties = CsvToPropertyReader.Exercise_1();
        if (properties.size() > 120) {
            properties = properties.subList(0, 120); // Limit the number of properties to 120
        }

        // Create a graph with properties as vertices and adjacency relations as edges
        Graph<Property, DefaultEdge> propertyGraph = new SimpleGraph<>(DefaultEdge.class);

        // Add all properties as vertices in the graph
        for (Property property : properties) {
            propertyGraph.addVertex(property);
        }

        // Add edges between adjacent properties
        for (Property property1 : properties) {
            for (Property property2 : properties) {
                if (!property1.equals(property2) && property1.isAdjacentTo(property2)) {
                    propertyGraph.addEdge(property1, property2);
                }
            }
        }

        // Visualize the graph using JGraphX
        visualizeGraph(propertyGraph);
    }

    /**
     * Method to visualize the graph using JGraphX.
     * This method creates a visual representation of the property graph using JGraphX,
     * applying an organic layout and displaying the graph in a JFrame.
     *
     * @param graph The graph to be visualized, with properties as vertices and edges representing adjacency.
     */
    public static void visualizeGraph(Graph<Property, DefaultEdge> graph) {
        mxGraph jGraph = new mxGraph();
        Object parent = jGraph.getDefaultParent();

        double minX = Double.MAX_VALUE;
        double minY = Double.MAX_VALUE;
        double maxX = Double.MIN_VALUE;
        double maxY = Double.MIN_VALUE;

        jGraph.getModel().beginUpdate();
        try {
            // Create a mapping between properties and vertices
            HashMap<Property, Object> vertexMap = new HashMap<>();

            // Add vertices to the JGraphX graph and calculate min/max coordinates
            for (Property property : graph.vertexSet()) {
                double x = property.getCentroid().getX() / 10000;
                double y = property.getCentroid().getY() / 10000;
                String style = mxConstants.STYLE_SHAPE + "=" + mxConstants.SHAPE_ELLIPSE + ";" + mxConstants.STYLE_FILLCOLOR + "=#00BFFF;";
                Object vertex = jGraph.insertVertex(parent, null, property.getObjectId(), x, y, 40, 40, style);
                vertexMap.put(property, vertex);

                // Update min/max values
                if (x < minX) minX = x;
                if (y < minY) minY = y;
                if (x > maxX) maxX = x;
                if (y > maxY) maxY = y;
            }

            // Add edges to the JGraphX graph
            for (DefaultEdge edge : graph.edgeSet()) {
                Property source = graph.getEdgeSource(edge);
                Property target = graph.getEdgeTarget(edge);
                jGraph.insertEdge(parent, null, "", vertexMap.get(source), vertexMap.get(target));
            }
        } finally {
            jGraph.getModel().endUpdate();
        }

        // Layout and display the graph in a JFrame with adjusted bounds
        mxOrganicLayout layout = new mxOrganicLayout(jGraph);
        layout.execute(jGraph.getDefaultParent());

        mxGraphComponent graphComponent = new mxGraphComponent(jGraph);
        graphComponent.getGraphControl().setBounds((int) minX - 50, (int) minY - 50, (int) (maxX - minX) + 100, (int) (maxY - minY) + 100);

        JFrame frame = new JFrame("Property Graph Visualization");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(graphComponent);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Main method to execute Exercise_2.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        Exercise_2();
    }
}

