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
        Point p1=this.axisRay.getP0();
        Vector p2=this.axisRay.getDir();
        Point norm = p1.add(p2.scale(p2.dotProduct(point.subtract(p1))));
        return point.subtract(norm).normalize();
    }
}
