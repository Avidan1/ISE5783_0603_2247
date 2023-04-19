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
        Point p0 = this.axisRay.getP0();
        Vector v = this.axisRay.getDir();
        double t = v.dotProduct(point.subtract(p0));

        // check if point is on the axis of the tube
        if (t==0) {
            return v.normalize();
        }

        // calculate the closest point on the axis to the input point
        Point p = p0.add(v.scale(t));

        // calculate and return the normal vector
        Vector n = point.subtract(p);
        return n.normalize();
    }
}
