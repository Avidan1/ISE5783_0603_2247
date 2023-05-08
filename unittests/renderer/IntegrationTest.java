package renderer;

import geometries.Plane;
import geometries.Geometry;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * *  testing the integration of camera and geometries
 *
 * @ author ziv and avidan
 **/
public class IntegrationTest {
    static Point zeroPoint = new Point(0, 0, 0);

    Camera camera = new Camera(zeroPoint, new Vector(0, 1, 0), new Vector(0, 0, -1));

    int rayIntersectionCount(Geometry geometry, Camera camera) {
        int count = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                count = count + geometry.findIntersections(camera.constructRay(3, 3, i, j)).size();
            }
        }
        return count;
    }

    /**
     * *  testing the integration of camera and sphere
     **/
    @Test
    void sphereIntegrationTest() {
        camera.setVPSize(3d, 3d);
        camera.setVPDistance(1);
        Sphere sphere = new Sphere(new Point(0, 0, -3), 1);
        //TC01: 2 intersection points
        assertEquals(2, rayIntersectionCount(sphere, camera), "wrong number of intersections");
        //TC02: 18 intersection points
        Sphere sphere18 = new Sphere(new Point(0, 0, -2.5), 2.5);
        Camera camera18 = new Camera(new Point(0, 0, 0.5), new Vector(0, 1, 0), new Vector(0, 0, -1));
        camera18.setVPSize(3d, 3d);
        camera18.setVPDistance(1);
        assertEquals(18, rayIntersectionCount(sphere18, camera18), "wrong number of intersections");
        //TC03: 10 intersection points
        Sphere sphere10 = new Sphere(new Point(0, 0, -2), 2);
        assertEquals(10, rayIntersectionCount(sphere10, camera18), "wrong number of intersections");
        //TC04: 9 intersection points
        Sphere sphere9 = new Sphere(new Point(0, 0, -1), 4);
        assertEquals(9, rayIntersectionCount(sphere9, camera18), "wrong number of intersections");
        //TC05: 0 intersection points
        Sphere sphere0 = new Sphere(new Point(0, 0, 1), 0.5);
        assertEquals(0, rayIntersectionCount(sphere0, camera18), "wrong number of intersections");

    }

    // testing the integration of camera and triangle
    @Test
    void triangleIntegrationTest() {
        Camera camera = new Camera(zeroPoint, new Vector(0, 0, -1), new Vector(0, -1, 0)).setVPDistance(1).setVPSize(3, 3);
        //TC01: 1 intersection point
        assertEquals(1, rayIntersectionCount(new Triangle(new Point(0, 1, -2), new Point(1, -1, -2), new Point(-1, -1, -2)), camera), "wrong number of intersections");

        //TC02: 2 intersection points
        assertEquals(2, rayIntersectionCount(new Triangle(new Point(1, -1, -2), new Point(-1, -1, -2), new Point(0, 20, -2)), camera), "wrong number of intersections");

    }
    //

    // testing the integration of camera and plane
    @Test
    void planeIntegrationTest() {
        //TC01 9 intersection points parallel to the view plane
        Camera camera = new Camera(zeroPoint, new Vector(0, 1, 0), new Vector(0, 0, -1));
        camera.setVPSize(3d, 3d);
        camera.setVPDistance(1);
        assertEquals(9, rayIntersectionCount(new Plane(new Point(0, 0, -1), new Vector(0, 0, 2)), camera), "wrong number of intersections");
        //TC02 9 intersection points not parallel to the view plane
        assertEquals(9, rayIntersectionCount(new Plane(new Point(0, 0, -1), new Vector(0, 2, 4)), camera), "wrong number of intersections");
        //TC03 6 intersection points not parallel to the view plane
        assertEquals(6, rayIntersectionCount(new Plane(new Point(0, 0, -1), new Vector(0, 2, 1)), camera), "wrong number of intersections");
        //TC04 0 intersection points not parallel to the view plane
        assertEquals(0, rayIntersectionCount(new Plane(new Point(0, 0, -1), new Vector(0, 2, 0.5)), camera), "wrong number of intersections");
    }
}
