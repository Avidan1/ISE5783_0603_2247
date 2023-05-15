package scene;
import geometries.Geometries;
import lighting.AmbientLight;
import primitives.Color;

public class Scene {
    /**
     * The name of the scene
     */
    public String name;
    /**
     * The background color of the scene
     */
    public Color background;
    /**
     * The ambient light of the scene
     */
    public AmbientLight ambientLight = AmbientLight.NONE;
    /**
     * The geometries of the scene
     */
    public Geometries geometries = new Geometries();

    /**
     * Constructor for scene
     * @param name
     */
    public Scene(String name) {
        this.name = name;
    }

    /**
     * Setter for background color
     * @param background
     * @return this
     */
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }
    /**
     * Setter for ambient light
     * @param ambientLight
     * @return this
     */

    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }
    /**
     * Setter for geometries
     * @param geometries
     * @return this
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }
}
