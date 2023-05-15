package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * Abstract class for ray tracing
 *
 * @author Avidan and Ziv
 */
public abstract class RayTracerBase {
    // scene to render
    protected Scene scene;

    /**
     * ctor for RayTracerBase from scene
     *
     * @param scene
     */
    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    /**
     * Abstract function to trace ray
     *
     * @param ray
     * @return color of the ray
     */
    abstract public Color traceRay(Ray ray);


}
