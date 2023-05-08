package renderer;

import geometries.Geometry;
import geometries.Sphere;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.Camera;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * *  testing the integration of camera and geometries
 *
 * @ author ziv and avidan
 **/
public class IntegrationTest {
    Camera camera = new Camera(new Point(0, 0, 0), new Vector(0, 1, 0), new Vector(0, 0, -1));

    int rayIntersectionLoop(Geometry geometry, Camera camera) {
        int width = 3;
        int height = 3;
        int count = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                count = count + geometry.findIntersections(camera.constructRay(width, height, i, j)).size();
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
        camera.setDistance(1);
        Sphere sphere = new Sphere(new Point(0, 0, -3), 1);
        //TC01: 2 intersection points
        assertEquals(2, sphere.findIntersections(camera.constructRay(3, 3, 0, 0)).size(), "wrong number of intersections");
        //TC02: 18 intersection points
        Sphere sphere18 = new Sphere(new Point(0, 0, -2.5), 2.5);
        Camera camera18 = new Camera(new Point(0, 0, 0.5), new Vector(0, 1, 0), new Vector(0, 0, -1));
        camera18.setVPSize(3d, 3d);
        camera18.setDistance(1);
        assertEquals(18, rayIntersectionLoop(sphere18, camera18), "wrong number of intersections");
        //TC03: 10 intersection points
        Sphere sphere10 = new Sphere(new Point(0, 0, -2), 2);
        assertEquals(10, rayIntersectionLoop(sphere10,camera18), "wrong number of intersections");
        //TC04: 9 intersection points
        Sphere sphere9 = new Sphere(new Point(0, 0, -1), 4);
        assertEquals(9, rayIntersectionLoop(sphere9,camera18), "wrong number of intersections");
        //TC05: 0 intersection points
        Sphere sphere0 = new Sphere(new Point(0, 0, 1), 0.5);
        assertEquals(0, rayIntersectionLoop(sphere0,camera18), "wrong number of intersections");

    }

    // testing the integration of camera and plane
    @Test
    void planeIntegrationTest() {

    }


    // testing the integration of camera and triangle
    @Test
    void triangleIntegrationTest() {
        //

    }
}
