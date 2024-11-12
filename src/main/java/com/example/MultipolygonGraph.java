package com.example;

import java.util.List;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

public class MultipolygonGraph extends Application {

    public static int n;
    public static int m;
    public static List<Property> properties;

    public static void setN(int n) {
        MultipolygonGraph.n = n;
    }

    public static void setM(int m) {
        MultipolygonGraph.m = m;
    }

    public static void setProperties(List<Property> properties) {
        MultipolygonGraph.properties = properties;
    }

    @Override
    public void start(Stage stage) {
        double[][] coordinates1 = properties.get(n - 1).getCoordinates();
        double[][] coordinates2 = properties.get(m - 1).getCoordinates();

        // Calculate the minimum and maximum values of the X and Y coordinates
        double minX = Double.MAX_VALUE;
        double maxX = Double.MIN_VALUE;
        double minY = Double.MAX_VALUE;
        double maxY = Double.MIN_VALUE;

        for (double[] coordinate : coordinates1) {
            if (coordinate[0] < minX)
                minX = coordinate[0];
            if (coordinate[0] > maxX)
                maxX = coordinate[0];
            if (coordinate[1] < minY)
                minY = coordinate[1];
            if (coordinate[1] > maxY)
                maxY = coordinate[1];
        }

        for (double[] coordinate : coordinates2) {
            if (coordinate[0] < minX)
                minX = coordinate[0];
            if (coordinate[0] > maxX)
                maxX = coordinate[0];
            if (coordinate[1] < minY)
                minY = coordinate[1];
            if (coordinate[1] > maxY)
                maxY = coordinate[1];
        }

        // Define the axes based on the minimum and maximum values
        NumberAxis xAxis = new NumberAxis(minX - 10, maxX + 10, (maxX - minX) / 10);
        NumberAxis yAxis = new NumberAxis(minY - 10, maxY + 10, (maxY - minY) / 10);
        xAxis.setLabel("Coordenada X");
        yAxis.setLabel("Coordenada Y");

        // Create the scatter chart(grapf) and set its title
        ScatterChart<Number, Number> scatterChart = new ScatterChart<>(xAxis, yAxis);
        scatterChart.setTitle("Grafo do Multipolygon com Arestas Conectadas");

        // Add points and connect the edges for the first set of coordinates
        addSeriesAndLines(scatterChart, coordinates1);

        // Add points and connect the edges for the second set of coordinates
        addSeriesAndLines(scatterChart, coordinates2);

        // Set up the scene and display the chart
        Pane pane = new Pane(scatterChart);
        Scene scene = new Scene(pane, 800, 800);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Multipolygon Graph");
        stage.show();
    }

    private void addSeriesAndLines(ScatterChart<Number, Number> scatterChart, double[][] coordinates) {
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        for (int i = 0; i < coordinates.length; i++) {
            double x = coordinates[i][0];
            double y = coordinates[i][1];
            series.getData().add(new XYChart.Data<>(x, y));
            if (i > 0) {
                double prevX = coordinates[i - 1][0];
                double prevY = coordinates[i - 1][1];
                XYChart.Series<Number, Number> lineSeries = new XYChart.Series<>();
                lineSeries.getData().add(new XYChart.Data<>(prevX, prevY));
                lineSeries.getData().add(new XYChart.Data<>(x, y));
                scatterChart.getData().add(lineSeries);
            }
        }
        double firstX = coordinates[0][0];
        double firstY = coordinates[0][1];
        double lastX = coordinates[coordinates.length - 1][0];
        double lastY = coordinates[coordinates.length - 1][1];
        XYChart.Series<Number, Number> lineSeries = new XYChart.Series<>();
        lineSeries.getData().add(new XYChart.Data<>(lastX, lastY));
        lineSeries.getData().add(new XYChart.Data<>(firstX, firstY));
        scatterChart.getData().add(lineSeries);
    }

    public static void main(String[] args) {
        launch(args);
    }
}