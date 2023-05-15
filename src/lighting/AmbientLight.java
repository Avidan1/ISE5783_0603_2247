package lighting;

import primitives.Double3;
import primitives.Color;


/**
 * Class AmbientLight is the basic class representing a light source with no direction
 * and no location, but with a color and intensity.
 *
 * @auther Ziv Farjun and Avidan Maatuk
 */

public class AmbientLight {
    /**
     * The intensity of the ambient light
     */
    private Color intensity;
    /**
     * Constant AmbientLight with no intensity
     */
    public static AmbientLight NONE = new AmbientLight(Color.BLACK, Double3.ZERO);

    /**
     * Constructor to initialize AmbientLight based object with its color and intensity
     *
     * @param ia color of the light
     * @param ka intensity of the light
     */
    public AmbientLight(Color ia, Double3 ka) {
        intensity = ia.scale(ka);
    }

    /**
     * Constructor to initialize AmbientLight based object with its color and intensity
     *
     * @param ia color of the light
     * @param ka intensity of the light
     */
    public AmbientLight(Color ia, double ka) {
        intensity = ia.scale(ka);
    }
    /**
     * Getter for the intensity of the light
     *
     * @return the intensity of the light
     */
    public Color getIntensity() {
        return intensity;
    }
}
