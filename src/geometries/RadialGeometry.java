package geometries;

/**
 * RadialGeometry class represents a geometry with a radius
 */
public abstract class RadialGeometry implements Geometry {
    protected double radius;
    RadialGeometry(double radius) {
        this.radius = radius;
    }

}
