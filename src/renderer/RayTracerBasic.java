package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

/**
 * Basic ray tracer class extends RayTracerBase
 * implements RayTracerBase
 *
 * @author Avidan and Ziv
 */
public class RayTracerBasic extends RayTracerBase {

    /**
     * ctor for basic implementation of the ray tracer
     * @param scene the scene to trace
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    @Override
    public Color traceRay(Ray ray) {
        return this.scene.geometries.findIntersections(ray) == null ? this.scene.background
                : this.calcColor(ray.findClosestPoint(this.scene.geometries.findIntersections(ray)));
    }

    /**
     * Computes the color of the intersection point using the Phong reflection model.
     *
     * @param point intersection point on the surface of geometry to calculate its color
     * @return color
     */
    private Color calcColor(Point point) {
        return this.scene.ambientLight.getIntensity();
    }
}
