package geometries;

import primitives.Point;
import primitives.Vector;
public class Plane implements Geometry{
Point q0;
Vector normal;

    public Vector getNormal(Point point) {
        return null;
    }
    public Vector getNormal() {
        return normal;
    }
    public Plane(Point q0, Vector normal) {
        this.q0 = q0;
        this.normal = normal.normalize();
    }
    public Plane (Point p1, Point p2, Point p3){ // TODO: check if this what he meant
        normal=getNormal(p1);
        q0=p2;
      /*  Vector v1 = p2.subtract(p1);
        Vector v2 = p3.subtract(p1);
        normal = v1.crossProduct(v2).normalize();
        q0 = p1;*/
    }
    public Point getQ0() {
        return q0;
    }
}
