package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.isZero;

/**
 * Camera class represents a camera in 3D Cartesian coordinate system
 *
 * @author Avidan and Ziv
 */
public class Camera {

    // =================== Fields ===================

    /**
     * The position of the camera.
     */
    private final Point p0;
    /**
     * The up vector of the camera.
     */
    private final Vector vUp;
    /**
     * The to vector of the camera.
     */
    private final Vector vTo;
    /**
     * The right vector of the camera.
     */
    private final Vector vRight;
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
    /**
     * The number of rays for depth of field effects.
     */
    private int numRays;
    /**
     * The apertureSize size for depth of field effects.
     */
    private double apertureSize = 1;

    /**
     * The focal length for depth of field effects.
     */
    private double focalDistance = 0;

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

    //=================== Setters (builder) ===================

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
     * Set Depth Of Field with its parameters
     *
     * @param focalDistance distance from the camera to the focal plane
     * @param aperture      size of the aperture
     * @param numOfRays     number of rays for depth of field effects
     * @return updated Camera object
     */
    public Camera setDof(double focalDistance, double aperture, int numOfRays) {
        this.focalDistance = focalDistance;
        this.apertureSize = aperture;
        this.numRays = numOfRays;
        return this;
    }

    /**
     * Set the distance of the camera from the view plane.
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
     * @return updated Camera object
     */
    public Camera setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }

    /**
     * Set the ray tracer of the camera.
     *
     * @param tracer the ray tracer of the camera
     * @return updated Camera object
     */
    public Camera setRayTracer(RayTracerBase tracer) {
        this.tracer = tracer;
        return this;
    }

    // =================== Functions ===================

    /**
     * Construct a ray through a pixel in the view plane.
     *
     * @param nX number of pixels in X axis
     * @param nY number of pixels in Y axis
     * @param j  pixel number in X axis
     * @param i  pixel number in Y axis
     * @return the ray through the pixel
     */
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
     * Constructs a list of rays through a pixel in the view plane with depth of field effects.
     *
     * @param nX number of pixels in X axis
     * @param nY number of pixels in Y axis
     * @param j  pixel number in X axis
     * @param i  pixel number in Y axis
     * @return the list of rays through the pixel with depth of field effects
     */
    private List<Ray> constructRaysWithDOF(int nX, int nY, int j, int i) {
        // Construct the centerRay
        Ray centerRay = constructRay(nX, nY, j, i);
        if (numRays <= 1)
            return List.of(centerRay);

        List<Ray> rays = new LinkedList<>();
        // add the centerRay to the list
        rays.add(centerRay);
        Point focalPoint= centerRay.getPoint(this.focalDistance);
        // Center of the apertureSize plane
        Point pCenter = centerRay.getP0();
        for (int k = 0; k < this.numRays; k++) {
            // Generate a random point on the apertureSize
            Point pointOnAperture = generatePointOnAperture(pCenter);
            Vector toFocalPoint = focalPoint.subtract(pointOnAperture).normalize();
            // Add the constructed centerRay  to the list
            rays.add(new Ray(pointOnAperture,toFocalPoint));
        }

        return rays;
    }


    /**
     * Generates a random point on the apertureSize plane for depth of field effects.
     * contributes to the realistic rendering of the depth of field effect
     *
     * @param pCenter the center point of the apertureSize plane
     * @return the random point on the apertureSize plane
     */
    private Point generatePointOnAperture(Point pCenter) {
/*        double angle = Math.random() * 2 * Math.PI;*/
        double radius = (apertureSize / 2);
        double x = pCenter.getX() + (Math.random() * apertureSize) - (apertureSize / 2);
        double y = pCenter.getY() + (Math.random() * apertureSize) - (apertureSize / 2);
        double z = pCenter.getZ() + (Math.random() * apertureSize) - (apertureSize / 2);
        if (x>radius+pCenter.getX()||y>radius+pCenter.getY()||z>radius+pCenter.getZ())
            return generatePointOnAperture(pCenter);
        return new Point(x, y, z);
    }

    /**
     * Throws UnsupportedOperationException if any of the required resources are missing
     * (rayTracerBase, imageWriter, width, height, distance).
     *
     * @return the camera itself
     */
    public Camera renderImage() {
        if (this.tracer == null || this.imageWriter == null || this.width == 0 || this.height == 0 || this.distance == 0)
            throw new UnsupportedOperationException("MissingResourcesException");
        int nX = this.imageWriter.getNx();
        int nY = this.imageWriter.getNy();
        for (int i = 0; i < nY; i++)
            for (int j = 0; j < nX; j++)
                castRay(j, i, nX, nY);
        return this;
    }

    /**
     * Casts ray or rays through the given pixel and color it with the color returned by the ray tracer.
     */
    private void castRay(int j, int i, int nX, int nY) {
        List<Color> colors = new LinkedList<>();
        for (Ray ray : constructRaysWithDOF(nX, nY, j, i))
            colors.add(this.tracer.traceRay(ray));
        this.imageWriter.writePixel(j, i, Color.average(colors));
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

    /**
     * Throws UnsupportedOperationException if imageWriter object is null.
     */
    public void writeToImage() {
        if (imageWriter == null) throw new UnsupportedOperationException("MissingResourcesException");
        this.imageWriter.writeToImage();
    }


}
