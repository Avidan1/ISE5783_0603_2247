package geometries;

import primitives.Double3;
import primitives.Point;
import primitives.Ray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

/**
 * This class represents a list of geometries
 * that implements the Intersectable interface
 *
 * @author Avidan and Ziv
 */
public class Geometries extends Intersectable {
    /**
     * list of geometries
     */
    private final List<Intersectable> intersectables = new LinkedList<>();
    /**
     * list of functions that return the center of the bounding box
     */
    static List<Function<Intersectable, Double>> axes = new ArrayList<>(Arrays.asList((x) -> x.bbox.center.getX(), (x) -> x.bbox.center.getY(), (x) -> x.bbox.center.getZ()));

    /**
     * constructor without parameters, creates a new empty list of geometries
     */
    public Geometries() {

        bbox = new AABB(new Point(new Double3(Double.POSITIVE_INFINITY)),new Point(new Double3(Double.NEGATIVE_INFINITY)));
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
    /***
     * add geometries to te list
     * @param geometries list of geometries
     * @return the geometries object after adding the geometries
     */
    public Geometries add(List<Intersectable> geometries){
        if (geometries.size() == 0) return this;
        Point maxBbox = this.bbox.getMax(), minBbox = this.bbox.getMin();
        boolean inf = this.bbox.isInfinite();
        for (Intersectable geometry : geometries) {
            this.intersectables.add(geometry);
            if (geometry.bbox.isInfinite()) {
                inf = true;
            }
            maxBbox = Point.createMaxPoint(maxBbox, geometry.bbox.getMax());
            minBbox = Point.createMinPoint(minBbox, geometry.bbox.getMin());
        }
        this.bbox = new AABB(minBbox, maxBbox).setInfinity(inf);
        return this;
    }
    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        List<GeoPoint> intersections = null;
        if(!this.bbox.intersect(ray, maxDistance))
            return null;
        for (var geometry : this.intersectables) {
            var geoIntersections = geometry.findGeoIntersections(ray, maxDistance);
            if (geoIntersections != null) {
                if (intersections == null)
                    intersections = new LinkedList<>(geoIntersections); // todo corect?

                else
                    intersections.addAll(geoIntersections);
            }
        }
        return intersections;
    }
}

