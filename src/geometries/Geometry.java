package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;

/**
 * Geometry interface represents a geometry in 3D Cartesian coordinate system
 */
public abstract class Geometry extends Intersectable {
    //***************fields**************//
    /**
     * The emission color of the geometry.
     */
    protected Color emission = Color.BLACK;

    /**
     * Getter for the emission color of the geometry.
     *
     * @return The emission color of the geometry.
     */
    public Color getEmission() {
        return this.emission;
    }

    /**
     * Getter for the material of the geometry.
     *
     * @return The material of the geometry.
     */
    public Material getMaterial() {
        return this.material;
    }

    /**
     * setter for the material of the geometry.
     *
     * @param material The material of the geometry to set.
     * @return The geometry object with the updated material.
     */
    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

    private Material material = new Material();

    //**************setters**************//
    /**
     * Setter for the emission color of the geometry.
     *
     * @param emission The emission color of the geometry.
     * @return The Geometry object.
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }
    //**************operations***********//

    /**
     * Calculate the normal vector to the geometry at the specified point.
     *
     * @param point The point on the geometry shape.
     * @return The normal vector to the geometry at the specified point.
     */
    public abstract Vector getNormal(Point point);
}
