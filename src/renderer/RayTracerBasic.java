package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

public class RayTracerBasic extends RayTracerBase {

    /**
     * ctor for RayTracerBasic
     * use super to call RayTracerBase ctor
     *
     * @param scene
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
        try { // todo is this try-catch necessary or just throw exception?
            throw new Exception("RayTracerBasic");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Color traceRay(Ray ray) {
        return this.calcColor(ray.findClosestPoint(this.scene.geometries.findIntersections(ray)));
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
