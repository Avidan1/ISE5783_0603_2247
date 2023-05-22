package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;


public class SpotLight extends PointLight{
    /**
     * The direction of the light
     */
    private Vector direction;
    /**
     * Constructor to initialize PointLight based object with its color and intensity
     *
     * @param Intensity light intensity value
     * @param position  light position
     */
    public SpotLight(Color Intensity, Point position) {
        super(Intensity, position);
        this.direction = direction.normalize();
    }

    @Override
    public Color getIntensity(Point p) {
        double nominator = Math.max(0, direction.dotProduct(getL(p)));
        return super.getIntensity(p).scale(nominator);
    }

    @Override
    public Vector getL(Point p) {
        return super.getL(p);
    }
}
