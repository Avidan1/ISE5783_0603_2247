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
     * @param intensity light intensity value
     */
    protected Light(Color intensity) {
        this.intensity = intensity;
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
