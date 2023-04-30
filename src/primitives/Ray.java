package primitives;

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
     *
     * @param p0  The starting point of the ray.
     * @param dir The direction of the ray.
     */
    public Ray(Point p0, Vector dir) {
        this.p0 = p0;
        this.dir = dir.normalize();
    }

    /**
     * @return A string representation of the ray.
     */
    @Override
    public String toString() {
        return "Ray{" +
                "p0=" + this.p0 +
                ", dir=" + this.dir +
                '}';
    }

    @Override
    /**
     * @return True if the two rays are equal, false otherwise.
     */
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof Ray other) && this.p0.equals(other.p0) && this.dir.equals(other.dir);
    }

    /**
     * @return The starting point of the ray.
     */
    public Point getP0() {
        return this.p0;
    }

    /**
     * @return The direction of the ray.
     */
    public Vector getDir() {
        return this.dir;
    }

    /**
     * @param t The length of the direction vector.
     * @return The point on the ray at the specified length.
     */
    public Point getPoint(double t) {
        if (isZero(t)) {
            throw new IllegalArgumentException("t cannot be 0 or the length of the direction vector");
        }
        return this.p0.add(this.dir.scale(t));
    }
}