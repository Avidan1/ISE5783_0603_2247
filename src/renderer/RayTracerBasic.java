package renderer;

import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;


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
        var intersections = scene.geometries.findGeoIntersections(ray);
        return intersections == null ? this.scene.background
                : this.calcColor(ray.findClosestGeoPoint(intersections));
    }

    /**
     * Computes the color of the intersection point using the Phong reflection model.
     *
     * @param point intersection point on the surface of geometry to calculate its color
     * @return color
     */
    private Color calcColor(GeoPoint point) {
        return this.scene.ambientLight.getIntensity().add(point.geometry.getEmission());
    }
}
