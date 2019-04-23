package visual;
import javafx.scene.shape.Polygon;

import java.util.Arrays;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Hexagon extends Polygon {
    /** Boxing polygon */
    Polygon polygon;

    /** Defines a hexagon with equal length size
     *
     * @param sideLength Length of each side
     * @param centerX x co-ordinate of center
     * @param centerY y co-ordinate of center
     */
    public Hexagon(double sideLength, double centerX, double centerY) {
        Double[] points = new Double[16];
        // First point
        double currentPointX = centerX-sideLength;
        double currentPointY = centerY-sideLength/2;
        // Direction of the current line in radians
        double direction = Math.PI*2;

        for(int pointNum=0; pointNum < 8; pointNum++) {
            points[pointNum*2] = currentPointX;
            points[pointNum*2 + 1] = currentPointY;
            currentPointX = currentPointX + cos(direction) * sideLength;
            currentPointY = currentPointY + sin(direction) *sideLength;
            direction -= Math.PI/4;
        }

        polygon = new Polygon();
        polygon.getPoints().addAll(points);
    }

    public Polygon getPolygon() {
        return polygon;
    }
}
