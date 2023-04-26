package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * The Cylinder class represents a 3D cylinder object that extends a tube, with a defined height.
 */
public class Cylinder extends Tube {

    /**
     * The height of the cylinder.
     */
    private final double height;

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
    @Override
    public Vector getNormal(Point point) {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public List<Point> findIntersections(Ray ray) {

        // TODO Auto-generated method stub
        return null;
    }
}
