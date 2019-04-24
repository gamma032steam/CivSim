package game;

import javafx.scene.paint.Paint;

import java.util.ArrayList;

/** A 'team' represented by a colour that can occupy tiles and expand */
public class Empire {
    /** Colour of the empire's tiles */
    Paint teamColour;
    LandTile capital;
    ArrayList<LandTile> territory = new ArrayList<LandTile>();

    public Empire(Paint teamColour, LandTile capital) {
        this.teamColour = teamColour;
        this.capital = capital;
        territory.add(capital);
        capital.setOccupier(this);
    }

    /** Expands the entire empire out 1 tile (for testing) */
    public void expandExpotentially() {
        ArrayList<LandTile> toCapture = new ArrayList<LandTile>();
        for(LandTile tile: territory) {
            for (Tile adjacent : tile.getNeighbours()) {
                LandTile newTile = (LandTile) adjacent;
                if (newTile.getEmpire() != this) {
                    toCapture.add(newTile);
                }
            }
        }

        for(LandTile tile: toCapture) {
            tile.setOccupier(this);
            territory.add(tile);
        }
    }

    public void expandLinearly() {
        for(LandTile tile: territory) {
            for (Tile adjacent: tile.getNeighbours()) {
                LandTile newTile = (LandTile) adjacent;
                if (newTile.getEmpire() == null) {
                    newTile.setOccupier(this);
                    territory.add(newTile);
                    return;
                }
            }
        }
    }

    public Paint getTeamColour() {
        return teamColour;
    }

    public void removeTerritory(LandTile tile) {
        territory.remove(tile);
    }
}
