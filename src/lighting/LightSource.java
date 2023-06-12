package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * This interface will serve all light sources
 *
 * @author ziv and aviden
 */
public interface LightSource {
    /**
     * Calculate light intensity at a enlighted point
     * @param p the point to calculate the intensity at
     * @return light intensity value
     */
    Color getIntensity(Point p);

    /**
     * calculate light direction to a point
     * @param p the point to calculate the direction to
     * @return light direction value
     */
    Vector getL(Point p);
    /**
     * calculate the distance between the light source and a point
     * @param point the point to calculate the distance to
     * @return the distance between the light source and a point
     */
    double getDistance(Point point);
}
