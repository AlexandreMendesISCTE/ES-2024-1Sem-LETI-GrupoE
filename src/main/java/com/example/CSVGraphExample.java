package com.example;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.nio.dot.DOTExporter;
import org.jgrapht.nio.Attribute;
import org.jgrapht.nio.DefaultAttribute;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class CSVGraphExample {
    public static void main(String[] args) {
        String csvFilePath = "src/main/resources/Madeira-Moodle.csv";

        try (Reader reader = new FileReader(csvFilePath);
                CSVParser csvParser = new CSVParser(reader,
                        CSVFormat.DEFAULT.withDelimiter(';').withFirstRecordAsHeader())) {

            // Get the header map
            List<String> headers = csvParser.getHeaderNames();
            System.out.println("Headers: " + headers);

            // Create a graph
            Graph<String, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);

            // Iterate through the records and create graph nodes and edges
            for (CSVRecord csvRecord : csvParser) {
                String objectId = csvRecord.get("OBJECTID");
                String parId = csvRecord.get("PAR_ID");

                // Add vertices
                graph.addVertex(objectId);
                graph.addVertex(parId);

                // Add an edge between the vertices
                graph.addEdge(objectId, parId);
            }

            // Export the graph to DOT format
            DOTExporter<String, DefaultEdge> exporter = new DOTExporter<>(v -> v);
            exporter.setVertexAttributeProvider(
                    (v) -> Collections.singletonMap("label", DefaultAttribute.createAttribute(v)));
            StringWriter writer = new StringWriter();
            exporter.exportGraph(graph, writer);
            System.out.println(writer.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
