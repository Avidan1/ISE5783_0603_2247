package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.LinkedList;
import java.util.List;

/**
 * This class represents a list of geometries
 * that implements the Intersectable interface
 *
 * @author Avidan and Ziv
 */
public class Geometries extends Intersectable {
    private final List<Intersectable> intersectables = new LinkedList<Intersectable>();

    /**
     * constructor without parameters, creates a new empty list of geometries
     */
    public Geometries() {
    }

    /**
     * constructor with parameters, creates a new list of geometries
     *
     * @param geometries array of geometries
     */
    public Geometries(Intersectable... geometries) {
        this.intersectables.addAll(List.of(geometries));
    }

    /**
     * This method adds the geometries array to the list
     *
     * @param geometries array of geometries
     */
    public void add(Intersectable... geometries) {
        this.intersectables.addAll(List.of(geometries));
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        List<Point> intersections = null;
        for (Intersectable geometry : this.intersectables) {
            List<Point> geometryIntersections = geometry.findIntersections(ray);
            if (geometryIntersections != null) {
                if (intersections == null)
                    intersections = new LinkedList<>();
                intersections.addAll(geometryIntersections);
            }
        }
        return intersections;
    }
}

