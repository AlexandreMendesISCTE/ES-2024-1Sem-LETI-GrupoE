package com.example;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;
import org.locationtech.jts.geom.*;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import mil.nga.geopackage.*;

public class GeoProcessor {

    public static void main(String[] args) throws IOException, ParseException {
        // Caminho do arquivo CSV
        Path csvPath = Paths.get("src/main/resources/Madeira-Moodle.csv");

        // Leitura do arquivo CSV
        List<String> lines = Files.readAllLines(csvPath);

        // Iterar por cada linha e processar o MULTIPOLYGON
        for (String line : lines) {
            String multipolygonWKT = extractMultipolygonWKT(line);
            if (multipolygonWKT != null) {
                // Processar e desenhar as coordenadas
                List<Coordinate[]> polygons = parseMultipolygon(multipolygonWKT);
                drawPolygons(polygons);
            }
        }
    }

    // Extrair MULTIPOLYGON da linha do CSV usando regex
    private static String extractMultipolygonWKT(String line) {
        Pattern pattern = Pattern.compile("MULTIPOLYGON \\(\\(\\((.*)\\)\\)\\)");
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            return "MULTIPOLYGON (((" + matcher.group(1) + ")))";
        }
        return null;
    }

    // Parsear o WKT de MULTIPOLYGON para obter os polígonos
    private static List<Coordinate[]> parseMultipolygon(String wkt) throws ParseException {
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

    // Método para desenhar os polígonos extraídos
    private static void drawPolygons(List<Coordinate[]> polygons) {
        for (Coordinate[] coords : polygons) {
            for (int i = 0; i < coords.length - 1; i++) {
                Coordinate start = coords[i];
                Coordinate end = coords[i + 1];

                // Exibir ou conectar os pontos (ex: desenhar em um painel gráfico)
                System.out.println("Linha de (" + start.x + ", " + start.y + ") para (" + end.x + ", " + end.y + ")");

                // Aqui você pode usar uma biblioteca gráfica para desenhar as linhas
            }
        }
    }
}
