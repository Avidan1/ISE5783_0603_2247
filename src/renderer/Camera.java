package renderer;
import primitives.*;

/*
 * Camera class represents a camera in 3D Cartesian coordinate system
 */
public class Camera {
    private Point p0;
    private Vector vUp;
    private Vector vTo;
    private Vector vRight;
    private double width;
    private double height;
    private double distance;

    /**
     * Construct a new Camera object with the given parameters.
     *
     * @param p0      the position of the camera
     * @param vUp     the up vector of the camera
     * @param vTo     the to vector of the camera
     */
    public Camera(Point p0, Vector vUp, Vector vTo) {
        this.p0 = p0;
        this.vUp = vUp.normalize();
        this.vTo = vTo.normalize();
        if (vUp.dotProduct(vTo) != 0)
            throw new IllegalArgumentException("vUp and vTo must be orthogonal");
        this.vRight = vTo.crossProduct(vUp).normalize();
    }

    /**
     * Set the distance of the camera from the view plane.
     *
     * @param width
     * @param height
     * @return itself
     */
    public Camera setVPSize(double width, double height) {

        this.width = width;
        this.height = height;
        return this;
    }

    /**
     *  set the distance of the camera from the view plane.
     * @param distance
     * @return itself
     */
    public Camera setDistance(double distance) {
        this.distance = distance;
        return this;
    }

    /**
     * Construct a ray through a pixel in the view plane.
     * @param nX number of pixels in X axis
     * @param nY number of pixels in Y axis
     * @param j pixel number in X axis
     * @param i pixel number in Y axis
     * @return the ray through the pixel
     */
    public Ray constructRay (int nX , int nY, int j, int i){
        return null;
    }

}
