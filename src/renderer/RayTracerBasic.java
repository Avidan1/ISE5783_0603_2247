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
     *  return the point of the closest intersection
     * @param ray the ray that intersect the scene
     * @return the point of the closest intersection
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        return ray.findClosestGeoPoint(scene.geometries.findGeoIntersections(ray));
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
    private Double3 transparency(GeoPoint gp, Vector l, Vector n,LightSource lightSource) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(gp.point, lightDirection,n);
        Point point = lightRay.getP0();
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
        if (intersections == null) return Double3.ONE;
        Double3 ktr = Double3.ONE;
        for (GeoPoint pointIntersect : intersections) {
            if (lightSource.getDistance(point) > point.distance(pointIntersect.point))
            {
                ktr = ktr.product(pointIntersect.geometry.getMaterial().kT);
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
                .add(scene.ambientLight.getIntensity());
    }

    private Color calcColor(GeoPoint gp, Ray ray, int level, Double3 k) {
        Color color = calcLocalEffects(gp, ray, k);
        return 1 == level ? color
                : color.add(calcGlobalEffects(gp, ray, level, k));
    }

    /**
     * Calculates the local effects of the given geometry point and ray
     *
     * @param gp  intersection point on the surface of geometry to calculate the local effects
     * @param ray the ray that intersected the geometry
     * @param k   the transparency of the point
     * @param level the level of the recursion
     * @return The resulting color after calculating the local effects.
     */
    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
        Color color = Color.BLACK;
        Material material = gp.geometry.getMaterial();
        Double3 kr = material.kR, kkr = k.product(kr);
        // in the recursion, the kkr will be the kkr of the previous level
        // so  the impact of the reflection decreases with the level
        Vector v = gp.geometry.getNormal(gp.point);
        Ray reflectedRay = constructReflectedRay(v, gp.point, ray);
        GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
        if (!(kkr.lowerThan(MIN_CALC_COLOR_K)) && reflectedPoint != null) {
            color = color.add(calcColor(reflectedPoint, reflectedRay, level - 1, kkr).scale(kr));
        }
        Double3 kt = material.kT, kkt = k.product(kt);
        Ray refractedRay = constructRefractedRay(v, gp.point, ray);
        GeoPoint refractedPoint = findClosestIntersection(refractedRay);
        if (!(kkt.lowerThan(MIN_CALC_COLOR_K)) && refractedPoint != null) {
            color = color.add(calcColor(refractedPoint, refractedRay, level - 1, kkt).scale(kt));
        }
        return color;
    }

    private Ray constructReflectedRay(Vector n, Point point, Ray inRay) {
        Vector sub = n.scale(inRay.getDir().dotProduct(n)).scale(2);
        Vector dir = inRay.getDir().subtract(sub);
        return new Ray(point, dir, n);
    }

    private Ray constructRefractedRay(Vector n, Point point, Ray inRay) {
        return new Ray(point, inRay.getDir(), n);
    }

    /**
     * Calculates the local effects of the given geometry point and ray
     *
     * @param gp  intersection point on the surface of geometry to calculate the local effects
     * @param ray the ray that intersected the geometry
     * @return The resulting color after calculating the local effects.
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray, Double3 k) {//TODO: Understand the code !
        if (gp == null) return Color.BLACK;
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
                Double3 ktr = transparency(gp, l, n, lightSource);
                if (!(ktr.product(k).lowerThan(MIN_CALC_COLOR_K))){
                    Color iL = lightSource.getIntensity(gp.point).scale(ktr);
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
