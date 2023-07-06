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
 */
public class Geometries extends Intersectable {
    private final List<Intersectable> intersectables = new LinkedList<>();
    static List<Function<Intersectable, Double>> axes = new ArrayList<>(Arrays.asList((x) -> x.bbox.center.getX(), (x) -> x.bbox.center.getY(), (x) -> x.bbox.center.getZ()));

    public Geometries() {
        bbox = new AABB(new Point(new Double3(Double.POSITIVE_INFINITY)), new Point(new Double3(Double.NEGATIVE_INFINITY)));
    }

    public Geometries(Intersectable... geometries) {
        this.intersectables.addAll(List.of(geometries));
    }

    public void add(Intersectable... geometries) {
        this.intersectables.addAll(List.of(geometries));
    }

    public Geometries add(List<Intersectable> geometries) {
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
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        List<GeoPoint> intersections = null;
        if (!this.bbox.intersect(ray, maxDistance))
            return null;
        for (Intersectable geometry : this.intersectables) {
            List<GeoPoint> geoIntersections = geometry.findGeoIntersections(ray, maxDistance);
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
