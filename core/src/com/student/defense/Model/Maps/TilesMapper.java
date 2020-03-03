package com.student.defense.Model.Maps;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

class TilesMapper {

    private static String mapFile = "maps/mapping.csv";
    private static HashMap<Integer, String> tileIdToTileName = null;

    private static void InitializeMap() {
        tileIdToTileName = new HashMap<>();
        String row;
        try {
            BufferedReader csvReader = new BufferedReader(new FileReader(mapFile));
            while ((row = csvReader.readLine()) != null) {
                String[] values = row.split("=");
                tileIdToTileName.put(Integer.parseInt(values[0]), values[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Tile mapping config file format error, should be <int=string> each line");
        }
    }

    static HashMap<Integer, String> GetMapper() {
        if (tileIdToTileName == null) {
            InitializeMap();
        }

        return tileIdToTileName;
    }
}
