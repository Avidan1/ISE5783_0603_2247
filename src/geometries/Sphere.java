package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

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
    Sphere(Point center, double radius) {
        super(radius);
        this.center = center;
    }

    @Override
    public Vector getNormal(Point point) {
        //calculate the normal vector to the sphere
        return point.subtract(center).normalize();
    }

    @Override
    public List<Point> findIntersections(Ray ray) {

        if (ray.getP0().equals(this.center)) {
            return List.of(this.center.add(ray.getDir().scale(this.radius)));
        }
        Point point = ray.getP0();
        Vector dir_vector = ray.getDir();
        Vector p0_o = this.center.subtract(point);
        double t_m = alignZero(dir_vector.dotProduct(p0_o));
        double d = alignZero(Math.sqrt(p0_o.dotProduct(p0_o) - t_m * t_m));

        // if the ray starts outside the sphere and goes away from it
        if (d >= this.radius)
            return null;

        double t_h = alignZero(Math.sqrt(this.radius * this.radius - d * d));
        double t_1 = alignZero(t_m + t_h);
        double t_2 = alignZero(t_m - t_h);

        if (t_1 <= 0 && t_2 <= 0)
            return null;

        if (t_1 > 0 && t_2 <= 0)
            return List.of(ray.getPoint(t_1));

        if (t_1 <= 0 && t_2 > 0)
            return List.of(ray.getPoint(t_2));

        if (t_1 > 0 && t_2 > 0)
            return List.of(ray.getPoint(t_1), ray.getPoint(t_2));

        return null;

    }
}
