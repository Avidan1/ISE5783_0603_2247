package primitives;

/**
 * The Vector class represents a 3D vector object.
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
    public Vector add(Vector vector) {
        return new Vector(xyz.add(vector.xyz));
    }
    public Vector scale (double k) {
         return new Vector (xyz.scale(k));
    }
    public double dotProduct (Vector vector) {
        return ((this.xyz.d1 * vector.xyz.d1)
               +(this.xyz.d2 * vector.xyz.d2)
               +(this.xyz.d3 * vector.xyz.d3));
    }
    public Vector crossProduct (Vector vector) {
    return new Vector((this.xyz.d2*vector.xyz.d3-this.xyz.d3*vector.xyz.d2),
            (this.xyz.d3*vector.xyz.d1-this.xyz.d1*vector.xyz.d3),
            (this.xyz.d1*vector.xyz.d2-this.xyz.d2*vector.xyz.d1));
    }
    public double lengthSquared (){
        return (this.xyz.d1*this.xyz.d1+
                this.xyz.d2*this.xyz.d2+
                this.xyz.d3*this.xyz.d3);
    }
    public double length () {
         return Math.sqrt(this.lengthSquared());
    }
    public Vector normalize () {
         double len = this.length();
         return new Vector (this.xyz.d1/len , this.xyz.d2/len , this.xyz.d3/len);
    }
    }