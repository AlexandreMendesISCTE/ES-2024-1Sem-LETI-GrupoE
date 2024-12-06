package com.example;

import java.util.HashMap;
import java.util.List;

import javax.swing.JFrame;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import com.example.utils.PropertyAdjacencyUtils;
import com.example.utils.PropertyGeometryUtils;
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
     *
     * @param targetObjectId The object ID to be centered in the visualization, if present.
     */
    public static void Exercise_2(String targetObjectId) {
        // Load properties from CSV file
        /* 
        List<Property> properties = CsvToPropertyReader.Exercise_1();
        if (properties.size() > 120) {
            properties = properties.subList(0, 120); // Limit the number of properties to 120
        }
        */
        List<Property> properties2 = CsvToPropertyReader.Exercise_1();
        String defaultFreguesia = "Arco da Calheta";
        List<Property> properties = CsvToPropertyReader.filterPropertiesByFreguesia(properties2, defaultFreguesia);

        // Create a graph with properties as vertices and adjacency relations as edges
        Graph<Property, DefaultEdge> propertyGraph = new SimpleGraph<>(DefaultEdge.class);

        // Add all properties as vertices in the graph
        for (Property property : properties) {
            propertyGraph.addVertex(property);
        }

        // Add edges between adjacent properties
        for (Property property1 : properties) {
            for (Property property2 : properties) {
                if (!property1.equals(property2) && PropertyAdjacencyUtils.areAdjacent(property1, property2)) {
                    propertyGraph.addEdge(property1, property2);
                }
            }
        }

        // Visualize the graph using JGraphX
        visualizeGraph(propertyGraph, targetObjectId);
    }

    /**
     * Exercise_5 - Loads properties, creates a graph of owners, and visualizes it.
     * This method represents the relationship between owners as a graph, where nodes represent owners
     * and edges represent adjacency relations between owners who have contiguous properties.
     */
    public static void Exercise_5() {
        // Load properties from CSV file
        List<Property> properties = CsvToPropertyReader.Exercise_1();
        if (properties.size() > 120) {
            properties = properties.subList(0, 120); // Limit the number of properties to 120
        }

        // Create a graph with owners as vertices and adjacency relations as edges
        Graph<String, DefaultEdge> ownerGraph = new SimpleGraph<>(DefaultEdge.class);

        // Iterate over properties to add owners as nodes and establish edges based on adjacency
        for (int i = 0; i < properties.size(); i++) {
            Property property1 = properties.get(i);
            ownerGraph.addVertex(property1.getOwner());

            for (int j = i + 1; j < properties.size(); j++) {
                Property property2 = properties.get(j);
                ownerGraph.addVertex(property2.getOwner());

                // Check if the properties are adjacent
                if (PropertyAdjacencyUtils.areAdjacent(property1, property2)) {
                    // If the owners are different, add an edge between them
                    if (!property1.getOwner().equalsIgnoreCase(property2.getOwner())) {
                        ownerGraph.addEdge(property1.getOwner(), property2.getOwner());
                    }
                }
            }
        }

        // Visualize the owner graph using JGraphX
        visualizeOwnerGraph(ownerGraph);
    }

    /**
     * Method to visualize the graph using JGraphX.
     * This method creates a visual representation of the property graph using JGraphX,
     * applying an organic layout and displaying the graph in a JFrame.
     *
     * @param graph The graph to be visualized, with properties as vertices and edges representing adjacency.
     * @param targetObjectId The object ID to be centered in the visualization, if present.
     */
    public static void visualizeGraph(Graph<Property, DefaultEdge> graph, String targetObjectId) {
        mxGraph jGraph = new mxGraph();
        Object parent = jGraph.getDefaultParent();
    
        double minX = Double.MAX_VALUE;
        double minY = Double.MAX_VALUE;
        double maxX = Double.MIN_VALUE;
        double maxY = Double.MIN_VALUE;
    
        Object targetVertex = null;
    
        jGraph.getModel().beginUpdate();
        try {
            // Create a mapping between properties and vertices
            HashMap<Property, Object> vertexMap = new HashMap<>();
    
            // Add vertices to the JGraphX graph and calculate min/max coordinates
            for (Property property : graph.vertexSet()) {
                double x = PropertyGeometryUtils.getCentroid(property).getX() / 10000;
                double y = PropertyGeometryUtils.getCentroid(property).getY() / 10000;
                String style = mxConstants.STYLE_SHAPE + "=" + mxConstants.SHAPE_ELLIPSE + ";" + mxConstants.STYLE_FILLCOLOR + "=#00BFFF;";
                
                // Highlight the vertex with the specified target object ID
                if (property.getObjectId().equals(targetObjectId)) {
                    style += mxConstants.STYLE_FILLCOLOR + "=#FF0000;"; // Red color to highlight
                }
    
                Object vertex = jGraph.insertVertex(parent, null, property.getObjectId(), x, y, 40, 40, style);
                vertexMap.put(property, vertex);
    
                // Update min/max values
                if (x < minX) minX = x;
                if (y < minY) minY = y;
                if (x > maxX) maxX = x;
                if (y > maxY) maxY = y;
    
                // Store the vertex to be centered if it's the target
                if (property.getObjectId().equals(targetObjectId)) {
                    targetVertex = vertex;
                }
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
    
        // If target vertex exists, focus on it
        if (targetVertex != null) {
            jGraph.setSelectionCell(targetVertex);
        }
    
        mxGraphComponent graphComponent = new mxGraphComponent(jGraph);
        graphComponent.getGraphControl().setBounds((int) minX - 50, (int) minY - 50, (int) (maxX - minX) + 100, (int) (maxY - minY) + 100);
        if (targetVertex != null) {
            graphComponent.scrollCellToVisible(targetVertex, true);
        }
    
        JFrame frame = new JFrame("Property Graph Visualization");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(graphComponent);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Method to visualize the owner graph using JGraphX.
     * This method creates a visual representation of the owner graph using JGraphX,
     * applying an organic layout and displaying the graph in a JFrame.
     *
     * @param graph The graph representing the relationship between owners.
     */
    public static void visualizeOwnerGraph(Graph<String, DefaultEdge> graph) {
        mxGraph jGraph = new mxGraph();
        Object parent = jGraph.getDefaultParent();

        jGraph.getModel().beginUpdate();
        try {
            // Create a mapping between owners and vertices
            HashMap<String, Object> vertexMap = new HashMap<>();

            // Add vertices to the JGraphX graph
            for (String owner : graph.vertexSet()) {
                String style = mxConstants.STYLE_SHAPE + "=" + mxConstants.SHAPE_ELLIPSE + ";" + mxConstants.STYLE_FILLCOLOR + "=#00BFFF;";
                Object vertex = jGraph.insertVertex(parent, null, owner, Math.random() * 400, Math.random() * 400, 40, 40, style);
                vertexMap.put(owner, vertex);
            }

            // Add edges to the JGraphX graph
            for (DefaultEdge edge : graph.edgeSet()) {
                String source = graph.getEdgeSource(edge);
                String target = graph.getEdgeTarget(edge);
                jGraph.insertEdge(parent, null, "", vertexMap.get(source), vertexMap.get(target));
            }
        } finally {
            jGraph.getModel().endUpdate();
        }

        // Layout and display the graph in a JFrame
        mxOrganicLayout layout = new mxOrganicLayout(jGraph);
        layout.execute(jGraph.getDefaultParent());

        mxGraphComponent graphComponent = new mxGraphComponent(jGraph);
        JFrame frame = new JFrame("Owner Graph Visualization");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(graphComponent);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Main method to execute Exercise_2 and Exercise_5.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        Exercise_2("698");
        //Exercise_5();
    }
}
