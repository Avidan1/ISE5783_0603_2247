package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * The Plane class represents a 3D plane object, defined by a point and a normal vector.
 */
public class Plane implements Geometry {
    /**
     * The point on the plane.
     */
    private final Point q0;

    /**
     * The normal vector to the plane.
     */
    private final Vector normal;

    /**
     * Constructs a new Plane object with the specified point and normal vector.
     *
     * @param q0     The point on the plane.
     * @param normal The normal vector to the plane.
     */
    public Plane(Point q0, Vector normal) {
        this.q0 = q0;
        this.normal = normal.normalize();
    }

    /**
     * Constructs a new Plane object with three points on the plane.
     *
     * @param p1 The first point on the plane.
     * @param p2 The second point on the plane.
     * @param p3 The third point on the plane.
     */
    public Plane(Point p1, Point p2, Point p3) {
        this.q0 = p1;
        this.normal = p1.subtract(p2).crossProduct(p1.subtract(p3)).normalize();
    }

    @Override
    public Vector getNormal(Point point) {
        return this.normal;
    }

    /**
     * @param ray
     * @return
     */
    @Override
    public List<Point> findIntersections(Ray ray) {
        Point p0 = ray.getP0();
        Vector v = ray.getDir();

        Vector n = getNormal(p0);
        // if the point is on point q0
        if (q0.equals(p0)) {
            return null;
        }
        // vector from the center of the sphere to the point p0
        Vector p0_q0 = q0.subtract(p0);
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

        // if the ray is in the opposite direction of the plane
        if (t <= 0) {
            return null;
        }

        Point point = ray.getPoint(t);

        return List.of(point);

    }

    /**
     * @return The normal vector to the plane.
     */
    public Vector getNormal() {
        return this.normal;
    }

    /**
     * @return The point on the plane.
     */
    public Point getQ0() {
        return this.q0;
    }


}
