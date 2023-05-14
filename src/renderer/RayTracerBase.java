package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;
import
/**
 * Abstract class for ray tracing
 */
public abstract class RayTracerBase {
    // scene to render
    protected Scene scene;
    /**
     * ctor
     * @param scene
     */
    public RayTracerBase(Scene scene){
        this.scene = scene;
    }
    /**
     * Abstract function to trace ray
     * @param ray
     * @return color
     */
    abstract public Color traceRay(Ray ray);




}
