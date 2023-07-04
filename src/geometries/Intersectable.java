package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * Intersectable interface represents a geometry in 3D Cartesian coordinate system
 */
public abstract class Intersectable {
    protected AABB getBBox() {
        return boundingBox
                ;

    }

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
            return (obj instanceof GeoPoint other) &&
                    this.geometry == other.geometry && this.point.equals(other.point);
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

    /**
     * Represents an Axis-Aligned Bounding Box (AABB).
     */
    public class AABB {
        private Point min;
        private Point max;
        private Point center;
        private boolean infinite;

        private static final double DELTA = 0.1;

        /**
         * Constructs an AABB with the given minimum point, maximum point, and center point.
         * Expands the AABB slightly using the DELTA constant.
         *
         * @param min    The minimum point of the AABB.
         * @param max    The maximum point of the AABB.
         * @param center The center point of the AABB.
         */
        public AABB(Point min, Point max, Point center) {
            this.min = min.add(new Vector(-DELTA, -DELTA, -DELTA));
            this.max = max.add(new Vector(DELTA, DELTA, DELTA));
            this.center = center;
        }

        /**
         * Constructs an AABB with the given minimum and maximum points.
         * Calculates the center point and expands the AABB using the DELTA constant.
         *
         * @param min The minimum point of the AABB.
         * @param max The maximum point of the AABB.
         */
        public AABB(Point min, Point max) {
            this.min = min.add(new Vector(-DELTA, -DELTA, -DELTA));
            this.max = max.add(new Vector(DELTA, DELTA, DELTA));
            this.center = new Point(
                    (min.getX() + max.getX()) / 2,
                    (min.getY() + max.getY()) / 2,
                    (min.getZ() + max.getZ()) / 2
            );
        }

        /**
         * Checks if the AABB is infinite.
         *
         * @return true if the AABB is infinite, false otherwise.
         */
        public boolean isInfinite() {
            return infinite;
        }

        /**
         * Sets the infinity flag of the AABB.
         *
         * @param infinite true to set the AABB as infinite, false otherwise.
         * @return The updated AABB instance.
         */
        public AABB setInfinite(boolean infinite) {
            this.infinite = infinite;
            return this;
        }

        /**
         * Checks if the AABB intersects with the given Ray within the specified maximum distance.
         *
         * @param ray         The Ray to check for intersection.
         * @param maxDistance The maximum distance at which intersection can occur.
         * @return true if the AABB intersects with the Ray, false otherwise.
         */
        public boolean intersect(Ray ray, double maxDistance) {
            if (infinite) {
                return true;
            }

            // Calculate intersection with AABB using the slab method
            Vector direction = ray.getDir();
            Vector invDirection = new Vector(1 / direction.getX(), 1 / direction.getY(), 1 / direction.getZ());
            Point[] bounds = {min, max};
            int[] sign = {
                    invDirection.getX() < 0 ? 1 : 0,
                    invDirection.getY() < 0 ? 1 : 0,
                    invDirection.getZ() < 0 ? 1 : 0
            };

            // Initialize variables for intersection tests
            double tMin, tMax, tyMin, tyMax, tzMin, tzMax;
            tMin = (bounds[sign[0]].getX() - ray.getP0().getX()) * invDirection.getX();
            tMax = (bounds[1 - sign[0]].getX() - ray.getP0().getX()) * invDirection.getX();
            tyMin = (bounds[sign[1]].getY() - ray.getP0().getY()) * invDirection.getY();
            tyMax = (bounds[1 - sign[1]].getY() - ray.getP0().getY()) * invDirection.getY();

            // Check for intersection with Y-axis
            if (tMin > tyMax || tyMin > tMax) {
                return false;
            }

            if (tyMin > tMin) {
                tMin = tyMin;
            }

            if (tyMax < tMax) {
                tMax = tyMax;
            }

            tzMin = (bounds[sign[2]].getZ() - ray.getP0().getZ()) * invDirection.getZ();
            tzMax = (bounds[1 - sign[2]].getZ() - ray.getP0().getZ()) * invDirection.getZ();

            // Check for intersection with Z-axis
            if (tMin > tzMax || tzMin > tMax) {
                return false;
            }

            // Check if the intersection occurs within the specified maximum distance
            return tzMax < tMax && tMax < maxDistance;
        }

        /**
         * Calculates the surface area of the AABB.
         *
         * @return The surface area of the AABB.
         */
        public double getAABBArea() {
            Point extent = max.subtract(min);
            return extent.getX() * extent.getY() + extent.getY() * extent.getZ() + extent.getZ() * extent.getX();
        }

        /**
         * Returns the minimum point of the AABB.
         *
         * @return The minimum point of the AABB.
         */
        public Point getMin() {
            return min;
        }

        /**
         * Returns the maximum point of the AABB.
         *
         * @return The maximum point of the AABB.
         */
        public Point getMax() {
            return max;
        }

        /**
         * Returns the center point of the AABB.
         *
         * @return The center point of the AABB.
         */
        public Point getCenter() {
            return center;
        }

        public AABB boundingBox;

    }

}
