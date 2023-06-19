package renderer;

import geometries.Geometry;
import geometries.Polygon;
import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import lighting.DirectionalLight;
import lighting.PointLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import scene.Scene;

import static java.awt.Color.BLUE;
import static java.awt.Color.WHITE;

/**
 * Test rendering a basic image
 *
 * @author Dan
 */
public class OurImage {

        private final Scene scene1 = new Scene("Test scene");
        private final Scene scene2 = new Scene("Test scene")
                .setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.15)));

        private final Camera camera1 = new Camera(new Point(0, 0, 1000),
                new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setVPSize(150, 150).setVPDistance(1000);
        private final Camera camera2 = new Camera(new Point(0, 0, 1000),
                new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setVPSize(200, 200).setVPDistance(1000);

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
        private final Geometry sphere2 = new Sphere(sphereCenter2, SPHERE_RADIUS).setEmission( new Color(30, 10, 100))
                .setMaterial(new Material().setKd(KD).setKs(KS).setNShininess(SHININESS));
        private final Geometry sphere3 = new Sphere(sphereCenter3, SPHERE_RADIUS).setEmission( new Color(30, 100, 40))
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

       public void ourImage1 () {
            scene1.geometries.add(triangle1, triangle2,triangle3, sphere1, sphere2, sphere3);

            scene1.lights.add(new DirectionalLight(Color.WHITE, new Vector(1, 1, -1)));
            scene1.lights.add(new PointLight(sphereLightColor1, sphereLightPosition).setKl(0.00001).setKq(0.000001));
            scene1.lights.add(new PointLight(sphereLightColor3, sphereLightPosition).setKl(0.00001).setKq(0.000001));
            scene1.lights.add(new SpotLight(trianglesLightColor, trianglesLightPosition, trianglesLightDirection)
                    .setKl(0.00001).setKq(0.00000001));

            ImageWriter imageWriter = new ImageWriter("ourImage1", 500, 500);
            camera1.setImageWriter(imageWriter).setRayTracer(new RayTracerBasic(scene1));
            camera1.renderImage();
            camera1.writeToImage();
        }



}