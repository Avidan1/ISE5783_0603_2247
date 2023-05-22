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
    /**
     * scene to render
     */
    protected final Scene scene;

    /**
     * Initialize ray tracer
     *
     * @param scene scene to trace
     */
    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    /**
     * Traces the scene with a ray to calculate its color
     *
     * @param ray to trace through the scene
     * @return color of the ray
     */
    abstract public Color traceRay(Ray ray);


}
