package lighting;

import primitives.Color;
import primitives.Double3;


/**
 * Class AmbientLight is the basic class representing a light source with no direction
 * and no location, but with a color and intensity.
 *
 * @author Ziv Farjun and Avidan Maatuk
 */

public class AmbientLight extends Light {

    /**
     * Constant AmbientLight with no intensity
     */
    public static final AmbientLight NONE = new AmbientLight(Color.BLACK, Double3.ZERO);

    /**
     * Constructor to initialize AmbientLight based object with its color and intensity
     *
     * @param ia intensity of the light
     * @param ka attenuation factor (rgb) of the light
     */
    public AmbientLight(Color ia, Double3 ka) {
        super(ia.scale(ka));
    }

    /**
     * Constructor to initialize AmbientLight based object with its color and intensity
     *
     * @param ia intensity of the light
     * @param ka attenuation factor (single number) of the light
     */
    @SuppressWarnings("unused")
    public AmbientLight(Color ia, double ka) {
        super(ia.scale(ka));
    }

}
