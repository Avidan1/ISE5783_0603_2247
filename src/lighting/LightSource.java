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
    public Color getIntensity(Point p);
    /**
     * Getter for light direction
     *
     * @return light direction value
     */
    public Vector getL(Point p);
}
