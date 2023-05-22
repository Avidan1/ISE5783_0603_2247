package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * This class will present a point light source
 *
 * @author ziv and aviden
 */
public class PointLight extends Light implements LightSource {
    /**
     * Point of the light source direction
     */
    private final Point position;


    /**
     * Constant PointLight with no intensity
     */
    private double kC = 1, kL = 0, kQ = 0;

    /**
     * Constructor to initialize PointLight based object with its color and intensity
     *
     * @param Intensity light intensity value
     * @param position  light position
     */
    protected PointLight(Color Intensity, Point position) {
        super(Intensity);
        this.position = position;
    }

    @Override
    public Color getIntensity(Point p) {
        double distanceSquared = position.distanceSquared(p);
        double denominator = this.kC + this.kL * Math.sqrt(distanceSquared) + this.kQ * distanceSquared;
        return this.intensity.reduce(denominator);
    }

    @Override
    public Vector getL(Point p) {
        return p.subtract(position).normalize();
    }

    //=============setters================
    /**
     * setter for kC
     * @param kC
     * @return the object itself with the updated kC
     */
    public PointLight setkC(double kC) {
        this.kC = kC;
        return this;
    }
    /**
     * setter for kL
     * @param kL
     * @return the object itself with the updated kL
     */
    public PointLight setkL(double kL) {
        this.kL = kL;
        return this;
    }
    /**
     * setter for kQ
     * @param kQ
     * @return the object itself with the updated kQ
     */
    public PointLight setkQ(double kQ) {
        this.kQ = kQ;
        return this;
    }

}
