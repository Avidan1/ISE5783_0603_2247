package geometries;

import primitives.Point;
import primitives.Vector;
import primitives.Ray;
/**
 * Tube class represents a tube in 3D Cartesian coordinate system
 */
public class Tube extends RadialGeometry {
    /**
     * The axis ray of the tube.
     */
    protected final Ray axisRay;
    /**
     * Constructs a new Tube object with the specified axis ray and radius.
     *
     * @param axisRay The axis ray of the tube.
     * @param radius The radius of the tube.
     */
    Tube(Ray axisRay, double radius){
        super(radius);
        this.axisRay=axisRay;
    }
    @Override
    public Vector getNormal(Point point) {
        try{
            Point O = this.axisRay.getP0()
                    .add((this.axisRay.getDir())
                            .scale(point.subtract(this.axisRay.getP0())
                                    .dotProduct(this.axisRay.getDir())));
            return point.subtract(O).normalize();
        }
        catch (IllegalArgumentException e){
            //p is in front of the ray
            return this.axisRay.getP0().subtract(point).normalize();
        }
    }
}
