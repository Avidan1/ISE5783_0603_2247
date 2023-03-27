package geometries;

import primitives.Point;
import primitives.Vector;
/**
 * The Plane class represents a 3D plane object, defined by a point and a normal vector.
 */
public class Plane implements Geometry {
    /**
     * The point on the plane.
     */
    private Point q0;

    /**
     * The normal vector to the plane.
     */
    private Vector normal;

    /**
     * Constructs a new Plane object with the specified point and normal vector.
     *
     * @param q0 The point on the plane.
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
        normal = getNormal(p1);
        q0 = p2;
    }

    /**
     * implement from the GEOMETRY interface
     *
     * @param point The point at which to retrieve the normal vector.
     * @return The normal vector to the plane at the specified point.
     */
    public Vector getNormal(Point point) {
        return null;
    }

    /**
     * Returns the normal vector to the plane.
     *
     * @return The normal vector to the plane.
     */
    public Vector getNormal() {
        return normal;
    }

    /**
     * Returns the point on the plane.
     *
     * @return The point on the plane.
     */
    public Point getQ0() {
        return q0;
    }
}
