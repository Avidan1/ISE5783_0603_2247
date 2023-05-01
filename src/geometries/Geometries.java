package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.LinkedList;
import java.util.List;

/**
 * Intersectable interface represents a geometry in 3D Cartesian coordinate system
 */
public class Geometries implements Intersectable {
    List<Intersectable> _intersectables;

    public void Geometries() {
        _intersectables = new LinkedList<Intersectable>();
    }

    public void Geometries(Intersectable... geometries) {
        _intersectables = new LinkedList<Intersectable>();
        add(geometries);
    }

    public void add(Intersectable... geometries) {
        for (Intersectable geometry : geometries) {
            _intersectables.add(geometry);
        }
    }

    public List<Point> findIntersections(Ray ray) {
        List<Point> result = new LinkedList<>();
        for (var geometry : _intersectables) {
            List<Point> temp = geometry.findIntersections(ray);
            if (temp != null) {
                result.addAll(temp);
            }
        }
        return result;

    }

}

