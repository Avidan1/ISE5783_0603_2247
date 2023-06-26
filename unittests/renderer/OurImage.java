package renderer;

import geometries.Geometry;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import lighting.DirectionalLight;
import lighting.PointLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import scene.Scene;

import static java.awt.Color.BLUE;

/**
 * Test rendering a basic image
 *
 * @author Ziv and Avidan
 */
public class OurImage {

    private final Scene scene1 = new Scene("Test scene");

    private static final int SHININESS = 301;
    private static final double KD = 0.5;
    private static final Double3 KD3 = new Double3(0.2, 0.6, 0.4);

    private static final double KS = 0.5;
    private static final Double3 KS3 = new Double3(0.2, 0.4, 0.3);

    private final Material material = new Material().setKd(KD3).setKs(KS3).setNShininess(SHININESS);
    private final Color trianglesLightColor = new Color(800, 500, 250);
    private final Color sphereLightColor1 = new Color(800, 500, 0);
    private final Color sphereLightColor2 = new Color(0, 500, 250);
    private final Color sphereLightColor3 = new Color(800, 0, 250);
    private final Color sphereColor = new Color(BLUE).reduce(2);

    private final Point sphereCenter1 = new Point(-30, 30, -30);
    private final Point sphereCenter2 = new Point(20, -30, -30);
    private final Point sphereCenter3 = new Point(50, 50, 0);
    private final Point sphereCenter4 = new Point(0, -50, 0);
    private final Point sphereCenter5 = new Point(0, 0, -100);
    private final Point sphereCenter6 = new Point(0, 50, 100);

    private static final double SPHERE_RADIUS = 30d;

    // The triangles' vertices:
    private final Point[] vertices =
            {
                    // the shared left-bottom:
                    new Point(-110, -110, -150),
                    // the shared right-top:
                    new Point(95, 100, -150),
                    // the right-bottom
                    new Point(110, -110, -150),
                    // the left-top
                    new Point(-75, 78, 100)
            };
    private final Point sphereLightPosition = new Point(-50, -50, 25);
    private final Point trianglesLightPosition = new Point(30, 10, -100);
    private final Vector trianglesLightDirection = new Vector(-2, -2, -2);

    private final Geometry sphere1 = new Sphere(sphereCenter1, SPHERE_RADIUS)
            .setEmission(sphereColor).setMaterial(new Material().setKd(KD).setKs(KS).setNShininess(SHININESS));
    private final Geometry sphere2 = new Sphere(sphereCenter2, SPHERE_RADIUS).setEmission(new Color(30, 10, 100))
            .setMaterial(new Material().setKd(KD).setKs(KS).setNShininess(SHININESS));
    private final Geometry sphere3 = new Sphere(sphereCenter3, SPHERE_RADIUS).setEmission(new Color(30, 100, 40))
            .setMaterial(new Material().setKd(KD).setKs(KS).setNShininess(SHININESS));

    private final Geometry sphere4 = new Sphere(sphereCenter4, SPHERE_RADIUS).setEmission(new Color(100, 30, 40))
            .setMaterial(new Material().setKd(KD).setKs(KS).setNShininess(SHININESS));
    private final Geometry sphere5 = new Sphere(sphereCenter5, SPHERE_RADIUS).setEmission(new Color(100, 100, 100))
            .setMaterial(new Material().setKd(KD).setKs(KS).setNShininess(SHININESS));
    private final Geometry sphere6 = new Sphere(sphereCenter6, SPHERE_RADIUS).setEmission(new Color(100, 100, 100))
            .setMaterial(new Material().setKd(KD).setKs(KS).setNShininess(SHININESS));
    private final Geometry triangle1 = new Triangle(vertices[0], vertices[1], vertices[2])
            .setMaterial(material);
    private final Geometry triangle2 = new Triangle(vertices[0], vertices[1], vertices[3])
            .setMaterial(material);

    private final Geometry triangle3 = new Triangle(vertices[0], vertices[2], vertices[3]).setMaterial(new Material().setKd(0.4).setKs(0.4).setNShininess(100));


    /**
     * Produce a picture of complex shapes with many geometries and light sources
     */
    @Test

