package geometries;

import primitives.*;

/**
 * Geometry interface represents a geometry in 3D Cartesian coordinate system
 */
public interface Geometry {
     /**
      * Calculate the normal vector to the geometry at the specified point.
      *
      * @param point The point on the geometry shape.
      * @return The normal vector to the geometry at the specified point.
      */
     Vector getNormal(Point point);
}
