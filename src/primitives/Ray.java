package primitives;

import java.util.List;

import static primitives.Util.isZero;

/**
 * The Ray class represents a ray in a three-dimensional space.
 */
public class Ray {
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
     * @param p0  The starting point of the ray.
     * @param dir The direction of the ray.
     */
    public Ray(Point p0, Vector dir) {
        this.p0 = p0;
        this.dir = dir.normalize();
    }

    @Override
    public String toString() {
        return "Ray{" +
                "p0=" + this.p0 +
                ", dir=" + this.dir +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof Ray other) && this.p0.equals(other.p0) && this.dir.equals(other.dir);
    }

    /**
     * Getter for the starting point of the ray
     * @return The starting point of the ray.
     */
    public Point getP0() {
        return this.p0;
    }

    /**
     * Getter for the direction of the ray
     * @return The direction of the ray.
     */
    public Vector getDir() {
        return this.dir;
    }

    /**
     * Calculates the point at a given distance from the ray head
     * @param t The distance
     * @return The calculated point
     */
    public Point getPoint(double t) {
        return isZero(t) ? p0 : this.p0.add(this.dir.scale(t));
    }

    public Point findClosestPoint (List<Point> points) {
        Point result = null;
        double minDistance = Double.POSITIVE_INFINITY;
        for (Point point : points) {
            double distance = point.distance(this.p0);
            if (distance < minDistance) {
                minDistance = distance;
                result = point;
            }
        }
        return result;

    }
}