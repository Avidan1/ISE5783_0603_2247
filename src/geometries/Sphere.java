package geometries;

import primitives.Double3;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;


/**
 * The Sphere class represents a sphere in a 3D Cartesian coordinate system.
 * This class extends the RadialGeometry class and adds a center point to define the position of the sphere.
 */
public class Sphere extends RadialGeometry {
    /**
     * The center point of the sphere.
     */
    private final Point center;

    /**
     * Construct a new Sphere object with the given center point and radius.
     *
     * @param center the center point of the sphere
     * @param radius the radius of the sphere
     */
    public Sphere(Point center, double radius) {
        super(radius);
        this.center = center;
        this.bbox = new AABB(center.add(new Vector(new Double3(-radius))), center.add(new Vector(new Double3(radius))), center);
    }

    @Override
    public Vector getNormal(Point point) {
        //calculate the normal vector to the sphere
        return point.subtract(this.center).normalize();
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray,double maxDistance) {
        // if the ray starts at the center of the sphere
        if (ray.getP0().equals(this.center)) {
            return List.of(new GeoPoint(this, this.center.add(ray.getDir().scale(this.radius))));
        }
        Point point = ray.getP0();
        Vector dirVector = ray.getDir();
        Vector p0O = this.center.subtract(point);
        double tM = alignZero(dirVector.dotProduct(p0O));
        double d2 = alignZero(p0O.dotProduct(p0O) - tM * tM);
        // if the ray starts outside the sphere and goes away from it
        if (alignZero(d2 - this.radius2) >= 0)
            return null;
        double tH = alignZero(Math.sqrt(this.radius2 - d2));

        double t2 = alignZero(tM + tH);
        // if the father point is before the ray head - there are not intersections
        if (t2 <= 0) return null;

        double t1 = alignZero(tM - tH);
        return t1 <= 0 //
                ? List.of(new GeoPoint(this, ray.getPoint(t2))) //
                : List.of(new GeoPoint(this, ray.getPoint(t1)), new GeoPoint(this, ray.getPoint(t2)));
    }
}
