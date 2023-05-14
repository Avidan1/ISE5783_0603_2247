package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

public class RayTracerBasic extends RayTracerBase{

    /**
     * ctor for RayTracerBasic
     * use super to call RayTracerBase ctor
     * @param scene
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
        try { // todo is this try-catch necessary or just throw exception?
            throw new Exception ("RayTracerBasic");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Color traceRay(Ray ray) {
        return null;
    }
}
