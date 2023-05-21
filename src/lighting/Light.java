package lighting;

import primitives.Color;

/**
 * This class will serve all light sources
 * @author Avidan and Ziv
 */
public class Light {
    /**
     * Light intensity
     */
    private final Color intensity;

    /**
     * Constructor to initialize light intensity
     *
     * @param Intensity light intensity value
     */
    protected Light(Color Intensity) {
        intensity = Intensity;
    }

    /**
     * Getter for light intensity
     *
     * @return light intensity value
     */
    public Color getIntensity() {
        return intensity;
    }
}
