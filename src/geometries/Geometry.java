package geometries;

import primitives.Point;
import primitives.Vector;
/**
 * Geometry interface represents a geometry in 3D Cartesian coordinate system
 */
public interface Geometry {
     public Vector getNormal(Point point);
}
