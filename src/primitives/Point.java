package primitives;

/**
 * Class Point is the basic class representing a point~
 * 3-Dimensional coordinate system.
 *
 * @author Ziv Farjun and Avidan Maatuk
 */
public class Point {
    /**
     * The point (0,0,0).
     */
    public static final Point ZERO = new Point(0, 0, 0);

    /**
     * The coordinates of the point.
     */
    final protected Double3 xyz;

    /**
     * Creates a Point with the maximum coordinates from two input Points.
     * @param maxBbox The first Point.
     * @param max The second Point.
     * @return A new Point with the maximum coordinates from the two input Points.
     */
    public static Point createMaxPoint(Point maxBbox, Point max) {
        return new Point(Math.max(maxBbox.getX(), max.getX()), Math.max(maxBbox.getY(), max.getY()), Math.max(maxBbox.getZ(), max.getZ()));
    }

    /**
     * Creates a Point with the minimum coordinates from two input Points.
     * @param minBbox The first Point.
     * @param min The second Point.
     * @return A new Point with the minimum coordinates from the two input Points.
     */
    public static Point createMinPoint(Point minBbox, Point min) {
    return new Point(Math.min(minBbox.getX(), min.getX()), Math.min(minBbox.getY(), min.getY()), Math.min(minBbox.getZ(), min.getZ()));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof Point other) && this.xyz.equals(other.xyz);
    }

    @Override

    public String toString() {
        return "Point{" + xyz + "}";
    }

    /**
     * Constructs a new Point object with the specified coordinates.
     *
     * @param xyz The coordinates of the point.
     */
    public Point(Double3 xyz) {
        this.xyz = xyz;
    }

    /**
     * Constructs a new Point object with the specified coordinates.
     *
     * @param x The x coordinate of the point.
     * @param y The y coordinate of the point.
     * @param z The z coordinate of the point.
     */
    public Point(double x, double y, double z) {
        this.xyz = new Double3(x, y, z);
    }

    /**
     * Calculate a new vector that is the difference between this point and the given point.
     *
     * @param point1 the point to subtract from this point
     * @return subtracted vector
     */
    public Vector subtract(Point point1) {
        return new Vector(this.xyz.subtract(point1.xyz));
    }

    /**
     * Calculate a new point that is the sum of this point and the given vector.
     *
     * @param vector the vector to add to this point
     * @return added point
     */
    public Point add(Vector vector) {
        return new Point(this.xyz.add(vector.xyz));
    }

    /**
     * Calculate a new point that is the difference between this point and the given vector.
     *
     * @param point the point to calculate the distance from
     * @return the squared distance
     */
    public double distanceSquared(Point point) {
        double dx = this.xyz.d1 - point.xyz.d1;
        double dy = this.xyz.d2 - point.xyz.d2;
        double dz = this.xyz.d3 - point.xyz.d3;
        return dx * dx + dy * dy + dz * dz;
    }

    /**
     * Calculate the distance between this point and the given point.
     *
     * @param point the point to calculate the distance from
     * @return the distance
     */
    public double distance(Point point) {
        return Math.sqrt(distanceSquared(point));
    }

    /**
     * Getter for the x coordinate of the point.
     *
     * @return the x coordinate of the point
     */
    public double getX() {
        return this.xyz.d1;
    }

    /**
     * Getter for the y coordinate of the point.
     *
     * @return the y coordinate of the point
     */
    public double getY() {
        return this.xyz.d2;
    }

    /**
     * Getter for the z coordinate of the point.
     *
     * @return the z coordinate of the point
     */
    public double getZ() {
        return this.xyz.d3;
    }


}
