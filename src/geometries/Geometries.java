package geometries;

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
    private final List<Intersectable> intersectables = new LinkedList<>();

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
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {// TODO: 22/05/2023
        List<GeoPoint> intersections = null;
        for (Intersectable geometry : intersectables) {
            var geoIntersections = geometry.findGeoIntersections(ray);
            if (geoIntersections != null) {
                if (intersections == null)
                    intersections = new LinkedList<>(geoIntersections);
                else
                    intersections.addAll(geoIntersections);
            }
        }
        return intersections;
    }
}

