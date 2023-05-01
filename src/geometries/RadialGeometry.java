package geometries;

/**
 * RadialGeometry class represents a geometry with a radius
 */
public abstract class RadialGeometry implements Geometry {
    /**
     * The radius of the Radial Geometry
     */
    protected final double radius;

    /**
     * Constructs a new RadialGeometry object with the specified radius.
     *
     * @param radius The radius of the Radial Geometry.
     */
    RadialGeometry(double radius) {
        this.radius = radius;
    }

}
