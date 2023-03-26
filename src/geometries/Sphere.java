package geometries;

import primitives.Point;
import primitives.Vector;
/**
 * Sphere class represents a sphere in 3D Cartesian coordinate system
 */
public class Sphere extends RadialGeometry {
    Point center;
    Sphere(Point center,double radius){
        super(radius);
        this.center=center;
    }

    @Override
    public Vector getNormal(Point point) {
        return null;
    }
}
