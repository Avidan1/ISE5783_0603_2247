package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;


/**
 * The Plane class represents a 3D plane object, defined by a point and a normal vector.
 */
public class Plane extends Geometry {
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

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        Point p0 = ray.getP0();
        Vector v = ray.getDir();
        Vector n = getNormal(p0);

        // If the point is on point q0 or the ray is parallel to the plane of the sphere, return null
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0 || this.q0.equals(p0))
            return null;

        // Calculate intersection point
        Vector p0Q0 = this.q0.subtract(p0);
        double nP0Q0 = alignZero(n.dotProduct(p0Q0));
        double t = alignZero(nP0Q0 / nv);
        return t <= 0 ? null : List.of(new GeoPoint(this, ray.getPoint(t)));
    }

    /**
     * returns the normal vector to the plane.
     *
     * @return The normal vector to the plane.
     */
    public Vector getNormal() {
        return this.normal;
    }

    /**
     * returns the point on the plane.
     *
     * @return The point on the plane.
     */
    public Point getQ0() {
        return this.q0;
    }


}
