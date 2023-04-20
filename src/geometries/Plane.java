package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

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

    @Override
    public List<Point> findIntsersections(Ray ray) {
        return null;
    }
}
