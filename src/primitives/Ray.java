package primitives;

import geometries.Intersectable.GeoPoint;
import java.util.List;
import static primitives.Util.isZero;

/**
 * The Ray class represents a ray in a three-dimensional space.
 */
public class Ray {
    /**
     * The delta for the shadow rays movement from the axis
     */
    private static final double DELTA = 0.1;
    /**
     * The starting point of the ray.
     */
    private final Point p0;
    /**
     * The direction of the ray.
     */
    private final Vector dir;

    /**
     * Constructs a new Ray object with the specified starting point and direction.
     *
     * @param p0  The starting point of the ray.
     * @param dir The direction of the ray.
     */
    public Ray(Point p0, Vector dir) {
        this.p0 = p0;
        this.dir = dir.normalize();
    }

    /**
     * Constructs a new Ray object with the specified starting point and direction.
     *
     * @param p0     The starting point of the ray.
     * @param dir    The direction of the ray - must be normalized.
     * @param normal The normal vector - must be normalized.
     */
    public Ray(Point p0, Vector dir, Vector normal) {
        double delta = dir.dotProduct(normal) >= 0 ? DELTA : -DELTA;
        this.p0 = p0.add(normal.scale(delta));
        this.dir = dir;
    }

    @Override
    public String toString() {
        return "Ray{" + "p0=" + this.p0 + ", dir=" + this.dir + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof Ray other) && this.p0.equals(other.p0) && this.dir.equals(other.dir);
    }

    /**
     * Getter for the starting point of the ray
     *
     * @return The starting point of the ray.
     */
    public Point getP0() {
        return this.p0;
    }

    /**
     * Getter for the direction of the ray
     *
     * @return The direction of the ray.
     */
    public Vector getDir() {
        return this.dir;
    }

    /**
     * Calculates the point at a given distance from the ray head
     *
     * @param t The distance
     * @return The calculated point
     */
    public Point getPoint(double t) {
        return isZero(t) ? p0 : this.p0.add(this.dir.scale(t));
    }

    /**
     * Finds the closest point to the ray head from a given list of points
     *
     * @param points The list of points
     * @return The closest point
     */
    public Point findClosestPoint(List<Point> points) {
        return points == null || points.isEmpty() ? null
                : findClosestGeoPoint(points.stream().map(p -> new GeoPoint(null, p)).toList()).point;
    }

    /**
     * Finds the closest point to the ray head from a given list of points
     *
     * @param points The list of GeoPoints
     * @return The closest point
     */
    public GeoPoint findClosestGeoPoint(List<GeoPoint> points) {
        if (points == null || points.isEmpty()) return null;
        GeoPoint result = null;
        double minDistance = Double.POSITIVE_INFINITY;
        for (GeoPoint point : points) {
            double distance = point.point.distance(this.p0);
            if (distance < minDistance) {
                minDistance = distance;
                result = point;
            }
        }
        return result;
    }
}
