package renderer;
import primitives.*;

/*
 * Camera class represents a camera in 3D Cartesian coordinate system
 */
public class Camera {
    /**
     * The position of the camera.
     */
    private Point p0;
    /**
     * The up vector of the camera.
     */
    private Vector vUp;
    /**
     * The to vector of the camera.
     */
    private Vector vTo;
    /**
     * The right vector of the camera.
     */
    private Vector vRight;
    /**
     * The width of the view plane.
     */
    private double width;
    /**
     * The height of the view plane.
     */
    private double height;
    /**
     * The distance of the camera from the view plane.
     */
    private double distance;

    /**
     * Construct a new Camera object with the given parameters.
     *
     * @param p0      the position of the camera
     * @param vUp     the up vector of the camera
     * @param vTo     the to vector of the camera
     */
    public Camera(Point p0, Vector vTo, Vector vUp) {
        this.p0 = p0;
        this.vTo = vTo.normalize();
        this.vUp = vUp.normalize();
        if (vUp.dotProduct(vTo) != 0)
            throw new IllegalArgumentException("vUp and vTo must be orthogonal");
        this.vRight = vTo.crossProduct(vUp).normalize();
    }

    /**
     * Set the size of the view plane.
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
    public Camera setVPDistance(double distance) {
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
        double rY = this.height / nY;
        double rX = this.width / nX;

        double yI = -(i - (nY - 1d) / 2) * rY;
        double jX = (j - (nX - 1d) / 2) * rX;
        Point pIJ = this.p0.add(this.vTo.scale(this.distance));

        if (jX != 0) pIJ = pIJ.add(this.vRight.scale(jX));
        if (yI != 0) pIJ = pIJ.add(this.vUp.scale(yI));

        return new Ray(this.p0, pIJ.subtract(this.p0));
    }

}
