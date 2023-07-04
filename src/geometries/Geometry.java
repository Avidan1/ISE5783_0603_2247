package geometries;

import primitives.*;

import java.util.*;
import java.util.function.Function;

/**
 * The Geometries class represents a collection of intersectable geometries.
 * It implements the Intersectable interface and provides methods for adding and organizing geometries.
 * The class supports building a Bounding Volume Hierarchy (BVH) for efficient intersection calculations.
 */
public class Geometries extends Intersectable {
    private LinkedList<Intersectable> bodies;
    private static List<Function<Intersectable, Double>> axes = new ArrayList<>(Arrays.asList(
            (x) -> x.getBBox().getCenter().getX(),
            (x) -> x.getBBox().getCenter().getY(),
            (x) -> x.getBBox().getCenter().getZ()
    ));

    /**
     * Constructs an empty Geometries object.
     * Initializes the bodies list and sets the bounding box to infinite.
     */
    public Geometries() {
        bodies = new LinkedList<>();
        bbox = new AABB(new Point(Double.POSITIVE_INFINITY), new Point(Double.NEGATIVE_INFINITY));
    }

    /**
     * Constructs a Geometries object and adds the provided geometries to the collection.
     * Initializes the bodies list and sets the bounding box to encompass all geometries.
     *
     * @param geometries The array of geometries to add.
     */
    public Geometries(Intersectable... geometries) {
        this();
        add(geometries);
    }

    /**
     * Adds one or more geometries to the collection.
     * Updates the bounding box to encompass all geometries.
     *
     * @param geometries The array of geometries to add.
     */
    public void add(Intersectable... geometries) {
        add(Arrays.asList(geometries));
    }

    /**
     * Adds a list of geometries to the collection.
     * Updates the bounding box to encompass all geometries.
     *
     * @param geometries The list of geometries to add.
     * @return The Geometries object with the added geometries.
     */
    public Geometries add(List<Intersectable> geometries) {
        if (geometries.size() == 0) return this;
        Point maxBbox = bbox.getMax();
        Point minBbox = bbox.getMin();
        boolean inf = bbox.isInfinite();

        for (Intersectable geometry : geometries) {
            bodies.add(geometry);
            if (geometry.getBBox().isInfinite()) {
                inf = true;
            }

            maxBbox = Point.createMaxPoint(maxBbox, geometry.getBBox().getMax());
            minBbox = Point.createMinPoint(minBbox, geometry.getBBox().getMin());
        }

        bbox = new AABB(minBbox, maxBbox).setInfinity(inf);
        return this;
    }

    /**
     * Builds a Bounding Volume Hierarchy (BVH) for the geometries.
     * Divides the geometries into sublists based on the SAH cost, and recursively builds BVH for each sublist.
     *
     * @return The root Geometries object representing the BVH.
     */
    public Geometries buildBVH() {
        if (bodies.size() <= 2) return this;
        double[] arr = decideHowToSplitBySAH();
        List<List<Intersectable>> split = splitByAxis(axes.get((int) arr[0]), arr[1]);
        bodies = new LinkedList<>();
        add(new Geometries().add(split.get(0)).buildBVH(), new Geometries().add(split.get(1)).buildBVH());
        if (split.size() == 3) add(split.get(2));
        return this;
    }

    /**
     * Evaluates the Surface Area Heuristic (SAH) cost for a given splitting position.
     *
     * @param func The function to evaluate for each intersectable.
     * @param pos  The splitting position.
     * @return The SAH cost for the given splitting position.
     */
    double evaluateSAH(Function<Intersectable, Double> func, double pos) {
        Geometries left = new Geometries();
        Geometries right = new Geometries();
        for (Intersectable i : bodies) {
            if (i.getBBox().isInfinite()) {
                continue;
            }
            if (func.apply(i) < pos) {
                left.add(i);
            } else {
                right.add(i);
            }
        }
        double cost = left.bodies.size() * left.getBBox().AABBArea() + right.bodies.size() * right.getBBox().AABBArea();
        return cost > 0 ? cost : Double.POSITIVE_INFINITY;
    }

    /**
     * Decides the best axis and position to split the geometries using the Surface Area Heuristic (SAH).
     *
     * @return An array containing the best axis and position to split.
     */
    double[] decideHowToSplitBySAH() {
        int bestAxis = -1;
        double bestPos = 0, bestCost = Double.POSITIVE_INFINITY;
        for (int axis = 0; axis < 3; axis++) {
            for (Intersectable i : bodies) {
                double candidatePos = axes.get(axis).apply(i);
                double cost = evaluateSAH(axes.get(axis), candidatePos);
                if (cost < bestCost) {
                    bestPos = candidatePos;
                    bestAxis = axis;
                    bestCost = cost;
                }
            }
        }
        return new double[]{bestAxis, bestPos};
    }

    /**
     * Splits the geometries into three lists based on a given splitting function and position.
     *
     * @param func The function to determine the splitting position for each intersectable.
     * @param pos  The splitting position.
     * @return A list containing three lists of intersectables: left, right, and infinite.
     */
    List<List<Intersectable>> splitByAxis(Function<Intersectable, Double> func, double pos) {
        List<List<Intersectable>> list = new ArrayList<>(Arrays.asList(
                new ArrayList<>(), new ArrayList<>()
        ));

        for (Intersectable i : bodies) {
            if (i.getBBox().isInfinite()) {
                if (list.size() == 2) list.add(new ArrayList<>());
                list.get(2).add(i);
            } else if (func.apply(i) < pos) {
                list.get(0).add(i);
            } else {
                list.get(1).add(i);
            }
        }

        return list;
    }

    // Getters, setters, and other methods

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDis) {
        if (!bbox.intersect(ray)) return null;
        List<GeoPoint> intersections = null;
        for (Intersectable body : bodies) {
            List<GeoPoint> bodyIntersections = body.findIntersections(ray, maxDis);
            if (bodyIntersections != null) {
                if (intersections == null) intersections = new ArrayList<>();
                intersections.addAll(bodyIntersections);
            }
        }
        return intersections;
    }

    @Override
    public AABB getBBox() {
        return bbox;
    }

    @Override
    public String toString() {
        return "Geometries{" +
                "bodies=" + bodies +
                ", bbox=" + bbox +
                '}';
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        return null;
    }
}
