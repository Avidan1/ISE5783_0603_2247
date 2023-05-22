package geometries;

/**
 * RadialGeometry class represents a geometry with a radius
 */
public abstract class RadialGeometry extends Geometry {
    /**
     * The radius of the Radial Geometry
     */
    protected final double radius;

    /**
     * The squared radius of the Radial Geometry
     */
    protected final double radius2;

    /**
     * Constructs a new RadialGeometry object with the specified radius.
     *
     * @param radius The radius of the Radial Geometry.
     */
    RadialGeometry(double radius) {
        this.radius = radius;
        this.radius2 = radius * radius;
    }

}
