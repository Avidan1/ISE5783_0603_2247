package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * This class represents a point light source.
 * It extends the abstract class Light and implements the LightSource interface.
 * The position of the light source is defined by a point in space.
 * The intensity of the light is specified by its color.
 * The light attenuation factors (kC, kL, kQ) can be set to control the light's intensity decay with distance.
 *
 * @authorAvidan & Ziv
 */
public class PointLight extends Light implements LightSource {
    /**
     * The position of the light source.
     */
    private final Point position;

    /**
     * The constant factors for light attenuation (default values: kC = 1, kL = 0, kQ = 0).
     */
    private double kC = 1, kL = 0, kQ = 0;

    /**
     * Constructs a PointLight object with the specified intensity and position.
     *
     * @param intensity The light intensity as a Color object.
     * @param position  The position of the light source as a Point object.
     */
    protected PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }

    /**
     * Retrieves the intensity of the light at a given point.
     *
     * @param p The point at which to calculate the intensity.
     * @return The intensity of the light as a Color object.
     */
    @Override
    public Color getIntensity(Point p) {
        double distanceSquared = this.position.distanceSquared(p);
        double denominator = this.kC + this.kL * Math.sqrt(distanceSquared) + this.kQ * distanceSquared;
        return this.intensity.reduce(denominator);
    }

    /**
     * Retrieves the normalized direction vector from the light source to a given point.
     *
     * @param p The point from which to calculate the direction vector.
     * @return The direction vector as a Vector object.
     */
    @Override
    public Vector getL(Point p) {
        return p.subtract(this.position).normalize();
    }

    //=============setters================

    /**
     * Sets the constant factor kC for light attenuation.
     *
     * @param kC The value of kC to be set.
     * @return The updated PointLight object.
     */
    public PointLight setKc(double kC) {
        this.kC = kC;
        return this;
    }

    /**
     * Sets the linear factor kL for light attenuation.
     *
     * @param kL The value of kL to be set.
     * @return The updated PointLight object.
     */
    public PointLight setKl(double kL) {
        this.kL = kL;
        return this;
    }

    /**
     * Sets the quadratic factor kQ for light attenuation.
     *
     * @param kQ The value of kQ to be set.
     * @return The updated PointLight object.
     */
    public PointLight setKq(double kQ) {
        this.kQ = kQ;
        return this;
    }

}
