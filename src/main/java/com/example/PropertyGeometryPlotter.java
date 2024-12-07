package com.example;

import java.awt.Color;
import java.awt.Dimension;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.chart.ui.UIUtils;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.io.WKTReader;

/**
 * Class for visualizing property geometries on XY charts.
 * It uses JFreeChart for creating and displaying charts.
 * The geometry of properties is plotted based on their WKT (Well-Known Text) representation.
 */
public class PropertyGeometryPlotter extends ApplicationFrame {

    /**
     * Constructor for the class.
     *
     * @param title Title of the chart window.
     */
    public PropertyGeometryPlotter(String title) {
        super(title);
    }

    /**
     * Configures and displays the chart with a styled plot and axes.
     *
     * @param chart The JFreeChart instance to configure and display.
     * @param title The title of the chart window.
     */
    private void configureAndDisplayChart(JFreeChart chart, String title) {
        XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.white);

        // Configure the domain (X) and range (Y) axes
        NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        domainAxis.setAutoRangeIncludesZero(false); // Avoid including zero in the range unnecessarily
        rangeAxis.setAutoRangeIncludesZero(false);
        domainAxis.setLowerMargin(0.05); // Add margin for better visualization
        domainAxis.setUpperMargin(0.05);
        rangeAxis.setLowerMargin(0.05);
        rangeAxis.setUpperMargin(0.05);

        // Create a ChartPanel to hold the chart
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(1000, 800)); // Set preferred size for the chart
        setContentPane(chartPanel); // Add the panel to the window

        // Display the chart
        pack();
        UIUtils.centerFrameOnScreen(this); // Center the frame on the screen
        setVisible(true);
    }

    /**
     * Adds a property's geometry to the dataset for plotting.
     *
     * @param dataset  The dataset to add the geometry to.
     * @param reader   The WKTReader instance for reading geometry.
     * @param property The property whose geometry is being added.
     * @throws Exception If the geometry cannot be parsed.
     */
    private void addPropertyGeometryToDataset(XYSeriesCollection dataset, WKTReader reader, Property property) throws Exception {
        Geometry geometry = reader.read(property.getGeometry());
        XYSeries series = new XYSeries("Property " + property.getObjectId(), false, true);

        // Handle MultiPolygon geometries
        if (geometry.getGeometryType().equals("MultiPolygon")) {
            for (int i = 0; i < geometry.getNumGeometries(); i++) {
                Geometry polygon = geometry.getGeometryN(i);
                for (Coordinate coordinate : polygon.getCoordinates()) {
                    series.add(coordinate.x, coordinate.y);
                }
            }
        }
        // Handle Polygon geometries
        else if (geometry.getGeometryType().equals("Polygon")) {
            for (Coordinate coordinate : geometry.getCoordinates()) {
                series.add(coordinate.x, coordinate.y);
            }
        }

        // Add the series to the dataset
        dataset.addSeries(series);
    }

    /**
     * Plots properties filtered by their Object IDs on a chart.
     *
     * @param properties List of properties.
     * @param objectId1  The first Object ID to plot.
     * @param objectId2  The second Object ID to plot.
     */
    public void plotProperties(List<Property> properties, String objectId1, String objectId2) {
        GeometryFactory geometryFactory = new GeometryFactory();
        WKTReader reader = new WKTReader(geometryFactory);
        XYSeriesCollection dataset = new XYSeriesCollection();

        try {
            // Filter and add geometries to the dataset
            for (Property property : properties) {
                if (property.getObjectId().equals(objectId1) || property.getObjectId().equals(objectId2)) {
                    addPropertyGeometryToDataset(dataset, reader, property);
                }
            }
        } catch (Exception e) {
            System.err.println("Error reading geometry: " + e.getMessage());
            e.printStackTrace();
        }

        // Create and display the chart
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Property Geometries",
                "X Coordinate",
                "Y Coordinate",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        configureAndDisplayChart(chart, "Property Geometries");
    }

    /**
     * Draws properties from a specific area (freguesia) based on two Object IDs.
     *
     * @param properties List of properties.
     * @param freguesia  The name of the area.
     * @param objectId1  First Object ID.
     * @param objectId2  Second Object ID.
     */
    public void drawProperties(List<Property> properties, String freguesia, int objectId1, int objectId2) {
        List<Property> filteredProperties = CsvToPropertyReader.filterPropertiesByFreguesia(properties, freguesia);
        plotProperties(filteredProperties, String.valueOf(objectId1), String.valueOf(objectId2));
    }

    /**
     * Draws a single property on the chart.
     *
     * @param properties List of properties.
     * @param freguesia  The name of the area.
     * @param objectId   The Object ID of the property.
     */
    public void drawProperty(List<Property> properties, String freguesia, int objectId) {
        List<Property> filteredProperties = CsvToPropertyReader.filterPropertiesByFreguesia(properties, freguesia);
        plotProperties(filteredProperties, String.valueOf(objectId), String.valueOf(objectId));
    }

    /**
     * Draws all properties on the chart.
     *
     * @param properties List of properties.
     */
    public void drawAllProperties(List<Property> properties) {
        GeometryFactory geometryFactory = new GeometryFactory();
        WKTReader reader = new WKTReader(geometryFactory);
        XYSeriesCollection dataset = new XYSeriesCollection();

        try {
            // Add all geometries to the dataset
            for (Property property : properties) {
                addPropertyGeometryToDataset(dataset, reader, property);
            }
        } catch (Exception e) {
            System.err.println("Error reading geometry: " + e.getMessage());
            e.printStackTrace();
        }

        // Create and display the chart
        JFreeChart chart = ChartFactory.createXYLineChart(
                "All Property Geometries",
                "X Coordinate",
                "Y Coordinate",
                dataset,
                PlotOrientation.VERTICAL,
                false,
                true,
                false
        );

        configureAndDisplayChart(chart, "All Property Geometries");
    }

    /**
     * Main method to test the class functionalities.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        PropertyGeometryPlotter plotter = new PropertyGeometryPlotter("Property Geometry Plot");
        plotter.drawAllProperties(CsvToPropertyReader.Exercise_1());
    }
}
