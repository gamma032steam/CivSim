package game;

public class LandTile extends Tile {
    private Empire occupier = null;
    public boolean encapsulated = false;
    public LandTile(double sideLength, double centerX, double centerY, int xCoord, int yCoord) {
        super(sideLength, centerX, centerY, xCoord, yCoord);
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
