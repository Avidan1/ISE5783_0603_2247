package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

import static primitives.Util.alignZero;

/**
 * This class will serve all spotlight sources
 * It extends the abstract class PointLight and implements the LightSource interface.
 * The position of the light source is defined by a point in space.
 * @author Avidan and Ziv
 */
public class SpotLight extends PointLight {
    /**
     * The direction of the light
     */
    private final Vector direction;

    /**
     * Constructor to initialize PointLight based object with its color and intensity
     *
     * @param intensity light intensity value
     * @param position  light position
     * @param direction light direction
     */
    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalize();
    }

    @Override
    public Color getIntensity(Point p) {
        double lDirection = alignZero(getL(p).dotProduct(this.direction));
        return lDirection <= 0 ? Color.BLACK : super.getIntensity(p).scale(lDirection);
    }

    @Override
    public Vector getL(Point p) {
        return super.getL(p);
    }
}
