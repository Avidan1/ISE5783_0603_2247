package geometries;

import primitives.Point;
import primitives.Vector;
/**
 * The Sphere class represents a sphere in a 3D Cartesian coordinate system.
 * This class extends the RadialGeometry class and adds a center point to define the position of the sphere.
 */
public class Sphere extends RadialGeometry {
    Point center;

    /**
     * Creates a new Sphere object with the given center point and radius.
     *
     * @param center the center point of the sphere
     * @param radius the radius of the sphere
     */
    Sphere(Point center, double radius) {
        super(radius);
        this.center = center;
    }

    /**
     * Returns the normal vector of the sphere at the given point.
     *
     * @param point the point to calculate the normal vector at
     * @return the normal vector of the sphere at the given point
     */
    @Override
    public Vector getNormal(Point point) {
        return null;
    }
}
