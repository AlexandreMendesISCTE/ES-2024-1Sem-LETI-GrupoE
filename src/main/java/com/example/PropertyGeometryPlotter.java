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
 * Class for plotting property geometries on XY charts.
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
     * Plots properties filtered by ObjectIds on a chart.
     *
     * @param properties List of properties.
     * @param objectId1  First ObjectId to plot.
     * @param objectId2  Second ObjectId to plot.
     */
    public void plotProperties(List<Property> properties, String objectId1, String objectId2) {
        GeometryFactory geometryFactory = new GeometryFactory();
        WKTReader reader = new WKTReader(geometryFactory);

        XYSeriesCollection dataset = new XYSeriesCollection();

        try {
            // Process each property and filter by the provided ObjectIds
            for (Property property : properties) {
                if (property.getObjectId().equals(objectId1) || property.getObjectId().equals(objectId2)) {
                    Geometry geometry = reader.read(property.getGeometry());
                    XYSeries series = new XYSeries("Property " + property.getObjectId(), false, true);

                    // Plot MultiPolygon geometries
                    if (geometry.getGeometryType().equals("MultiPolygon")) {
                        for (int i = 0; i < geometry.getNumGeometries(); i++) {
                            Geometry polygon = geometry.getGeometryN(i);
                            Coordinate[] coordinates = polygon.getCoordinates();
                            for (Coordinate coordinate : coordinates) {
                                series.add(coordinate.x, coordinate.y);
                            }
                        }
                    }
                    // Plot Polygon geometries
                    else if (geometry.getGeometryType().equals("Polygon")) {
                        Coordinate[] coordinates = geometry.getCoordinates();
                        for (Coordinate coordinate : coordinates) {
                            series.add(coordinate.x, coordinate.y);
                        }
                    }
                    dataset.addSeries(series);
                }
            }
        } catch (Exception e) {
            System.err.println("Error reading geometry: " + e.getMessage());
            e.printStackTrace();
        }

        // Create the chart
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

        // Configure the chart
        XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.white);

        // Configure the axes
        NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        domainAxis.setAutoRangeIncludesZero(false);
        rangeAxis.setAutoRangeIncludesZero(false);
        domainAxis.setLowerMargin(0.05);
        domainAxis.setUpperMargin(0.05);
        rangeAxis.setLowerMargin(0.05);
        rangeAxis.setUpperMargin(0.05);

        // Add the chart to a panel
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(1000, 800));
        setContentPane(chartPanel);
    }

    /**
     * Draws properties from a specific area with two ObjectIds.
     *
     * @param properties List of properties.
     * @param freguesia  Name of the area (freguesia).
     * @param objectId1  First ObjectId.
     * @param objectId2  Second ObjectId.
     */
    public void drawProperties(List<Property> properties, String freguesia, int objectId1, int objectId2) {
        List<Property> filteredProperties = CsvToPropertyReader.filterPropertiesByFreguesia(properties, freguesia);
        String objId1 = String.valueOf(objectId1);
        String objId2 = String.valueOf(objectId2);
        plotProperties(filteredProperties, objId1, objId2);
        pack();
        UIUtils.centerFrameOnScreen(this);
        setVisible(true);
    }

    /**
     * Draws a single property on the chart.
     *
     * @param properties List of properties.
     * @param freguesia  Name of the area (freguesia).
     * @param objectId   ObjectId of the property.
     */
    public void drawProperty(List<Property> properties, String freguesia, int objectId) {
        List<Property> filteredProperties = CsvToPropertyReader.filterPropertiesByFreguesia(properties, freguesia);
        String objId = String.valueOf(objectId);
        plotProperties(filteredProperties, objId, objId);
        pack();
        UIUtils.centerFrameOnScreen(this);
        setVisible(true);
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
            // Process all properties
            for (Property property : properties) {
                Geometry geometry = reader.read(property.getGeometry());
                XYSeries series = new XYSeries("Property " + property.getObjectId(), false, true);

                // Plot MultiPolygon
                if (geometry.getGeometryType().equals("MultiPolygon")) {
                    for (int i = 0; i < geometry.getNumGeometries(); i++) {
                        Geometry polygon = geometry.getGeometryN(i);
                        Coordinate[] coordinates = polygon.getCoordinates();
                        for (Coordinate coordinate : coordinates) {
                            series.add(coordinate.x, coordinate.y);
                        }
                    }
                }
                // Plot Polygon
                else if (geometry.getGeometryType().equals("Polygon")) {
                    Coordinate[] coordinates = geometry.getCoordinates();
                    for (Coordinate coordinate : coordinates) {
                        series.add(coordinate.x, coordinate.y);
                    }
                }
                dataset.addSeries(series);
            }
        } catch (Exception e) {
            System.err.println("Error reading geometry: " + e.getMessage());
            e.printStackTrace();
        }

        // Create the chart
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

        // Configure the chart
        XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.white);

        // Configure the axes
        NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        domainAxis.setAutoRangeIncludesZero(false);
        rangeAxis.setAutoRangeIncludesZero(false);
        domainAxis.setLowerMargin(0.05);
        domainAxis.setUpperMargin(0.05);
        rangeAxis.setLowerMargin(0.05);
        rangeAxis.setUpperMargin(0.05);

        // Add the chart to a panel
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(1000, 800));
        setContentPane(chartPanel);
        pack();
        UIUtils.centerFrameOnScreen(this);
        setVisible(true);
    }

    /**
     * Main method to test the class.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        PropertyGeometryPlotter plotter = new PropertyGeometryPlotter("Property Geometry Plot");
        //plotter.drawProperty(CsvToPropertyReader.Exercise_1(), "Arco da Calheta", 1);
        //plotter.drawProperties(CsvToPropertyReader.Exercise_1(), "Arco da Calheta", 1, 2);
        //plotter.drawAllProperties(CsvToPropertyReader.filterPropertiesByFreguesia(CsvToPropertyReader.Exercise_1(), "Arco da Calheta"));
        plotter.drawAllProperties(CsvToPropertyReader.Exercise_1());
    }
}
