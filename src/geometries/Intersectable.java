package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

/**
 * Intersectable interface represents a geometry in 3D Cartesian coordinate system
 */
public abstract class Intersectable {
    /**
     * GeoPoint class represents a point on a geometry
     */
    public static class GeoPoint {
        /**
         * geometry object
         */
        public Geometry geometry;
        /**
         * point on the geometry
         */
        public Point point;

        /**
         * constructor
         *
         * @param geometry object
         * @param point    on the geometry
         */
        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            return (obj instanceof GeoPoint) && this.geometry.equals(((GeoPoint) obj).geometry) && this.point.equals(((GeoPoint) obj).point);
        }

        @Override
        public String toString() {
            return "GeoPoint{" + "geometry=" + this.geometry + ", point=" + this.point + '}';
        }
    }

    /**
     * calculate the intersection points of the geometry with the specified ray
     *
     * @param ray to intersect with
     * @return a list of the intersection points
     */
    public final List<Point> findIntersections(Ray ray) {
        List<GeoPoint> geoList = findGeoIntersections(ray);
        return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
    }

    /**
     * calculate the intersection points of the geometry with the specified ray
     *
     * @param ray to intersect with
     * @return a list of the intersection points
     */
    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray);

    /**
     * calculate the intersection points of the geometry with the specified ray
     *
     * @param ray to intersect with
     * @return a list of the intersection points
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersectionsHelper(ray);
    }
}
