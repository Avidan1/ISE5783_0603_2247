package primitives;

/**
 * The Vector class represents a vector in a 3D Cartesian coordinate system.
 */
public class Vector extends Point {
    /**
     * Constructs a new Vector object with the specified coordinates.
     *
     * @param xyz The coordinates of the vector.
     */
    Vector(Double3 xyz) {
        super(xyz);
        if (xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException();
    }

    /**
     * Constructs a new Vector object with the specified coordinates.
     *
     * @param x The x coordinate of the vector.
     * @param y The y coordinate of the vector.
     * @param z The z coordinate of the vector.
     */
    public Vector(double x, double y, double z) {
        super(x, y, z);
        if (this.xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException();
    }

    /**
     * @return a string representation of this vector
     */
    @Override
    public String toString() {
        return "Vector{" + this.xyz + "}";
    }

    /**
     * Calculate a new vector that is the sum of this vector and the given vector.
     *
     * @param vector the vector to add to this vector
     * @return the sum vector
     */
    public Vector add(Vector vector) {
        return new Vector(this.xyz.add(vector.xyz));
    }

    /**
     * Calculate a scaled version of this vector.
     *
     * @param k the scaling factor
     * @return a scaled version of this vector
     */
    public Vector scale(double k) {
        return new Vector(this.xyz.scale(k));
    }

    /**
     * Calculate the dot product of this vector and the given vector.
     *
     * @param vector the vector to calculate the dot product with
     * @return the dot product's Saclar
     */
    public double dotProduct(Vector vector) {
        return (this.xyz.d1 * vector.xyz.d1)
                + (this.xyz.d2 * vector.xyz.d2)
                + (this.xyz.d3 * vector.xyz.d3);
    }

    /**
     * Calculate the cross product of this vector and the given vector.
     *
     * @param vector the vector to calculate the cross product with
     * @return the cross product's Vector
     */
    public Vector crossProduct(Vector vector) {
        return new Vector(((this.xyz.d2 * vector.xyz.d3) - (this.xyz.d3 * vector.xyz.d2)),
                ((this.xyz.d3 * vector.xyz.d1) - (this.xyz.d1 * vector.xyz.d3)),
                ((this.xyz.d1 * vector.xyz.d2) - (this.xyz.d2 * vector.xyz.d1)));
    }

    /**
     * Calculate the squared length of this vector.
     *
     * @return the squared length
     */
    public double lengthSquared() {
        return (this.xyz.d1 * this.xyz.d1 +
                this.xyz.d2 * this.xyz.d2 +
                this.xyz.d3 * this.xyz.d3);
    }

    /**
     * Calculate the length of this vector.
     *
     * @return the length value
     */
    public double length() {
        return Math.sqrt(this.lengthSquared());
    }

    /**
     * Calculate a normalized version of this vector.
     *
     * @return a normalized version's vector
     */
    public Vector normalize() {
        return new Vector(this.xyz.reduce(this.length()));
    }
}