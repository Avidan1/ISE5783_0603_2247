package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

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
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        return null;
    }

}
