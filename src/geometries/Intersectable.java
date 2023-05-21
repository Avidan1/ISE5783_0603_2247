package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

/**
 * Intersectable interface represents a geometry in 3D Cartesian coordinate system
 */
public interface Intersectable {
    /**
     * calculate the intersection points of the geometry with the specified ray
     *
     * @param ray to intersect with
     * @return a list of the intersection points
     */
    List<Point> findIntersections(Ray ray);

}
