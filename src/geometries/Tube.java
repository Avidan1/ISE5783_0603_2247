package geometries;

import primitives.Point;
import primitives.Vector;
import primitives.Ray;
/**
 * Tube class represents a tube in 3D Cartesian coordinate system
 */
public class Tube extends RadialGeometry {
    protected Ray axisRay;
    Tube(Ray axisRay, double radius){
        super(radius);
        this.axisRay=axisRay;
    }

    @Override
    public Vector getNormal(Point point) {
        return null;
    }
}
