package scene;

import geometries.Geometries;
import lighting.AmbientLight;
import lighting.LightSource;
import primitives.Color;

import java.util.LinkedList;
import java.util.List;

public class Scene {
    //===========fields================
    /**
     * The name of the scene
     */
    public final String name;
    /**
     * The background color of the scene
     */
    public Color background = Color.BLACK;
    /**
     * The ambient light of the scene
     */
    public AmbientLight ambientLight = AmbientLight.NONE;
    /**
     * The geometries of the scene
     */
    public Geometries geometries = new Geometries();

    /**
     * The lights of the scene
     */
    public List<LightSource> lights = new LinkedList<>();
    //=============constructor================

    /**
     * Constructor for scene
     *
     * @param name the name of the scene
     */
    public Scene(String name) {
        this.name = name;
    }

    //=============setters================

    /**
     * Setter for background color
     *
     * @param background the background color intensity
     * @return the scene object itself
     */
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    /**
     * Setter for ambient light
     *
     * @param ambientLight the ambient light  for the scene
     * @return the scene object itself
     */

    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    /**
     * Setter for geometries
     *
     * @param geometries the 3D model of the scene
     * @return the scene object itself
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }

    /**
     * Setter for lights
     * @param lights list of lights for the scene
     * @return The scene object itself
     */
    public Scene setLights(List<LightSource> lights) {
        this.lights = lights;
        return this;
    }
}
