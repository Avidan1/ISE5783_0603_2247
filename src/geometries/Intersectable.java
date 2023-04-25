package geometries;
import primitives.*;
import java.util.List;
/**
 * Intersectable interface represents a geometry in 3D Cartesian coordinate system
 */
public interface Intersectable {
    /**
     * caalculate the intersection points of the geometry with the specified ray
     * @param ray
     * @return a list of the intersection points
     */
    List<Point> findIntsersections(Ray ray);

}
