package renderer;

import geometries.Geometry;
import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import lighting.DirectionalLight;
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
        private final Color sphereLightColor = new Color(800, 500, 0);
        private final Color sphereColor = new Color(BLUE).reduce(2);

        private final Point sphereCenter = new Point(0, 0, -50);
        private static final double SPHERE_RADIUS = 50d;

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

        private final Geometry sphere = new Sphere(sphereCenter, SPHERE_RADIUS)
                .setEmission(sphereColor).setMaterial(new Material().setKd(KD).setKs(KS).setNShininess(SHININESS));
        private final Geometry triangle1 = new Triangle(vertices[0], vertices[1], vertices[2])
                .setMaterial(material);
        private final Geometry triangle2 = new Triangle(vertices[0], vertices[1], vertices[3])
                .setMaterial(material);


        /**
         * Create a Star of David using dots and triangles
         */
        @Test
        public void starOfDavid () {
            Scene scene = new Scene("Star of David");

            Point dotCenter = new Point(0, 0, -150);
            double dotRadius = 5;
            int numDots = 12;

            // Create dots forming the Star of David
            for (int i = 0; i < numDots; i++) {
                double angle = i * (2 * Math.PI / numDots);
                double x = Math.cos(angle) * 80;
                double y = Math.sin(angle) * 80;
                Point dotPosition = new Point(x, y, dotCenter.getZ());
                Geometry dot = new Sphere(dotPosition, dotRadius)
                        .setEmission(Color.WHITE)
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(301));
                scene.geometries.add(dot);
            }

            // Create triangles connecting the dots
            for (int i = 0; i < numDots; i++) {
                Point dot1 = new Point(Math.cos(i * (2 * Math.PI / numDots)) * 80, Math.sin(i * (2 * Math.PI / numDots)) * 80, dotCenter.getZ());
                Point dot2 = new Point(Math.cos((i + 1) * (2 * Math.PI / numDots)) * 80, Math.sin((i + 1) * (2 * Math.PI / numDots)) * 80, dotCenter.getZ());
                Point dot3 = dotCenter;
                Geometry triangle = new Triangle(dot1, dot2, dot3)
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(301));
                scene.geometries.add(triangle);
            }

            scene.lights.add(new DirectionalLight(Color.WHITE, new Vector(0, 0, -1)));

            ImageWriter imageWriter = new ImageWriter("starOfDavid", 500, 500);
            Camera camera = new Camera(new Point(0, 0, 1000),
                    new Vector(0, 0, -1), new Vector(0, 1, 0))
                    .setImageWriter(imageWriter)
                    .setRayTracer(new RayTracerBasic(scene))
                    .setVPSize(200, 200)
                    .setVPDistance(1000);

            camera.renderImage();
            camera.writeToImage();
        }
    }