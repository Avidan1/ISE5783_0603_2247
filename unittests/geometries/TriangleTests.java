package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing Triangle
 *
 * @author Avidan and Ziv
 */
class TriangleTests {
    /**
     * Test method for {@link geometries.Triangle#getNormal(primitives.Point)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that the normal is correct
        Point p1 = new Point(1, 2, 3);
        Point p2 = new Point(4, 5, 6);
        Point p3 = new Point(7, 8, 0);
        Triangle triangle = new Triangle(p1, p2, p3);
        // ensure there are no exceptions
        assertDoesNotThrow(() -> triangle.getNormal(p1), "");
        // ensure the normal length is 1
        assertEquals(1, triangle.getNormal(p1).length(), 0.0000001, "ERROR: Bad normal!=1");
        assertEquals(0, triangle.getNormal(p1).dotProduct(p1.subtract(p2)), 0.0000001, "ERROR: The normal is not orthogonal to p1-p2");
        assertEquals(0, triangle.getNormal(p1).dotProduct(p2.subtract(p3)), 0.0000001,"ERROR: The normal is not orthogonal to p1-p2");
    }
    /**
     * Test method for {@link geometries.Triangle#findIntersections(Ray)}.
     */
    @Test
    void testFindIntersections() {

        Triangle triangle = new Triangle(new Point(1, 1, 0),
                new Point(-1, 1, 0), new Point(0, 3, 0));

        //==============Equivalence partition tests=================
        //TC01: Ray's line is inside the triangle (1 point)
        assertEquals(List.of(new Point(0, 2.5, 0)),
                triangle.findIntersections(new Ray(new Point(0, 1.5, -1), new Vector(0, 1, 1))),
                "Ray's line out of triangle");
        //TC02: Ray's line is outside against the edge (0 points)
        assertEquals(List.of(new Point(0, 1.5, 0)),
                triangle.findIntersections(new Ray(new Point(0, 1.5, -1), new Vector(0, 0, 1))),
                "Ray's line out of triangle");
        assertEquals(List.of(new Point(0, 1.5, 0)),
                triangle.findIntersections(new Ray(new Point(0, 1.5, 1), new Vector(0, 0, -1))),
                "Ray's line out of triangle");
        //TC02: Ray's line is outside against the edge (0 points)
        assertNull(triangle.findIntersections(new Ray(new Point(0, 0.5, 1), new Vector(0, -1, -1))),
                "Ray's line should be out of triangle");
        //TC03: Ray's line is outside against the vertex (0 points)
        assertNull(triangle.findIntersections(new Ray(new Point(1.5, 0.5, 1), new Vector(2, -1, -1))),
                "Ray's line against vertex should be out of triangle");
        //==============Boundary values tests=================
        //TC04: Ray's line intersection is in the vertex  (0 points)
        assertNull(triangle.findIntersections(new Ray(new Point(1, 1, -1), new Vector(0, 0, 1))),
                "intersection on the edge should be out of triangle");
        //TC05: Ray's line intersection is on the edge (0 points)
        assertNull(triangle.findIntersections(new Ray(new Point(0, 1, -1), new Vector(0, 0, 1))),
                "intersection on the edge should be out of triangle");
        //TC06: intersection is on the edge's continuation (0 points)
        assertNull(triangle.findIntersections(new Ray(new Point(2, 1, -1), new Vector(0, 0, 1))),
                "intersection on the edge continuation should be out of triangle");
    }
}