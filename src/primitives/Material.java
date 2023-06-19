package primitives;

/**
 * This class will present a material of a geometry
 * @author Avidan and Ziv
 */
public class Material {
    /**
     * The diffuse coefficient of the material.
     */
    public  Double3 kD = Double3.ZERO;
    /**
     * The coefficient transparency of the material.
     */
    public Double3 kT = Double3.ZERO;

    /**
     * The coefficient reflection of the material.
     */
    public Double3 kR = Double3.ZERO;

    /**
     * The specular coefficient of the material.
     */
    public Double3 kS = Double3.ZERO;
    /**
     * The shininess coefficient of the material.
     */
    public int nShininess = 0;
    //===========setters============//

    /**
     * sets the transparency coefficient of the material.
     *
     * @param kT the transparency coefficient to set
     * @return This Material object with the updated transparency coefficient
     */
    public Material setKt(Double3 kT) {
        this.kT = kT;
        return this;
    }

    /**
     * sets the reflection coefficient of the material.
     *
     * @param kR the reflection coefficient to set
     * @return This Material object with the updated reflection coefficient
     */
    public Material setKr(Double3 kR) {
        this.kR = kR;
        return this;
    }

    /**
     * sets the transparency coefficient of the material.
     *
     * @param kT the transparency coefficient to set
     * @return This Material object with the updated transparency coefficient
     */
    public Material setKt(double kT) {
        this.kT = new Double3(kT);
        return this;
    }

    /**
     * sets the reflection coefficient of the material.
     *
     * @param kR the reflection coefficient to set
     * @return This Material object with the updated reflection coefficient
     */
    public Material setKr(double kR) {
        this.kR = new Double3(kR);
        return this;
    }

    /**
     * sets the diffuse coefficient of the material.
     *
     * @param kD the diffuse coefficient to set
     * @return This Material object with the updated diffuse coefficient
     */
    public Material setKd(Double3 kD) {
        this.kD = kD;
        return this;
    }

    /**
     * sets the diffuse coefficient of the material.
     *
     * @param kD the diffuse coefficient to set
     * @return This Material object with the updated diffuse coefficient
     */
    public Material setKd(double kD) {
        this.kD = new Double3(kD);
        return this;
    }

    /**
     * Sets the specular coefficient of the material.
     *
     * @param kS the specular coefficient to set
     * @return This Material object with the updated specular coefficient
     */
    public Material setKs(Double3 kS) {
        this.kS = kS;
        return this;
    }

    /**
     * Sets the specular coefficient of the material.
     *
     * @param kS the specular coefficient to set
     * @return This Material object with the updated specular coefficient
     */
    public Material setKs(double kS) {
        this.kS = new Double3(kS);
        return this;
    }

    /**
     * Sets the shininess coefficient of the material.
     *
     * @param nShininess the shininess to set
     * @return This Material object with the updated shininess coefficient
     */
    public Material setNShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }

}
