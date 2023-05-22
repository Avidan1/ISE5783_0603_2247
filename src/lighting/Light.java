package lighting;

import primitives.Color;

/**
 * This class will serve all light sources
 *
 * @author Avidan and Ziv
 */
public class Light {
    /**
     * Light intensity
     */
    protected final Color intensity;

    /**
     * Constructor to initialize light intensity
     *
     * @param Intensity light intensity value
     */
    protected Light(Color Intensity) {
        this.intensity = Intensity;
    }

    /**
     * Getter for light intensity
     *
     * @return light intensity value
     */
    public Color getIntensity() {
        return this.intensity;
    }
}
