package com.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.locationtech.jts.geom.*;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;

public class GeoProcessorApp extends Application {

    private List<Coordinate[]> polygons;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException, ParseException {
        // Ler o arquivo CSV e processar os polígonos
        polygons = readAndProcessCSV("Madeira-moodle.csv");

        // Configurar a interface JavaFX
        Pane root = new Pane();
        Canvas canvas = new Canvas(800, 600); // Ajuste o tamanho do canvas conforme necessário
        root.getChildren().add(canvas);

        drawPolygons(canvas.getGraphicsContext2D());

        primaryStage.setTitle("Representação de Polígonos");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    // Ler o CSV e processar os polígonos
    private List<Coordinate[]> readAndProcessCSV(String filePath) throws IOException, ParseException {
        Path csvPath = Paths.get(filePath);
        List<String> lines = Files.readAllLines(csvPath);
        List<Coordinate[]> polygons = new ArrayList<>();

        for (String line : lines) {
            String multipolygonWKT = extractMultipolygonWKT(line);
            if (multipolygonWKT != null) {
                polygons.addAll(parseMultipolygon(multipolygonWKT));
            }
        }

        return polygons;
    }

    // Extrair MULTIPOLYGON da linha do CSV usando regex
    private String extractMultipolygonWKT(String line) {
        Pattern pattern = Pattern.compile("MULTIPOLYGON \\(\\(\\((.*)\\)\\)\\)");
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            return "MULTIPOLYGON (((" + matcher.group(1) + ")))";
        }
        return null;
    }

    // Parsear o WKT de MULTIPOLYGON para obter os polígonos
    private List<Coordinate[]> parseMultipolygon(String wkt) throws ParseException {
        List<Coordinate[]> polygons = new ArrayList<>();
        WKTReader reader = new WKTReader();
        Geometry geometry = reader.read(wkt);

        if (geometry instanceof MultiPolygon) {
            MultiPolygon multiPolygon = (MultiPolygon) geometry;
            for (int i = 0; i < multiPolygon.getNumGeometries(); i++) {
                Polygon polygon = (Polygon) multiPolygon.getGeometryN(i);
                polygons.add(polygon.getCoordinates());
            }
        }
        return polygons;
    }

    // Desenhar os polígonos na interface JavaFX
    private void drawPolygons(GraphicsContext gc) {
        gc.setLineWidth(2.0);

        for (Coordinate[] coords : polygons) {
            for (int i = 0; i < coords.length - 1; i++) {
                Coordinate start = coords[i];
                Coordinate end = coords[i + 1];

                // Desenhar linha entre pontos consecutivos
                gc.strokeLine(start.x, start.y, end.x, end.y);

                // Desenhar ponto inicial
                gc.fillOval(start.x - 2.5, start.y - 2.5, 5, 5);
            }
        }
    }
}
