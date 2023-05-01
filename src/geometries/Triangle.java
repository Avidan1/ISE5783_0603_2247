package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

/**
 * Triangle class represents a triangle in 3D Cartesian coordinate system
 */
public class Triangle extends Polygon {
    /**
     * Constructs a new Triangle object with the specified vertices.
     *
     * @param v1 The first vertex of the triangle.
     * @param v2 The second vertex of the triangle.
     * @param v3 The third vertex of the triangle.
     */
    public Triangle(Point v1, Point v2, Point v3) {
        super(v1, v2, v3);
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        Vector v1 = ((Point) vertices.toArray()[0]).subtract(ray.getP0());
        Vector v2 = ((Point) vertices.toArray()[1]).subtract(ray.getP0());
        Vector v3 = ((Point) vertices.toArray()[2]).subtract(ray.getP0());

        Vector n1 = v1.crossProduct(v2);
        Vector n2 = v1.crossProduct(v2);
        Vector n3 = v1.crossProduct(v2);

        if (n1.dotProduct(ray.getDir()) < 0) {
            if (n2.dotProduct(ray.getDir()) > 0 || n3.dotProduct(ray.getDir()) > 0)
                return null;
        }
        if (n1.dotProduct(ray.getDir()) > 0) {
            if (n2.dotProduct(ray.getDir()) < 0 || n3.dotProduct(ray.getDir()) < 0)
                return null;
        }

        return plane.findIntersections(ray);
    }
}

