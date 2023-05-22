package geometries;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * Geometry interface represents a geometry in 3D Cartesian coordinate system
 */
public abstract class Geometry extends Intersectable {
    /**
     * The emission color of the geometry.
     */
    protected Color emission=Color.BLACK;

    /**
     * Getter for the emission color of the geometry.
     * @return The emission color of the geometry.
     */
    public Color getEmission(){return this.emission;}
    /**
     * Setter for the emission color of the geometry.
     * @param emission The emission color of the geometry.
     * @return The geometry.
     */
    public Geometry setEmission(Color emission){
        this.emission=emission;
        return this;
    }
    /**
     * Calculate the normal vector to the geometry at the specified point.
     *
     * @param point The point on the geometry shape.
     * @return The normal vector to the geometry at the specified point.
     */
    public abstract Vector getNormal(Point point);
}
