package visual;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeLineJoin;

import java.util.Arrays;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

/** 2D Regular Hexagon, represented by a polygon */
public abstract class RegularHexagon {
    /** Boxing polygon */
    private Polygon polygon = new Polygon();

    /** Defines a hexagon with equal length size
     *
     * @param sideLength Length of each side
     * @param centerX x co-ordinate of center
     * @param centerY y co-ordinate of center
     */
    public RegularHexagon(double sideLength, double centerX, double centerY) {
        Double[] points = new Double[12];
        // First point
        double currentPointX = centerX-sideLength;
        double currentPointY = centerY-sideLength/2;
        // Direction of the current line in radians
        double direction = Math.PI/2;

        for(int pointNum=0; pointNum < 6; pointNum++) {
            points[pointNum*2] = currentPointX;
            points[pointNum*2 + 1] = currentPointY;
            currentPointX = currentPointX + cos(direction) * sideLength;
            currentPointY = currentPointY + sin(direction) * sideLength;
            direction -= Math.PI/3;
        }
        polygon.getPoints().addAll(points);
    }

    public void paint(Paint paint) {
        polygon.setFill(paint);
    }

    public Polygon getPolygon() {
        return polygon;
    }
}
