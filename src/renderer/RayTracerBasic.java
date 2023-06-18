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
    /**
     * The maximum level of recursion
     */
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    /**
     * The minimum k value for the color calculation
     */
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
     * checks if the point is shaded
     *
     * @param gp          the point to check
     * @param l           the light source
     * @param n           the normal
     * @param lightSource the light source
     * @return true if the point is unshaded
     */
    private boolean unshaded(GeoPoint gp, LightSource lightSource, Vector l, Vector n) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(gp.point, lightDirection, n);
        Point point = lightRay.getP0();
        List<Point> intersections = scene.geometries.findIntersections(lightRay);
        if (intersections == null) return true;
        if (gp.geometry.getMaterial().kT != Double3.ZERO)
            for (Point element : intersections) {
                if (lightSource.getDistance(point) > point.distance(element))
                    return false;
            }
        return true;
    }

    @Override
    public Color traceRay(Ray ray) {
        GeoPoint closestPoint = findClosestIntersection(ray);
        return closestPoint == null ? scene.background : calcColor(closestPoint, ray);
        // if there are no intersections return the background color;
    }

    /**
     * Constructs the reflected ray
     *
     * @param gp  the intersection point
     * @param ray the ray that intersected the geometry
     * @return The reflected ray
     */
    private Ray constructReflected(GeoPoint gp, Ray ray,Material material) {
        Vector v = ray.getDir();
        Vector n = gp.geometry.getNormal(gp.point);
        double vn = v.dotProduct(n);
        if (vn == 0) return null;
        Vector r = v.subtract(n.scale(2 * vn));
        return new Ray(gp.point, r, n);
    }

    /**
     * Constructor for the reflected ray
     *
     * @param gp  the intersection point
     * @param ray the ray that intersected the geometry
     * @return The refracted ray
     */
    private Ray constructRefracted(GeoPoint gp, Ray ray,Vector n) {
        return new Ray(gp.point, ray.getDir(), n); //use the constructor with the normal for moving the head
    }

    private Color calcColor(GeoPoint geoPoint, Ray ray, int level, double k) {
        Color color = geoPoint.geometry.getEmission()
                .add(calcLocalEffects(geoPoint, ray, k));

        return 1 == level ? color : color.add(calcGlobalEffects(geoPoint, ray, level, k));
    }
    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
        Color color = Color.BLACK;
        Material material = gp.geometry.getMaterial();
        Double3 kr = material.kR;
        Double3 kkr = k.product(kr); //in each recursive iteration the impact of the reflection decreases
        Vector n = gp.geometry.getNormal(gp.point);
        if (!kkr.lowerThan(MIN_CALC_COLOR_K)) {
            List<Ray> reflectedRays = constructReflected(gp, ray, material.Glossy);
            Color tempColor1 = Color.BLACK;
            // each ray
            for (Ray reflectedRay : reflectedRays) {
                GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
                tempColor1 = tempColor1.add(reflectedPoint == null ?
                        Color.BLACK : calcColor(reflectedPoint, reflectedRay, level - 1, kkr).scale(kr));
            }

            color = color.add(tempColor1.reduce(reflectedRays.size()));
        }
        Double3 kt = material.kT;
        Double3 kkt = k.product(kt); //in each recursive iteration the impact of the refraction decreases
        if (!kkt.lowerThan(MIN_CALC_COLOR_K)) {
            List<Ray> refractedRays = constructRefracted(gp, ray, n);
            Color tempColor2 = Color.BLACK;
            //calculate for each ray
            for (Ray refractedRay : refractedRays) {
                GeoPoint refractedPoint = findClosestIntersection(refractedRay);
                tempColor2 = tempColor2.add(refractedPoint == null ?
                        Color.BLACK : calcColor(refractedPoint, refractedRay, level - 1, kkt).scale(kt));
            }

            color = color.add(tempColor2.reduce(refractedRays.size()));
        }
        return color;
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
                if (unshaded(gp,this.scene.lights.get(0), l, n)) {
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

    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> intersections = this.scene.geometries.findGeoIntersections(ray);
        return intersections == null ? null : ray.findClosestGeoPoint(intersections);
    }
}
