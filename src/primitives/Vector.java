package primitives;

/**
 * The Vector class represents a vector in a 3D Cartesian coordinate system.
 */
public class Vector extends Point{

     Vector(Double3 xyz) {
        super(xyz);
         if (xyz.equals(Double3.ZERO)) {
             throw new IllegalArgumentException();
         }


     }

    public Vector(double x, double y, double z) {
        super(x, y, z);
        if (this.xyz.equals(Double3.ZERO)){
            throw new IllegalArgumentException();
        }
    }
    @Override
    public String toString() {
        return "Vector{" +
                xyz.toString() +
                '}';
    }
    /**
     * Returns a new vector that is the sum of this vector and the given vector.
     *
     * @param vector the vector to add to this vector
     * @return a new vector that is the sum of this vector and the given vector
     */
    public Vector add(Vector vector) {
        return new Vector(xyz.add(vector.xyz));
    }
    /**
     * Returns a scaled version of this vector.
     *
     * @param k the scaling factor
     * @return a scaled version of this vector
     */
    public Vector scale (double k) {
         return new Vector (xyz.scale(k));
    }
    /**
     * Returns the dot product of this vector and the given vector.
     *
     * @param vector the vector to calculate the dot product with
     * @return the dot product of this vector and the given vector
     */
    public double dotProduct (Vector vector) {
        return ((this.xyz.d1 * vector.xyz.d1)
               +(this.xyz.d2 * vector.xyz.d2)
               +(this.xyz.d3 * vector.xyz.d3));
    }
    /**
     * Returns the cross product of this vector and the given vector.
     *
     * @param vector the vector to calculate the cross product with
     * @return the cross product of this vector and the given vector
     */
    public Vector crossProduct (Vector vector) {
    return new Vector((this.xyz.d2*vector.xyz.d3-this.xyz.d3*vector.xyz.d2),
            (this.xyz.d3*vector.xyz.d1-this.xyz.d1*vector.xyz.d3),
            (this.xyz.d1*vector.xyz.d2-this.xyz.d2*vector.xyz.d1));
    }
    /**
     * Returns the squared length of this vector.
     *
     * @return the squared length of this vector
     */
    public double lengthSquared (){
        return (this.xyz.d1*this.xyz.d1+
                this.xyz.d2*this.xyz.d2+
                this.xyz.d3*this.xyz.d3);
    }
    /**
     * Returns the length of this vector.
     *
     * @return the length of this vector
     */
    public double length () {
         return Math.sqrt(this.lengthSquared());
    }
    /**
     * Returns a normalized version of this vector.
     *
     * @return a normalized version of this vector
     */
    public Vector normalize () {
         double len = this.length();
         return new Vector (this.xyz.d1/len , this.xyz.d2/len , this.xyz.d3/len);
    }
    }