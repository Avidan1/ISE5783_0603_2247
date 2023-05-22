package renderer;

import primitives.*;
import lighting.LightSource;
import scene.Scene;
import geometries.Intersectable.GeoPoint;
import static primitives.Util.alignZero;

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
     * @return the color of the intersection point
     */
    private Color calcColor(GeoPoint point) {
        return this.scene.ambientLight.getIntensity().add(point.geometry.getEmission());
    }

    /**
     * Computes the color of the intersection point using the Phong reflection model.
     *
     * @param intersection intersection point on the surface of geometry to calculate its color
     * @param ray          the ray that intersected the geometry
     * @return the color of the intersection point
     */
    private Color calcColor(GeoPoint intersection, Ray ray) {
        return this.scene.ambientLight.getIntensity().add(calcLocalEffects(intersection, ray));
    }

    /**
     * Calculates the local effects of the given geometry point and ray
     *
     * @param gp  intersection point on the surface of geometry to calculate the local effects
     * @param ray the ray that intersected the geometry
     * @return The resulting color after calculating the local effects.
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray) {
        Color color = gp.geometry.getEmission();
        Vector v = ray.getDir();
        Vector n = gp.geometry.getNormal(gp.point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) return color;
        Material mat = gp.geometry.getMaterial();
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(gp.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sing(nv)
                Color iL = lightSource.getIntensity(gp.point);
                color = color.add(iL.scale(calcDiffusive(mat, nl)),
                        iL.scale(calcSpecular(mat, n, l, nl, v)));
            }
        }
        return color;
    }

    /**
     * Calculates the diffusive effect of the given material and normal-lights angle.
     *
     * @param mat the material of the geometry
     * @param nl  The normal-lights angle to calculate the diffuse reflection for.
     * @return The resulting diffusive effect.
     */
    private Double3 calcDiffusive(Material mat, double nl) {
        return mat.kD.scale(Math.abs(nl));
    }

    /**
     * Calculates the specular reflection of the given material, normal, light, normal-lights angle,
     * and viewer direction.
     *
     * @param material The material to calculate the specular reflection for.
     * @param n        The normal of the geometry point.
     * @param l        The direction of the light.
     * @param nl       The normal-lights angle.
     * @param v        The viewer direction.
     * @return The calculated specular reflection.
     */

    private Double3 calcSpecular(Material material, Vector n, Vector l, double nl, Vector v) {
        Vector r = l.subtract(n.scale(l.dotProduct(n)).scale(2)).normalize();
        return material.kS.scale(Math.pow(Math.max(0, v.dotProduct(r) * (-1)), material.nShininess));
    }
}
