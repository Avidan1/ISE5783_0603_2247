package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;

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
    public final List<GeoPoint> findGeoIntersectionsHelper(Ray ray,double maxDistance) {
        List<GeoPoint> intersections = super.plane.findGeoIntersections(ray, maxDistance);
        // Check if the ray intersects the plane of the triangle.
        if (intersections == null)
            return null;
        GeoPoint p = intersections.get(0);
        // Check if the ray intersects the triangle.
        Vector v1 = this.vertices.get(0).subtract(ray.getP0());
        Vector v2 = this.vertices.get(1).subtract(ray.getP0());
        Vector n1 = v1.crossProduct(v2).normalize();
        double dotProduct1 = alignZero(n1.dotProduct(ray.getDir()));
        if (dotProduct1 == 0) return null;

        Vector v3 = this.vertices.get(2).subtract(ray.getP0());
        Vector n2 = v2.crossProduct(v3).normalize();
        double dotProduct2 = alignZero(n2.dotProduct(ray.getDir()));
        if (dotProduct2 * dotProduct1 <= 0) return null;

        Vector n3 = v3.crossProduct(v1).normalize();
        double dotProduct3 = n3.dotProduct(ray.getDir());
        if (dotProduct3 * dotProduct1 <= 0) return null;

        return List.of(new GeoPoint(this, p.point));
    }
}

