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
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {// TODO: 22/05/2023
        // Check if the ray intersects the plane of the triangle.
        List<GeoPoint> points = plane.findGeoIntersectionsHelper(ray);
        if (points == null)  return null;

        // Create vectors from the intersection point to the vertices
        // and check if the intersection point is inside the triangle.
        List<Vector> vectors = new LinkedList<>();
        for (Point vertex : vertices) {
            try {
                Vector v = vertices.get((vertices.indexOf(vertex) + 1) % vertices.size()).subtract(vertex);
                Vector u = vertex.subtract(points.get(0).point);
                vectors.add(v.crossProduct(u));
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
        // Check if all the vectors are in the same direction.
        // If not, the point is outside the triangle, so return null.
        Vector triangleNormal = plane.getNormal();
        int countNegOrPos = 0;
        for (Vector vector : vectors)
            if (triangleNormal.dotProduct(vector) > 0)
                countNegOrPos++;

        if (countNegOrPos == vertices.size() || countNegOrPos == 0) {
            points.get(0).geometry = this;
            return points;
        } else {
            return null;
        }
    }
}

