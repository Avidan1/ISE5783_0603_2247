package geometries;

import primitives.Point;
import primitives.Vector;
import primitives.Ray;

import java.util.List;

import static primitives.Util.isZero;

/**
 * Tube class represents a tube in 3D Cartesian coordinate system
 */
public class Tube extends RadialGeometry {
    /**
     * The axis ray of the tube.
     */
    protected final Ray axisRay;

    /**
     * Constructs a new Tube object with the specified axis ray and radius.
     *
     * @param axisRay The axis ray of the tube.
     * @param radius  The radius of the tube.
     */
    Tube(Ray axisRay, double radius) {
        super(radius);
        this.axisRay = axisRay;
    }

    @Override
    public Vector getNormal(Point point) {
        double t = point.subtract(this.axisRay.getP0()).dotProduct(this.axisRay.getDir());
        Point o = axisRay.getPoint(t);
        return point.subtract(o).normalize();
    }


    @Override
    public List<Point> findIntersections(Ray ray) {
        return null;
    }

}
