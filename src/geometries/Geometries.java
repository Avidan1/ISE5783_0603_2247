package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.LinkedList;
import java.util.List;
/**
 * Intersectable interface represents a geometry in 3D Cartesian coordinate system
 */
public class Geometries implements intersectable

interface intersectable {
    private List<Intersectable> _intersectables;

    public void Geometries() {
        _intersectables = new LinkedList<>();
    }

    public void Geometries(Intersectable... geometries) {
        _intersectables = new LinkedList<>();
        add(geometries);
    }

    public void add(Intersectable... geometries) {
        for (Intersectable geometry : geometries) {
            _intersectables.add(geometry);
        }
    }

    public static List<Point> findIntsersections(Ray ray){
        return null;
    }

}

