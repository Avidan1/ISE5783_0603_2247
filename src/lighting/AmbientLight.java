package lighting;
import primitives.Color;
import primitives.Double3;


public class AmbientLight {
//todo add javadoc?
    private Color intensity ; //todo final?

    public static AmbientLight NONE = new AmbientLight(Color.BLACK, Double3.ZERO);

    public AmbientLight(Color ia, Double3 ka) {
        intensity = ia.scale(ka);
    }

    public AmbientLight(Color ia, double ka) {
        intensity = ia.scale(ka);
    }
    public Color getIntensity(){
        return intensity;
    }
}
