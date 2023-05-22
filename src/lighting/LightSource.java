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
     * Getter for light intensity
     *
     * @return light intensity value
     */
    Color getIntensity(Point p);

    /**
     * calcaulate for light direction
     *
     * @return light direction value
     */
    Vector getL(Point p);
}
