package game;

import javafx.scene.paint.Color;

public class LandTile extends Tile {
    private Empire occupier = null;
    private int foodScore, militaryScore;

    public LandTile(double sideLength, double centerX, double centerY, int xCoord, int yCoord,
                    int foodScore, int militaryScore) {
        super(sideLength, centerX, centerY, xCoord, yCoord);
        this.foodScore = foodScore;
        this.militaryScore = militaryScore;
        paint(Color.DARKSEAGREEN);
    }

    public void setOccupier(Empire occupier) {
        // Old owner is removed
        if (this.occupier != null) {
            this.occupier.removeTerritory(this);
        }
        // New owner set
        this.occupier = occupier;
        paint(occupier.getTeamColour());
    }
    public Empire getEmpire() {
        return occupier;
    }
}
