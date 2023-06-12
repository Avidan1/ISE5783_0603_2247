package renderer;

import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import primitives.*;
import scene.Scene;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * Basic ray tracer class extends RayTracerBase
 * implements RayTracerBase
 *
 * @author Avidan and Ziv
 */
public class RayTracerBasic extends RayTracerBase {

    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final Double3 MIN_CALC_COLOR_K = new Double3(0.001);

    /**
     * The delta for the shadow rays movement from the axis
     */
    private static final double DELTA = 0.1;


    /**
     * checks if the point is shaded
     *
     * @param gp the point to check
     * @param l  the light source
     * @param n  the normal
     * @param nl the dot product of the normal and the light source
     * @return true if the point is unshaded
     */
    private boolean unshaded(GeoPoint gp, Vector l, Vector n, double nl) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Vector epsVector = n.scale(nl < 0 ? DELTA : -DELTA);
        Point point = gp.point.add(epsVector);
        Ray lightRay = new Ray(point, lightDirection);
        var intersections = scene.geometries.findGeoIntersections(lightRay);
        return intersections == null;

    }

    /**
     * ctor for basic implementation of the ray tracer
     *
     * @param scene the scene to trace
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    private boolean unshaded(GeoPoint gp, Vector l, Vector n, double nl, LightSource lightSource) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Vector epsVector = n.scale(nl < 0 ? DELTA : -DELTA);
        Point point = gp.point.add(epsVector);
        Ray lightRay = new Ray(point, lightDirection);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
        if (intersections == null) return true;
        for (GeoPoint pointIntersect : intersections) {
            double ray = lightSource.getDistance(pointIntersect.point) - lightSource.getDistance(point);
            if (ray <= 0) return false;
        }
        return true;
    }

    @Override
    public Color traceRay(Ray ray) {
        var intersections = scene.geometries.findGeoIntersections(ray);
        return intersections == null ? this.scene.background
                : this.calcColor(ray.findClosestGeoPoint(intersections), ray);
        // if there are no intersections return the background color;
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
    private Color calcLocalEffects(GeoPoint gp, Ray ray) {//TODO: Understand the code !
        Color color = gp.geometry.getEmission();
        Vector v = ray.getDir();
        Vector n = gp.geometry.getNormal(gp.point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) return color;
        Material mat = gp.geometry.getMaterial();
        for (LightSource lightSource : this.scene.lights) {
            Vector l = lightSource.getL(gp.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sing(nv)
                if (unshaded(gp, l, n, nl, this.scene.lights.get(0))) {
                    Color iL = lightSource.getIntensity(gp.point);
                    color = color.add(iL.scale(calcDiffusive(mat, nl)),
                            iL.scale(calcSpecular(mat, n, l, nl, v)));
                }
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
        return mat.kD.scale(nl < 0 ? -nl : nl);
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
        Vector r = l.subtract(n.scale(nl).scale(2)).normalize();
        double vr = alignZero(v.dotProduct(r));
        if (vr >= 0) return Double3.ZERO; // view from direction opposite to r vector
        return material.kS.scale(Math.pow(-vr, material.nShininess));
    }
}
