package geometries;

import primitives.Point;

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
}
