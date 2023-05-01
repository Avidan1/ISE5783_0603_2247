package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * This class represents a list of geometries
 * that implements the Intersectable interface
 *
 * @author Avidan and Ziv
 */
public class Geometries implements Intersectable {
    private final List<Intersectable> intersectables;

    /**
     * constructor without parameters, creates a new empty list of geometries
     */
    public Geometries() {
        this.intersectables = new LinkedList<Intersectable>();
    }
    /**
     * constructor with parameters, creates a new list of geometries
     *
     * @param geometries array of geometries
     */
    public Geometries(Intersectable... geometries) {
        this.intersectables = new LinkedList<>();
        this.intersectables.addAll(Arrays.asList(geometries));
    }
    /**
     * This method adds the geometries array to the list
     *
     * @param geometries array of geometries
     */
    public void add(Intersectable... geometries) {
        this.intersectables.addAll(Arrays.asList(geometries));
    }
    /**
     * This method finds all the intersections of the ray with the geometries in the list
     *
     * @param ray to check if intersect with the geometries and where
     * @return list of all the intersections points
     */
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

