package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * This class will present a directional light source
 *
 * @author ziv and aviden
 */
public class DirectionalLight extends Light implements LightSource {


    private Vector direction;

    /**
     * Constructor to initialize DirectionalLight based object with its color and intensity
     *
     * @param Intensity light intensity value
     * @param direction light direction
     */
    protected DirectionalLight(Color Intensity, Vector direction) {
        super(Intensity);
        this.direction = direction;
    }

    @Override
    public Color getIntensity(Point p) {
        return this.getIntensity();
    }

    @Override
    public Vector getL(Point p) {
        return this.direction.normalize();
    }
}
