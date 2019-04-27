package game;

import javafx.scene.paint.Color;

public class WaterTile extends Tile {
    public WaterTile(double sideLength, double centerX, double centerY, int xCoord, int yCoord) {
        super(sideLength, centerX, centerY, xCoord, yCoord);
        paint(Color.LIGHTBLUE);
    }
}
