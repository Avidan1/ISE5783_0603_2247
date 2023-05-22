package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

import static primitives.Util.alignZero;


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
     */
    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalize();
    }

    @Override
    public Color getIntensity(Point p) {
        double lDirection = alignZero(getL(p).dotProduct(direction));
        return lDirection <= 0 ? Color.BLACK : super.getIntensity(p).scale(lDirection);
    }

    @Override
    public Vector getL(Point p) {
        return super.getL(p);
    }
}
