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

    private static final Double3 INITIAL_K = Double3.ONE;
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final Double3 MIN_CALC_COLOR_K = new Double3(0.001);


    /**
     * ctor for basic implementation of the ray tracer
     *
     * @param scene the scene to trace
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    /**
     * return the point of the closest intersection
     *
     * @param ray the ray that intersect the scene
     * @return the point of the closest intersection
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        return ray.findClosestGeoPoint(this.scene.geometries.findGeoIntersections(ray));
    }

    /**
     * calculate the transparency of the point by the light source
     *
     * @param gp          the point to check
     * @param l           the light source
     * @param n           the normal vector
     * @param lightSource the light source
     * @return the transparency of the point
     */
    private Double3 transparency(GeoPoint gp, Vector l, Vector n, LightSource lightSource) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(gp.point, lightDirection, n);
        Point point = lightRay.getP0();
        List<GeoPoint> intersections = this.scene.geometries.findGeoIntersections(lightRay);
        if (intersections == null) return Double3.ONE;

        double distToLight = lightSource.getDistance(point);
        Double3 ktr = Double3.ONE;
        for (GeoPoint pointIntersect : intersections) {
            if (alignZero(distToLight - point.distance(pointIntersect.point)) > 0) {
                ktr = ktr.product(pointIntersect.geometry.getMaterial().kT);
                if (ktr.lowerThan(MIN_CALC_COLOR_K)) return Double3.ZERO;
            }
        }
        return ktr;
    }

    @Override
    public Color traceRay(Ray ray) {
        GeoPoint intersect = findClosestIntersection(ray);
        return intersect == null ? this.scene.background
                : this.calcColor(intersect, ray);
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
        return calcColor(intersection, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(this.scene.ambientLight.getIntensity());
    }

    /**
     * Calculates the color of the given geometry point and ray
     *
     * @param gp    intersection point on the surface of geometry to calculate its color
     * @param ray   the ray that intersected the geometry
     * @param level the level of the recursion
     * @param k     the transparency of the point
     * @return The resulting color after calculating the local effects.
     */
    private Color calcColor(GeoPoint gp, Ray ray, int level, Double3 k) {
        Vector v = ray.getDir();
        Vector n = gp.geometry.getNormal(gp.point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) return Color.BLACK;

        Color color = calcLocalEffects(gp, v, n, nv, k);
        return 1 == level ? color
                : color.add(calcGlobalEffects(gp, v, n, nv, level, k));
    }

    /**
     * Calculates the local effects of the given geometry point and ray
     *
     * @param gp    intersection point on the surface of geometry to calculate the local effects
     * @param v     the direction of the ray that intersected the geometry
     * @param n     normal to the geometry surface at the intersection point
     * @param vn    dot-product of (v,n)
     * @param k     the transparency of the point
     * @param level the level of the recursion
     * @return The resulting color after calculating the local effects.
     */
    private Color calcGlobalEffects(GeoPoint gp, Vector v, Vector n, double vn, int level, Double3 k) {
        Material material = gp.geometry.getMaterial();
        Ray reflectedRay = constructReflectedRay(gp.point, v, n, vn);
        Ray refractedRay = constructRefractedRay(gp.point, v, n);
        return calcGlobalEffect(reflectedRay, level, material.kR, k).
                add(calcGlobalEffect(refractedRay, level, material.kT, k));
    }

    private Color calcGlobalEffect(Ray ray, int level, Double3 kx, Double3 k) {
        Double3 kkx = k.product(kx);
        if (kkx.lowerThan(MIN_CALC_COLOR_K)) return Color.BLACK;
        GeoPoint point = findClosestIntersection(ray);
        return (point == null ? scene.background : calcColor(point, ray, level - 1, kkx)).scale(kx);
    }

    /**
     * Constructs the reflected ray
     *
     * @param point the point to reflect
     * @param v     the direction of the ray that intersected the geometry
     * @param n     the normal vector
     * @param vn    dot-product of (v,n)
     * @return the reflected ray
     */
    private Ray constructReflectedRay(Point point, Vector v, Vector n, double vn) {
        Vector r = v.subtract(n.scale(vn).scale(2));
        return new Ray(point, r, n);
    }

    /**
     * Constructs the refracted ray
     *
     * @param point the point to refract
     * @param v     the direction of the ray that intersected the geometry
     * @param n     the normal vector
     * @return the refracted ray
     */
    private Ray constructRefractedRay(Point point, Vector v, Vector n) {
        return new Ray(point, v, n);
    }

    /**
     * Calculates the local effects of the given geometry point and ray
     *
     * @param gp intersection point on the surface of geometry to calculate the local effects
     * @param v  the direction of the ray that intersected the geometry
     * @param n  normal to the geometry surface at the intersection point
     * @param vn dot-product of (v,n)
     * @return The resulting color after calculating the local effects.
     */
    private Color calcLocalEffects(GeoPoint gp, Vector v, Vector n, double vn, Double3 k) {
        Color color = gp.geometry.getEmission();
        Material mat = gp.geometry.getMaterial();
        for (LightSource lightSource : this.scene.lights) {
            Vector l = lightSource.getL(gp.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * vn > 0) { // sign(nl) == sing(vn)
                Double3 ktr = transparency(gp, l, n, lightSource);
                if (!(ktr.product(k).lowerThan(MIN_CALC_COLOR_K))) {
                    Color iL = lightSource.getIntensity(gp.point).scale(ktr);
                    color = color.add(iL.scale(calcDiffusive(mat, nl).add(calcSpecular(mat, n, l, nl, v))));
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
        return vr >= 0 ? Double3.ZERO // view from direction opposite to r vector
                : material.kS.scale(Math.pow(-vr, material.nShininess));
    }
}
