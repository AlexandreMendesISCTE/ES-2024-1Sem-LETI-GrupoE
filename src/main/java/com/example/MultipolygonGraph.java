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

    private static int n;
    private static int m;
    private static List<Property> properties;

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
        // Coordenadas do multipolígono
        double[][] coordinates1 = properties.get(n - 1).getCoordinates();

        double[][] coordinates2 = properties.get(m - 1).getCoordinates();

        // Calculando os valores mínimos e máximos das coordenadas X e Y
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

        // Definindo os eixos com base nos valores mínimos e máximos
        NumberAxis xAxis = new NumberAxis(minX - 10, maxX + 10, (maxX - minX) / 10);
        NumberAxis yAxis = new NumberAxis(minY - 10, maxY + 10, (maxY - minY) / 10);
        xAxis.setLabel("Coordenada X");
        yAxis.setLabel("Coordenada Y");

        // Criando o gráfico de dispersão
        ScatterChart<Number, Number> scatterChart = new ScatterChart<>(xAxis, yAxis);
        scatterChart.setTitle("Grafo do Multipolygon com Arestas Conectadas");

        // Adicionando pontos e conectando as arestas para o primeiro conjunto de
        // coordenadas
        addSeriesAndLines(scatterChart, coordinates1);

        // Adicionando pontos e conectando as arestas para o segundo conjunto de
        // coordenadas
        addSeriesAndLines(scatterChart, coordinates2);

        // Configurando a cena e exibindo o gráfico
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

            // Conectando os pontos
            if (i > 0) {
                double prevX = coordinates[i - 1][0];
                double prevY = coordinates[i - 1][1];
                XYChart.Series<Number, Number> lineSeries = new XYChart.Series<>();
                lineSeries.getData().add(new XYChart.Data<>(prevX, prevY));
                lineSeries.getData().add(new XYChart.Data<>(x, y));
                scatterChart.getData().add(lineSeries);
            }
        }
        // Fechando o loop
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