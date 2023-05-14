package renderer;

import primitives.*;

import static primitives.Util.isZero;

/**
 * Camera class represents a camera in 3D Cartesian coordinate system
 *
 * @ author Avidan and Ziv
 */
public class Camera {

    // =================== Fields ===================

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
     * The image writer of the camera.
     */
    private ImageWriter imageWriter;
    /**
     * The ray tracer of the camera.
     */
    private RayTracerBase tracer;

    //=================== Constructors ===================

    /**
     * Construct a new Camera object with the given parameters.
     *
     * @param p0  the position of the camera
     * @param vUp the up vector of the camera
     * @param vTo the to vector of the camera
     */
    public Camera(Point p0, Vector vTo, Vector vUp) {
        this.p0 = p0;
        this.vTo = vTo.normalize();
        this.vUp = vUp.normalize();
        if (!isZero(vUp.dotProduct(vTo)))
            throw new IllegalArgumentException("vUp and vTo must be orthogonal");
        this.vRight = vTo.crossProduct(vUp).normalize();
    }
    //=================== Setters ===================

    /**
     * Set the size of the view plane.
     *
     * @param width  of the view plane
     * @param height of the view plane
     * @return updated Camera itself
     */
    public Camera setVPSize(double width, double height) {

        this.width = width;
        this.height = height;
        return this;
    }

    /**
     * set the distance of the camera from the view plane.
     *
     * @param distance from the camera to the view plane
     * @return updated Camera itself
     */
    public Camera setVPDistance(double distance) {
        this.distance = distance;
        return this;
    }

    /**
     * Set the image writer of the camera.
     *
     * @param imageWriter the image writer of the camera
     */
    public Camera setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }

    /**
     * Set the ray tracer of the camera.
     *
     * @param tracer the ray tracer of the camera
     */
    public Camera setRayTracer(RayTracerBase tracer) {
        this.tracer = tracer;
        return this;
    }

    /**
     * Construct a ray through a pixel in the view plane.
     *
     * @param nX number of pixels in X axis
     * @param nY number of pixels in Y axis
     * @param j  pixel number in X axis
     * @param i  pixel number in Y axis
     * @return the ray through the pixel
     */
    // =================== functions ===================
    public Ray constructRay(int nX, int nY, int j, int i) {
        double rY = this.height / nY;
        double rX = this.width / nX;

        double yI = -(i - (nY - 1d) / 2) * rY;
        double jX = (j - (nX - 1d) / 2) * rX;
        Point pIJ = this.p0.add(this.vTo.scale(this.distance));

        if (jX != 0) pIJ = pIJ.add(this.vRight.scale(jX));
        if (yI != 0) pIJ = pIJ.add(this.vUp.scale(yI));

        return new Ray(this.p0, pIJ.subtract(this.p0));
    }

    /**
     * Throws UnsupportedOperationException if any of the required resources are missing
     * (rayTracerBase, imageWriter, width, height, distance).
     * not implemented yet
     */
    public void renderImage() {
        if (this.tracer == null || this.imageWriter == null || this.width == 0 || this.height == 0 || this.distance == 0)
            throw new UnsupportedOperationException("MissingResourcesException");
        int nX = this.imageWriter.getNx();
        int nY = this.imageWriter.getNy();
        for (int i = 0; i < nY; i++) {
            for (int j = 0; j < nX; j++) {
               this.imageWriter.writePixel(j, i, this.tracer.traceRay(this.constructRay(nX, nX, j, i)));
            }
        }
    }

    /**
     * Draws a grid on the image by writing a specified color to the pixels that fall on the grid lines.
     * Throws UnsupportedOperationException if imageWriter object is null.
     *
     * @param interval The spacing between grid lines.
     * @param color    The color to use for the grid lines.
     */
    public void printGrid(int interval, Color color) {
        if (this.imageWriter == null || this.width == 0 || this.height == 0 || this.distance == 0)
            throw new UnsupportedOperationException("MissingResourcesException");
        int nX = this.imageWriter.getNx();
        int nY = this.imageWriter.getNy();
        for (int i = 0; i < nY; i++) {
            for (int j = 0; j < nX; j++) {
                if (i % interval == 0 || j % interval == 0)
                    this.imageWriter.writePixel(j, i, color);
            }
        }
    }

    public void writeToImage() {
        if (imageWriter == null) throw new UnsupportedOperationException("MissingResourcesException");
        this.imageWriter.writeToImage();
    }

    private Color castRay(int nX, int nY, int i, int j) {
        return this.tracer.traceRay(this.constructRay(nX, nY, i, j));
    }
}
