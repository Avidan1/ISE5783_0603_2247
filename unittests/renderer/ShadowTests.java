package renderer;

import geometries.Intersectable;
import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import lighting.DirectionalLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;
import scene.Scene;

import static java.awt.Color.BLUE;
import static java.awt.Color.WHITE;

/**
 * Testing basic shadows
 *
 * @author Dan
 */
public class ShadowTests {
    private Intersectable sphere = new Sphere(new Point(0, 0, -200), 60d)                                         //
            .setEmission(new Color(BLUE))                                                                                  //
            .setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(30));
    private Material trMaterial = new Material().setKd(0.5).setKs(0.5).setNShininess(30);

    private Scene scene = new Scene("Test scene");
    private Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0))   //
            .setVPSize(200, 200).setVPDistance(1000)                                                                       //
            .setRayTracer(new RayTracerBasic(scene));

    /**
     * Helper function for the tests in this module
     */
    void sphereTriangleHelper(String pictName, Triangle triangle, Point spotLocation) {
        scene.geometries.add(sphere, triangle.setEmission(new Color(BLUE)).setMaterial(trMaterial));
        scene.lights.add( //
                new SpotLight(new Color(400, 240, 0), spotLocation, new Vector(1, 1, -3)) //
                        .setKl(1E-5).setKq(1.5E-7));
        camera.setImageWriter(new ImageWriter(pictName, 400, 400)) //
                .renderImage() //
                .writeToImage();
    }

    /**
     * Produce a picture of a sphere and triangle with point light and shade
     */
    @Test
    public void sphereTriangleInitial() {
        sphereTriangleHelper("shadowSphereTriangleInitial", //
                new Triangle(new Point(-70, -40, 0), new Point(-40, -70, 0), new Point(-68, -68, -4)), //
                new Point(-100, -100, 200));
    }

    /**
     * Sphere-Triangle shading - move triangle up-right
     */
    @Test
    public void sphereTriangleMove1() {
        sphereTriangleHelper("shadowSphereTriangleMove2", //
                new Triangle(new Point(-60, -30, 0), new Point(-30, -60, 0), new Point(-58, -58, -4)), //
                new Point(-100, -100, 200));
    }


    /**
     * Sphere-Triangle shading - move triangle upper-righter
     */
    @Test
    public void sphereTriangleMove2() {
        sphereTriangleHelper("shadowSphereTriangleMove1", //
                new Triangle(new Point(-50, -20, 0), new Point(-20, -50, 0), new Point(-48, -48, -4)), //
                new Point(-100, -100, 200));
    }


    /** Sphere-Triangle shading - move spot closer */
    @Test
    public void sphereTriangleSpot1() {
        sphereTriangleHelper("shadowSphereTriangleSpot1", //
                new Triangle(new Point(-70, -40, 0), new Point(-40, -70, 0), new Point(-68, -68, -4)), //
                new Point(-100, -100, 140));
    }

    /** Sphere-Triangle shading - move spot even more close */
    @Test
    public void sphereTriangleSpot2() {
        sphereTriangleHelper("shadowSphereTriangleSpot2", //
                new Triangle(new Point(-70, -40, 0), new Point(-40, -70, 0), new Point(-68, -68, -4)), //
                new Point(-80, -80, 100));
    }

    /**
     * Produce a picture of a two triangles lighted by a spotlight with a Sphere
     * producing a shading
     */
    @Test
    public void trianglesSphere() {
        scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));

        scene.geometries.add( //
                new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135), new Point(75, 75, -150)) //
                        .setMaterial(new Material().setKs(0.8).setNShininess(60)), //
                new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150)) //
                        .setMaterial(new Material().setKs(0.8).setNShininess(60)), //
                new Sphere(new Point(0, 0, -11), 30d) //
                        .setEmission(new Color(BLUE)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(30)) //
        );
        scene.lights.add( //
                new SpotLight(new Color(700, 400, 400), new Point(40, 40, 115), new Vector(-1, -1, -4)) //
                        .setKl(4E-4).setKq(2E-5));

        camera.setImageWriter(new ImageWriter("shadowTrianglesSphere", 600, 600)) //
                .renderImage() //
                .writeToImage();
    }
//    @Test
//    public void starOfDavid3D() {
//        scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));
//
//        // Create the triangles for the Star of David
//        Triangle triangle1 = (Triangle) new Triangle(new Point(-50, -86.602540378443864, -100), new Point(50, -86.602540378443864, -100), new Point(0, 0, -100))
//                .setEmission(new Color(WHITE))
//                .setMaterial(new Material().setKs(0.8).setNShininess(60));
//        Triangle triangle2 = (Triangle) new Triangle(new Point(-50, 86.602540378443864, -100), new Point(50, 86.602540378443864, -100), new Point(0, 0, -100))
//                .setEmission(new Color(WHITE))
//                .setMaterial(new Material().setKs(0.8).setNShininess(60));
//        Triangle triangle3 = (Triangle) new Triangle(new Point(-50, -86.602540378443864, -100), new Point(50, -86.602540378443864, -100), new Point(0, 0, -100))
//                .setEmission(new Color(WHITE))
//                .setMaterial(new Material().setKs(0.8).setNShininess(60));
//        Triangle triangle4 = (Triangle) new Triangle(new Point(-50, 86.602540378443864, -100), new Point(50, 86.602540378443864, -100), new Point(0, 0, -100))
//                .setEmission(new Color(WHITE))
//                .setMaterial(new Material().setKs(0.8).setNShininess(60));
//
//        scene.geometries.add(sphere, triangle1, triangle2, triangle3, triangle4);
//        scene.lights.add(
//                 new SpotLight(new Color(700, 400, 400), new Point(40, 40, 115), new Vector(-1, -1, -4))
//                        .setKl(4E-4).setKq(2E-5));
//        scene.lights.add(new DirectionalLight(new Color(500, 300, 0), new Vector(1, 1, -1)));
//
//
//        ;
//
//        camera.setImageWriter(new ImageWriter("starOfDavid3D", 600, 600))
//                .renderImage()
//                .writeToImage();
//    }

    @Test
    public void starOfDavid3D() {
        scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));

        // Create the triangles for the Star of David
        Triangle triangle1 = (Triangle) new Triangle(
                new Point(-50, -86.602540378443864, -100),
                new Point(50, -86.602540378443864, -100),
                new Point(0, 0, -100)
        ).setEmission(new Color(WHITE)).setMaterial(new Material().setKs(0.8).setNShininess(60));

        Triangle triangle2 = (Triangle) new Triangle(
                new Point(-50, 86.602540378443864, -100),
                new Point(50, 86.602540378443864, -100),
                new Point(0, 0, -100)
        ).setEmission(new Color(WHITE)).setMaterial(new Material().setKs(0.8).setNShininess(60));

        scene.geometries.add(sphere, triangle1, triangle2);
        scene.lights.add(
                new SpotLight(new Color(700, 400, 400), new Point(40, 40, 115), new Vector(-1, -1, -4))
                        .setKl(4E-4).setKq(2E-5)
        );

        camera.setImageWriter(new ImageWriter("starOfDavid3D", 600, 600))
                .renderImage()
                .writeToImage();
    }
}