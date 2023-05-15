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
     * ctor for RayTracerBasic
     * use super to call RayTracerBase ctor
     *
     * @param scene
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
        //throw new UnsupportedOperationException("RayTracerBasic is not implemented yet");
    }

    @Override
    public Color traceRay(Ray ray) {
        return this.scene.geometries.findIntersections(ray) == null ? this.scene.background : this.calcColor(ray.findClosestPoint(this.scene.geometries.findIntersections(ray)));
    }

    /**
     * Computes the color of the intersection point using the Phong reflection model.
     *
     * @param point
     * @return color
     */
    private Color calcColor(Point point) {
        return this.scene.ambientLight.getIntensity();
    }
}
