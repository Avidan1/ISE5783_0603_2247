package geometries;

import primitives.Double3;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static java.awt.geom.CubicCurve2D.iszero;
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
    public List<Point> findIntsersections(Ray ray) {
        Point p0 = ray.getP0();
        Vector v = ray.getDir();

        if (p0.equals(center)) {//if the point is on the center of the sphere
            return List.of(p0.add(v.scale(radius)));
        }

        Vector u = center.subtract(p0);

        double tm = alignZero(u.dotProduct(v));
        double dSquared = isZero(tm) ? u.lengthSquared() : u.lengthSquared() - tm * tm;
        double d = alignZero(Math.sqrt(dSquared));
        if (alignZero(d - radius) >= 0) {//if the distance between the ray and the center of the sphere is bigger than the radius
            return null; // no intersections the ray is above the sphere
        }
        double thSquared = alignZero(radius * radius - dSquared);
        if (thSquared <= 0) {
            return null;
        }

        double th = Math.sqrt(thSquared);
        double t1 = alignZero(tm - th);
        double t2 = alignZero(tm - th);

        if (t1 >= 0 && t2 >= 0) {//if t1 and t2 are negative or zero the didnt hit the sphere
            Point point1 = p0.add(v.scale(t1));
            Point point2 = p0.add(v.scale(t2));
            return List.of(point1, point2);
        }
        if (t1 > 0) {//if  only t1 is positive the ray hit the sphere in one point
            Point point1 = p0.add(v.scale(t1));
            return List.of(point1);
        }
        if (t2 > 0) {//if  only t2 is positive the ray hit the sphere in one point
            Point point2 = p0.add(v.scale(t2));
            return List.of(point2);
        }
        return null;
    }
