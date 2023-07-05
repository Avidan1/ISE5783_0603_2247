package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

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
            return (obj instanceof GeoPoint other) &&
                    this.geometry == other.geometry && this.point.equals(other.point);
        }

        @Override
        public String toString() {
            return "GeoPoint{" + "geometry=" + this.geometry + ", point=" + this.point + '}';
        }
    }

    /**
     * The AABB class represents an Axis-Aligned Bounding Box.
     * It is used to enclose objects and determine intersection in computer graphics and collision detection algorithms.
     */
    public static class AABB {
        /**
         * min point of the box
         */
        public Point min;
        /**
         * max point of the box
         */
        public Point max;
        /**
         * center point of the box
         */
        public Point center;
        /**
         * Flag indicating if the AABB is infinite.
         */
        public boolean isInfinite = false;

        /**
         * Constant value used for expanding the AABB slightly.
         */
        private static final double DELTA = 0.1;

        /**
         * Checks if the AABB is infinite.
         *
         * @return true if the AABB is infinite, false otherwise.
         */
        public boolean isInfinite() {
            return this.isInfinite;
        }

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
            center = new Point((this.min.getX() + this.max.getX()) / 2, (this.min.getY() + this.max.getY()) / 2, (this.min.getZ() + this.max.getZ()) / 2);
        }

        /**
         * Checks if the AABB intersects with the given Ray within the specified maximum distance.
         *
         * @param ray    The Ray to check for intersection.
         * @param maxDis The maximum distance at which intersection can occur.
         * @return true if the AABB intersects with the Ray, false otherwise.
         */
        public boolean intersect(Ray ray, double maxDis) {
            if (this.isInfinite) return true;
            Vector dir = ray.getDir();
            Point p0 = ray.getP0();
            Vector invDir = new Vector(1 / dir.getX(), 1 / dir.getY(), 1 / dir.getZ());
            int[] sign = {invDir.getX() < 0 ? 1 : 0, invDir.getY() < 0 ? 1 : 0, invDir.getZ() < 0 ? 1 : 0};
            double tmin, tmax, tymin, tymax, tzmin, tzmax;
            Point bounds[] = {this.min, this.max};
            double p0x = p0.getX(), p0y = p0.getY(), p0z = p0.getZ();
            double inx = invDir.getX(), iny = invDir.getY(), inz = invDir.getZ();
            tmin = (bounds[sign[0]].getX() - p0x) * inx;
            tmax = (bounds[1 - sign[0]].getX() - p0x) * inx;
            tymin = (bounds[sign[1]].getY() - p0y) * iny;
            tymax = (bounds[1 - sign[1]].getY() - p0y) * iny;
            if ((tmin > tymax) || (tymin > tmax)) return false;

            if (tymin > tmin) tmin = tymin;
            if (tymax < tmax) tmax = tymax;

            tzmin = (bounds[sign[2]].getZ() - p0z) * inz;
            tzmax = (bounds[1 - sign[2]].getZ() - p0z) * inz;

            if ((tmin > tzmax) || (tzmin > tmax)) return false;
            if (tzmax < tmax)
                tmax = tzmax;

            return (tzmin <= maxDis);
        }

        /**
         * Calculates the surface area of the AABB.
         *
         * @return The surface area of the AABB.
         */
        public double AABBsurfaceArea() {
            Point extents = max.subtract(min);
            double x = extents.getX(), y = extents.getY(), z = extents.getZ();
            return 2 * (x * y + x * z + y * z); // todo *2?
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

        /**
         * Sets the infinity flag of the AABB.
         *
         * @param isInfinite true to set the AABB as infinite, false otherwise.
         * @return The updated AABB instance.
         */
        public AABB setInfinity(boolean isInfinite) {
            this.isInfinite = isInfinite;
            return this;
        }

    }

    /**
     * The box for AABB improvement
     */

    public AABB bbox;

    /**
     * calculate the intersection points of the geometry with the specified ray
     *
     * @param ray to intersect with
     * @return a list of the intersection points
     */
    public final List<Point> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null : geoList.stream().map(gp -> gp).toList();
    }



    /**
     * calculate the intersection points of the geometry with the specified ray
     *
     * @param ray to intersect with
     * @return a list of the intersection points
     */
    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray,double maxDistance);

    /**
     * find geo intersection limited whit max distance
     * @param ray To find the intersection whit
     * @param maxDistance To limit the intersection points
     * @return List<GeoPoint> the ray intersect
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance){
        return findGeoIntersectionsHelper(ray, maxDistance);
    }


    /**
     * calculate the intersection points of the geometry with the specified ray
     *
     * @param ray to intersect with
     * @return a list of the intersection points
     */
    public final List<Point> findGeoIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null : geoList.stream().map(gp -> gp).toList();
    }
    }

