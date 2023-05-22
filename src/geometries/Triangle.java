package geometries;

import primitives.*;

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
        Vector v1 = vertices.get(0).subtract(ray.getP0());
        Vector v2 = vertices.get(1).subtract(ray.getP0());
        Vector v3 = vertices.get(2).subtract(ray.getP0());

        Vector n1 = v1.crossProduct(v2);
        Vector n2 = v2.crossProduct(v3);
        Vector n3 = v3.crossProduct(v1);

        double dotProduct1 = n1.dotProduct(ray.getDir());
        double dotProduct2 = n2.dotProduct(ray.getDir());
        double dotProduct3 = n3.dotProduct(ray.getDir());

        if (Util.isZero(dotProduct1) || Util.isZero(dotProduct2) || Util.isZero(dotProduct3)) {
            return null; // The point is on edge's continuation.
        }

        if ((dotProduct1 < 0 && (dotProduct2 > 0 || dotProduct3 > 0))
                || (dotProduct1 > 0 && (dotProduct2 < 0 || dotProduct3 < 0))) {
            return null; // Ray direction positivity check.
        }

        return List.of(new GeoPoint(this,points.get(0).point));
    }
}