    public void ourImage1() {
        scene1.geometries.add(triangle1, triangle2, triangle3, sphere1, sphere2);


        scene1.lights.add(new DirectionalLight(Color.WHITE, new Vector(1, 1, -1)));
        scene1.lights.add(new PointLight(sphereLightColor1, sphereLightPosition).setKl(0.00001).setKq(0.000001));
        scene1.lights.add(new PointLight(sphereLightColor3, sphereLightPosition).setKl(0.00001).setKq(0.000001));
        scene1.lights.add(new SpotLight(trianglesLightColor, trianglesLightPosition, trianglesLightDirection)
                .setKl(0.00001).setKq(0.00000001));

        new Camera(new Point(0, 0, 1000),
                new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setVPSize(150, 150).setVPDistance(1000)
                .setImageWriter(new ImageWriter("ourImage1", 500, 500))
                .setRayTracer(new RayTracerBasic(scene1))
                .renderImage()
                .writeToImage();
    }


    @Test
    public void ourImage2() {
        // Create the scene
        Scene scene = scene1;

        // Add objects to the scene
        scene.geometries.add(sphere4, sphere5, sphere6);

        // Add a light source to the scene
        scene.lights.add(new DirectionalLight(Color.WHITE, new Vector(1, 1, -1)));

        new Camera(new Point(0, 0, 1000),
                new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setVPSize(150, 150).setVPDistance(1000)
                .setDof(3, 2.8, 10)
                .setImageWriter(new ImageWriter("ourImage2", 800, 800))
                .setRayTracer(new RayTracerBasic(scene))
                .renderImage()
                .writeToImage();
    }

    @Test
    public void ourImageFinalPic() {
        Color pyramidColor = new Color(0, 30, 150);
        Material pyramidMaterial = new Material().setKd(0.5).setKs(0.5).setNShininess(100);
        Scene scene1 = new Scene("Test scene");

        Geometry plane = new Plane(new Point(0, 0, 0), new Vector(0, 1, 0))
                .setEmission(new Color(50, 50, 50)).setMaterial(new Material().setKd(0.5).setKs(0.5).setKr(0.5).setNShininess(70));
        Geometry sphere1 = new Sphere(new Point(-50, 15, -50), 15d)
                .setEmission(new Color(180, 0, 0)).setMaterial(new Material().setKd(0.4).setKs(0.8).setNShininess(70).setKt(0.8));
        Geometry sphere2 = new Sphere(new Point(-22, 13, -50), 13d)
                .setEmission(new Color(0, 180, 0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setNShininess(20).setKt(0.2));
        Geometry sphere3 = new Sphere(new Point(2, 11, -50), 11d)
                .setEmission(new Color(0, 0, 255)).setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(40).setKr(0.2));
        Geometry sphere4 = new Sphere(new Point(22, 9, -50), 9d)
                .setEmission(new Color(0, 255, 255)).setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(40).setKr(0.9));
        Geometry sphere5 = new Sphere(new Point(38, 7, -50), 7d)
                .setEmission(new Color(255, 0, 255)).setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(40).setKr(0.2));
        Geometry sphereInSphere1 = new Sphere(new Point(30, 20, 40), 20d)
                .setEmission(new Color(0, 0, 100)).setMaterial(new Material().setKd(0.2).setKs(0.2).setNShininess(40).setKt(0.5));
        Geometry sphereInSphere2 = new Sphere(new Point(30, 20, 40), 15d)
                .setEmission(new Color(0, 100, 0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setNShininess(40).setKt(0.5));
        Geometry sphereInSphere3 = new Sphere(new Point(30, 20, 40), 10d)
                .setEmission(new Color(100, 0, 0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setNShininess(40).setKr(0.5));
        Geometry sphere11 = new Sphere(new Point(-50, 15, -300), 15d)
                .setEmission(new Color(180, 0, 0)).setMaterial(new Material().setKd(0.4).setKs(0.8).setNShininess(70).setKt(0.8));
        Geometry sphere22 = new Sphere(new Point(-22, 13, -300), 13d)
                .setEmission(new Color(0, 180, 0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setNShininess(20).setKt(0.2));
        Geometry sphere33 = new Sphere(new Point(2, 11, -300), 11d)
                .setEmission(new Color(0, 0, 255)).setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(40).setKr(0.2));
        Geometry sphere44 = new Sphere(new Point(22, 9, -300), 9d)
                .setEmission(new Color(0, 255, 255)).setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(40).setKr(0.2));
        Geometry sphere55 = new Sphere(new Point(38, 7, -300), 7d)
                .setEmission(new Color(255, 0, 255)).setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(40).setKr(0.2));
        Geometry triangleP1 = new Triangle(new Point(-17.89, 10, -16.97 + 45), new Point(0, 0, 0 + 45), new Point(-4.41, 0, -17.68 + 45))
                .setEmission(pyramidColor).setMaterial(pyramidMaterial);
        Geometry triangleP2 = new Triangle(new Point(-17.89, 10, -16.97 + 45), new Point(-4.41, 0, -17.68 + 45), new Point(7.68, 0, 5.59 + 45))
                .setEmission(pyramidColor).setMaterial(pyramidMaterial);
        Geometry triangleP3 = new Triangle(new Point(-17.89, 10, -16.97 + 45), new Point(7.68, 0, 5.59 + 45), new Point(-27.68, 0, -5.59 + 45))
                .setEmission(pyramidColor).setMaterial(pyramidMaterial);

        scene1.geometries.add(plane, sphere1, sphere2, sphere3, sphere4, sphere5, sphereInSphere1, sphereInSphere3, sphereInSphere2, sphere11, sphere22, sphere33, sphere44, sphere55, triangleP1, triangleP2, triangleP3);
        scene1.lights.add(new DirectionalLight(Color.WHITE, new Vector(10, -15, -15)));
        //scene1.lights.add(new DirectionalLight(new Color(0, 15, 15), new Vector(0, -15, -15)));

        new Camera(new Point(25, 50, 50),
                new Vector(0, -1, -1), new Vector(0, 1, -1))
                .setVPSize(2500, 2500).setVPDistance(1000)
                .setDof(60, 2.2, 50)
                .setImageWriter(new ImageWriter("ourImageFinalPic", 1000, 1000))
                .setRayTracer(new RayTracerBasic(scene1))
                .renderImage().writeToImage();
    }


}

