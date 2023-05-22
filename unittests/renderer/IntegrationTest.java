package renderer;

import geometries.Geometry;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * *  testing the integration of camera and geometries
 *
 * @ author ziv and avidan
 **/
public class IntegrationTest {
    // use the same camera for faw tests
    final Camera camera = new Camera(new Point(0, 0, 0), new Vector(0, 0, -1), new Vector(0, 1, 0)).setVPSize(3d, 3d).setVPDistance(1);

    /**
     * *  calculate the number of intersections between the camera and the geometries
     **/

    int rayIntersectionCount(Geometry geometry, Camera camera) {

        int count = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                List<Point> points = geometry.findIntersections(camera.constructRay(3, 3, i, j));
                if (points != null)
                    count += points.size();
            }
        }
        return count;
    }

    /**
     * * Test method for {@link renderer.Camera#constructRay(int, int, int, int)}.
     * *  testing the integration of camera and sphere
     **/
    @Test
    void sphereIntegrationTest() {
        Camera camera1 = new Camera(new Point(0, 0, 0.5), new Vector(0, 0, -1), new Vector(0, 1, 0)).setVPSize(3d, 3d).setVPDistance(1);
        Sphere sphere = new Sphere(new Point(0, 0, -3), 1);
        //TC01: 2 intersection points
        assertEquals(2, rayIntersectionCount(sphere, camera), "wrong number of intersections");
        //TC02: 18 intersection points
        Sphere sphere18 = new Sphere(new Point(0, 0, -2.5), 2.5);
        assertEquals(18, rayIntersectionCount(sphere18, camera1), "wrong number of intersections");
        //TC03: 10 intersection points
        Sphere sphere10 = new Sphere(new Point(0, 0, -2), 2);
        assertEquals(10, rayIntersectionCount(sphere10, camera1), "wrong number of intersections");
        //TC04: 9 intersection points
        Sphere sphere9 = new Sphere(new Point(0, 0, -1), 4);
        assertEquals(9, rayIntersectionCount(sphere9, camera1), "wrong number of intersections");
        //TC05: 0 intersection points
        Sphere sphere0 = new Sphere(new Point(0, 0, 1), 0.5);
        assertEquals(0, rayIntersectionCount(sphere0, camera1), "wrong number of intersections");

    }

    /**
     * Test method for {@link renderer.Camera#constructRay(int, int, int, int)}.
     * testing the integration of camera and triangle
     */
    @Test
    void triangleIntegrationTest() {
        //TC01: 1 intersection point
        assertEquals(1, rayIntersectionCount(new Triangle(new Point(0, 1, -2), new Point(1, -1, -2), new Point(-1, -1, -2)), camera), "wrong number of intersections");
        //TC02: 2 intersection points
        assertEquals(2, rayIntersectionCount(new Triangle(new Point(1, -1, -2), new Point(-1, -1, -2), new Point(0, 20, -2)), camera), "wrong number of intersections");

    }

    /**
     * Test method for {@link renderer.Camera#constructRay(int, int, int, int)}.
     * testing the integration of camera and plane
     */
    @Test
    void planeIntegrationTest() {
        //TC01 9 intersection points parallel to the view plane
        assertEquals(9, rayIntersectionCount(new Plane(new Point(0, 0, -1), new Vector(0, 0, 2)), camera), "wrong number of intersections");
        //TC02 9 intersection points not parallel to the view plane
        assertEquals(9, rayIntersectionCount(new Plane(new Point(0, 0, -1), new Vector(0, 2, 4)), camera), "wrong number of intersections");
        //TC03 6 intersection points not parallel to the view plane
        assertEquals(6, rayIntersectionCount(new Plane(new Point(0, 0, -1), new Vector(0, 2, 1)), camera), "wrong number of intersections");

    }
}
