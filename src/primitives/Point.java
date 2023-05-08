package primitives;

/**
 * Class Point is the basic class representing a point~
 * 3-Dimensional coordinate system.
 *
 * @author Ziv Farjun and Avidan Maatuk
 */
public class Point {
    final protected Double3 xyz;

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
    Point(Double3 xyz) {
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
     * @return the x coordinate of the point
     */
    public double getX() {
        return this.xyz.d1;
    }
}