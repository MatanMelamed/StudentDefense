package com.student.defense.Model.Maps;


import com.badlogic.gdx.graphics.Texture;
import com.student.defense.View.Textures;
import com.student.defense.View.TexturesManager;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class GameMap {
    private String title;
    private Tile[][] tiles;
    private Waypoint waypoints;
    private int width, height;

    public static int TileResolution = 64; // 64x64 pixels
    public static int HalfTile = 32;

    public GameMap(String title, String mapConfigPath) {
        this.title = title;
        LoadTilesFromFile(mapConfigPath);
    }

    private void LoadTilesFromFile(String filePath) {

        String row;

        try {
            BufferedReader csvReader = new BufferedReader(new FileReader(filePath));

            // First row indicates dimensions
            row = csvReader.readLine();
            String[] dimensions = row.split(",");
            int mapHeight = Integer.parseInt(dimensions[0]);
            int mapWidth = Integer.parseInt(dimensions[1]);
            tiles = new Tile[mapHeight][mapWidth];

            // Second row indicates waypoints
            row = csvReader.readLine();
            String[] strPoints = row.split(",");
            Waypoint old = null, current;
            for (int i = 0; i < strPoints.length; ++i) {
                String[] strPoint = strPoints[i].split(" ");
                current = new Waypoint(Integer.parseInt(strPoint[1]), Integer.parseInt(strPoint[0]), TileResolution);

                if (i != 0) { old.SetNext(current); } else { waypoints = current; }

                old = current;
            }


            // Rest Are Tiles based mapping in mapping file, use tile mapper for this.
            HashMap<Integer, String> tilesMapper = TilesMapper.GetMapper();
            int rowIndex = 0;
            Texture tileTexture;

            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",");

                for (int colIndex = 0; colIndex < mapWidth; ++colIndex) {
                    String fileName = tilesMapper.get(Integer.parseInt(data[colIndex]));
                    boolean isPath = fileName.equals(Textures.TILES_PATH.GetFileName());
                    tileTexture = TexturesManager.GetTexture(fileName);
                    tiles[rowIndex][colIndex] = new Tile(colIndex * TileResolution,
                            rowIndex * TileResolution, tileTexture, isPath);
                }

                ++rowIndex;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("GameMap config file format error");
        }

        width = tiles[0].length * GameMap.TileResolution;
        height = tiles.length * GameMap.TileResolution;
    }

    // getters and setters
    public Tile[][] GetTiles() {
        return tiles;
    }

    public int GetWidth() {
        return width;
    }

    public int GetHeight() {
        return height;
    }


    public Tile GetTileByScreen(int x, int y) {
        return tiles[x / TileResolution][y / TileResolution];
    }

    public Waypoint GetWaypoints() {
        return waypoints;
    }

    public String GetTitle() {
        return title;
    }
}
