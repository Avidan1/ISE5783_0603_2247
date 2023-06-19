package renderer;

import geometries.Geometry;
import geometries.Intersectable;
import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;
import scene.Scene;

import static java.awt.Color.BLUE;
import static java.awt.Color.WHITE;

public class StarOfDavid {
    private Intersectable sphere = new Sphere(new Point(0, 0, -200), 60d)
            .setEmission(new Color(BLUE))
            .setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(30));
    private Material trMaterial = new Material().setKd(0.5).setKs(0.5).setNShininess(30);

    private Scene scene = new Scene("Star of David");
    private Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0))
            .setVPSize(200, 200).setVPDistance(1000)
            .setRayTracer(new RayTracerBasic(scene));

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
                new Point(100, 0, -100)
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
