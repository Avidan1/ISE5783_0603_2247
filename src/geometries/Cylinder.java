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
     * @param radius  The radius of the cylinder.
     * @param height  The height of the cylinder.
     */
    public Cylinder(Ray axisRay, double radius, double height) {
        super(axisRay, radius);
        this.height = height;
    }

    @Override
    public Vector getNormal(Point p) {
    /*    //check if p is on the base (p - p0).length <= radius
        //this is true because we assumed that the point p is on the origin
        if (p.equals(this.axisRay.getP0()) || p.subtract(this.axisRay.getP0()).length() <= this.radius)
            return this.axisRay.getDir().scale(-1);
        //check if p is on the top (p - topCenterPoint (p0+height*dir)).length <= radius
        Point topCenter = this.axisRay.getP0().add(this.axisRay.getDir().scale(height));
        if (p.equals(topCenter) || p.subtract(topCenter).length() <= this.radius)
            return this.axisRay.getDir();
        //else it is on the round surface
        Point O = this.axisRay.getP0()
                .add((this.axisRay.getDir())
                        .scale(p.subtract(this.axisRay.getP0())
                                .dotProduct(this.axisRay.getDir())));
        return p.subtract(O).normalize();*/
        return null;
    }

    @Override
    public List<Point> findIntersections(Ray ray) {

        // TODO Auto-generated method stub
        return null;
    }
}
