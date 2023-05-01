package geometries;

import primitives.Point;
import primitives.Vector;
import primitives.Ray;

import java.util.List;

import static primitives.Util.isZero;

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
     * @param radius  The radius of the tube.
     */
    Tube(Ray axisRay, double radius) {
        super(radius);
        this.axisRay = axisRay;
    }

    @Override
    public Vector getNormal(Point point) {
        Point p0 = this.axisRay.getP0();
        Vector v = this.axisRay.getDir();
        if (point.equals(p0))
            return point.subtract(p0).normalize();
        Vector p_p0 = point.subtract(this.axisRay.getP0());
        if (p_p0.crossProduct(v).length() == 0)
            throw new IllegalArgumentException("point cant be on the ray");
        double t = p_p0.dotProduct(this.axisRay.getDir());
        if (isZero(t))
            // point is in-front of p0
            return point.subtract(p0).normalize();
        Point o = axisRay.getPoint(t);
        return point.subtract(o).normalize();
    }


    @Override
    public List<Point> findIntersections(Ray ray) {
        return null;
    }

}
