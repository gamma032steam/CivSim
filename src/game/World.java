package game;

import app.Main;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import visual.OpenSimplexNoise;
import visual.SimplexNoise;

import java.util.ArrayList;
import java.util.Random;

public class World {
    public static final int HEXAGON_SIDE_LENGTH = 5;
    public static final double HEXAGON_WIDTH = Math.sqrt(3)/2*HEXAGON_SIDE_LENGTH*2;
    public static final double HEXAGON_HEIGHT = HEXAGON_SIDE_LENGTH + Math.sin(Math.PI/6)*HEXAGON_SIDE_LENGTH;
    public static final int TILES_X = (int)Math.floor(Main.GAME_WIDTH / (HEXAGON_WIDTH));
    public static final int TILES_Y = (int)Math.floor(Main.GAME_HEIGHT / (HEXAGON_HEIGHT));
    /** Tiles in a grid for calculation */
    private static final Tile tileGrid[][] = new Tile[TILES_Y+1][TILES_X*2+2];
    /** Tiles in an array for handling as a set */
    private static ArrayList<Tile> tileSet = new ArrayList<Tile>();
    /** All the empires in the game */
    private static ArrayList<Empire> empires = new ArrayList<>();

    public World() {
        initializeEmpires();
    }

    public void initializeEmpires() {
        Empire Britain = new Empire(Color.RED, (LandTile)tileGrid[2][8]);
        empires.add(Britain);
        /*Empire Australia = new Empire(Color.GREEN, (LandTile)tileGrid[2][64]);
        empires.add(Australia);*/
    }

    public void updateGame() {
        for(Empire empire: empires) {
            empire.expandLinearly();
        }
    }

    /** Fractal Brownian Motion
     * https://cmaher.github.io/posts/working-with-simplex-noise/
     * */
    private static double sumOctave(int num_iterations, double x, double y, double persistence, double scale, double low, double high) {
        int maxAmp = 0;
        int amp = 1;
        double freq = scale;
        double noise = 0;
        Random rand = new Random(System.nanoTime());
        OpenSimplexNoise simplex = new OpenSimplexNoise(rand.nextLong());
        //add successively smaller, higher - frequency terms
        for (int i = 0; i < num_iterations; ++i) {
            noise += SimplexNoise.noise(x * freq, y * freq) * amp;
            maxAmp += amp;
            amp *= persistence;
            freq *= 2;
        }
        // take the average value of the iterations
        noise /= maxAmp;

        // normalize the result
        noise = noise * (high - low) / 2 + (high + low) / 2;

        return noise;
    }
    public static Group generateMap() {
        // Create the map
        // Init random scale
        Random rand = new Random();
        double SCALE = 0.01;
        int offset = rand.nextInt(100000);
        // x-axis (columns)
        //System.out.format("%d %d\n", TILES_X*2+2, TILES_Y+1);
        for(int x = 0; x <= TILES_X; x++) {
            // y-axis (rows)
            for(int y = 0; y <= TILES_Y; y++) {
                // Find center
                double centerX;
                if (y%2 == 1) {
                    centerX = (x * HEXAGON_WIDTH) + HEXAGON_WIDTH;
                } else {
                    centerX = (x * HEXAGON_WIDTH) + (HEXAGON_WIDTH / 2);
                }
                double centerY = y * HEXAGON_HEIGHT + HEXAGON_HEIGHT / 2;

                // Convert to doubled coordinates
                int doubleYCoordinate = y;
                int doubleXCoordinate;
                if (y % 2 == 1) {
                    doubleXCoordinate = (2*x)+1;
                } else {
                    doubleXCoordinate = 2*x;
                }
                //System.out.format("%d %d\n", doubleXCoordinate, doubleYCoordinate);
                // Make some noise
                // Src: https://www.redblobgames.com/maps/terrain-from-noise/#demo
                // Noise generator

                double elevation = sumOctave(16, x+offset, y+offset, 0.8, SCALE, 0, 100);
                //double elevation = (new Random()).nextInt(100);
                //+  0.5 * noise.eval(x*FEATURE_SIZE*2, y*FEATURE_SIZE*2)
                //+ 0.25 * noise.eval(x*FEATURE_SIZE*4, y*FEATURE_SIZE*4);

                //System.out.println(elevation);
                //System.out.println(elevation);
                Tile newTile;
                if ((elevation < 55) && !(doubleYCoordinate ==2 && doubleXCoordinate == 8)) {
                    newTile = new WaterTile(HEXAGON_SIDE_LENGTH, centerX, centerY, doubleXCoordinate, doubleYCoordinate);
                } else {
                    newTile = new LandTile(HEXAGON_SIDE_LENGTH, centerX, centerY, doubleXCoordinate, doubleYCoordinate,
                                   0, 0);
                }
                tileGrid[doubleYCoordinate][doubleXCoordinate] = newTile;
                tileSet.add(newTile);
            }
        }

        // Set up neighbours
        // Source: https://www.redblobgames.com/grids/hexagons/
        int[][] directions = new int[6][2];
        directions[0][0] = 2; directions[0][1] = 0;
        directions[1][0] = 1; directions[1][1] = -1;
        directions[2][0] = -1; directions[2][1] = -1;
        directions[3][0] = -2; directions[3][1] = 0;
        directions[4][0] = -1; directions[4][1] = 1;
        directions[5][0] = 1; directions[5][1] = 1;
        for(Tile tile: tileSet) {
            for(int[] direction: directions) {
                int newX = tile.getX() + direction[0];
                int newY = tile.getY() + direction[1];
                if(newX >= 0 && newX <= TILES_X*2+1 && newY >= 0 && newY <= TILES_Y) {
                    tile.addNeighbour(tileGrid[newY][newX]);
                }
            }
        }

        // Add to the group
        Group tiles = new Group();
        for(Tile tile: tileSet) {
            tiles.getChildren().add(tile.getPolygon());
        }

        return tiles;
    }
}
