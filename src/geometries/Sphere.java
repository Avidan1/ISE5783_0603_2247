package geometries;

import primitives.Double3;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static java.awt.geom.CubicCurve2D.iszero;
import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * The Sphere class represents a sphere in a 3D Cartesian coordinate system.
 * This class extends the RadialGeometry class and adds a center point to define the position of the sphere.
 */
public class Sphere extends RadialGeometry {
    /**
     * The center point of the sphere.
     */
    private final Point center;

    /**
     * Construct a new Sphere object with the given center point and radius.
     *
     * @param center the center point of the sphere
     * @param radius the radius of the sphere
     */
    Sphere(Point center, double radius) {
        super(radius);
        this.center = center;
    }
    @Override
    public Vector getNormal(Point point) {
        //calculate the normal vector to the sphere
        return  point.subtract(center).normalize();
    }
    @Override
    public List<Point> findIntsersections(Ray ray) {
        Point p0 = ray.getP0();
        Vector v = ray.getDir();

        Vector n = getNormal(p0);
        // if the point is on the center of the sphere
        if (center.equals(p0)) {
            return null;
        }
        // vector from the center of the sphere to the point p0
        Vector p0_q0 = center.subtract(p0);
        // numerator of the quadratic equation
        double nP0Q0 = alignZero(n.dotProduct(p0_q0));
        // if the point is on the plane of the sphere
        if (isZero(nP0Q0)) {
            return null;
        }
        //demominator of the quadratic equation
        double nv = alignZero(n.dotProduct(v));

        // if the ray is parallel to the plane of the sphere
        if (isZero(nv)) {
            return null;
        }

        double t = alignZero(nP0Q0 / nv);

        // if the ray is in the opposite direction of the plane of the sphere
         if (t <= 0) {
            return null;
        }

         Point point = ray.getPoint(t);

         return List.of(point);
    }
}
