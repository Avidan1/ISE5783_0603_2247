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
    public static abstract class GeoPoint {
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
            return (obj instanceof GeoPoint) && geometry.equals(((GeoPoint) obj).geometry) && point.equals(((GeoPoint) obj).point);
        }

        @Override
        public String toString() {
            return "GeoPoint{" + "geometry=" + geometry + ", point=" + point + '}';
        }
    }

    /**
     * calculate the intersection points of the geometry with the specified ray
     *
     * @param ray to intersect with
     * @return a list of the intersection points
     */
    public abstract List<Point> findIntersections(Ray ray);

    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        List<Point> intersections = findIntersections(ray);
        if (intersections == null) return null;
        List<GeoPoint> result = new java.util.ArrayList<>(intersections.size());
        for (Point p : intersections)
            result.add(new GeoPoint(this, p));
        return result;
    }
    public  List<GeoPoint> findGeoIntersections(Ray ray){
        return findGeoIntersectionsHelper(ray);
    }
}
