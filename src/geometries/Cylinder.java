package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * The Cylinder class represents a 3D cylinder object that extends a tube, with a defined height.
 */
public class Cylinder extends Tube {

    /**
     * The height of the cylinder.
     */
    private double height;

    /**
     * Constructs a new Cylinder object with the specified axis ray, radius, and height.
     *
     * @param axisRay The axis ray of the cylinder.
     * @param radius The radius of the cylinder.
     * @param height The height of the cylinder.
     */
    public Cylinder(Ray axisRay, double radius, double height) {
        super(axisRay, radius);
        this.height = height;
    }

    /**
     * Returns the normal vector to the cylinder at the specified point.
     *
     * @param point The point at which to retrieve the normal vector.
     * @return The normal vector to the cylinder at the specified point.
     */
    @Override
    public Vector getNormal(Point point) {
        return super.getNormal(point);
    }
}
