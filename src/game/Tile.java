package game;

import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineJoin;
import visual.RegularHexagon;

import java.util.ArrayList;

public abstract class Tile extends RegularHexagon {
    private ArrayList<Tile> neighbours = new ArrayList<Tile>();
    /** x-coord in double coordinates */
    private int xCoord;
    /** y-coord in double coordinates */
    private int yCoord;

    Tile(double sideLength, double centerX, double centerY, int xCoord, int yCoord) {
        super(sideLength, centerX, centerY);
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        super.getPolygon().setStrokeLineJoin(StrokeLineJoin.ROUND);
        super.getPolygon().setStroke(Color.BLACK);
        super.getPolygon().setStrokeWidth(0.5);
    }

    public void addNeighbour(Tile neighbour) {
        neighbours.add(neighbour);
    }

    public ArrayList<Tile> getNeighbours() { return neighbours; }

    public int getX() {
        return xCoord;
    }
    public int getY() {
        return yCoord;
    }
}
