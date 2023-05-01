package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * Testing Plane
 *
 * @author Avidan and Ziv
 */
class PlaneTests {
    /**
     * Test method for {@link geometries.Plane#getNormal(primitives.Point)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that the normal is correct
        Point p1 = new Point(1, 2, 3);
        Point p2 = new Point(4, 5, 6);
        Point p3 = new Point(7, 8, 0);
        Plane plane = new Plane(p1, p2, p3);
        // ensure there are no exceptions
        assertDoesNotThrow(() -> plane.getNormal(p1), "");
        Vector n = plane.getNormal(p1);
        // ensure the normal length is 1
        assertEquals(1, n.length(), 0.0000001, "ERROR: Bad normal!=1");
        assertEquals(0, n.dotProduct(p1.subtract(p2)), 0.0000001, "ERROR: The normal is not orthogonal to p1-p2");
        assertEquals(0, n.dotProduct(p2.subtract(p3)), 0.0000001, "ERROR: The normal is not orthogonal to p1-p2");
    }

    /**
     * Test method for {@link geometries.Plane#Plane(primitives.Point, primitives.Point, primitives.Point)}.
     */
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that the constructor works correctly
        assertDoesNotThrow(() -> new Plane(new Point(0, 0, 1), new Point(0, 1, 0), new Point(1, 0, 0)), "ERROR: The constructor does not work correctly");
        // =============== Boundary Values Tests ==================
        // TC02: Test that the constructor throws an exception when the points are on the same line
        assertThrows(IllegalArgumentException.class, () -> new Plane(new Point(1, 0, 0), new Point(2, 0, 0), new Point(3, 0, 0)), "ERROR: The constructor does not throw an exception when the points are on the same line");
        // TC03: Test that the constructor throws an exception when the points are the same
        assertThrows(IllegalArgumentException.class, () -> new Plane(new Point(1, 0, 0), new Point(1, 0, 0), new Point(1, 2, 0)), "ERROR: The constructor does not throw an exception when the points are the same");
        assertThrows(IllegalArgumentException.class, () -> new Plane(new Point(0, 2, 0), new Point(1, 0, 0), new Point(1, 0, 0)), "ERROR: The constructor does not throw an exception when the points are the same");
    }

    /**
     * Test method for {@link geometries.Plane#findIntersections(primitives.Ray)}.
     */
    @Test
    void testFindIntersections() {

        // ============ Equivalence Partitions Tests ==============

        //TC01: Ray start before the plane, not parallel to plane, intersect the plane one time
        Plane plane = new Plane(new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1));
        assertEquals(List.of(new Point(0.46, 0.22, 0.32)), plane.findIntersections(
                        new Ray(new Point(0, 1.5, 0), new Vector(0.46, -1.28, 0.32))),
                "ERROR: Ray start before the plane, not parallel to plane, intersect the plane one time, not the expected point");
        //TC02: Ray start before the plane, not parallel to plane,do not intersect the plane
        Plane planeSurface = new Plane(new Point(1, 0, 1), new Point(0, 1, 1), new Point(0, 0, 1));
        assertNull(planeSurface.findIntersections(
                        new Ray(new Point(0, 0, 2), new Vector(-1, 2, 1.5))),
                "ERROR: Ray start before the plane, not parallel to plane,should not intersect the plane");

        //=============Boundary Values Tests=================

        //TC03: ray inside the plane, parallel to plane
        assertThrows(IllegalArgumentException.class,
                () -> planeSurface.findIntersections(
                        new Ray(new Point(1, 1, 1), new Vector(0, 0, 0))),
                "ERROR: ray inside the plane, parallel to plane, intersect the plane infinite times, should throw exception");
        //TC04: ray outside the plane parallel to plane - zero intersections point
        assertNull(planeSurface.findIntersections(
                        new Ray(new Point(2, 2, 2), new Vector(1, 1, 0))),
                "ERROR: ray outside the plane parallel to plane, should return null");
        //TC06: ray start before the plane, vertical to plane
        assertEquals(List.of(new Point(1, 1, 1)), planeSurface.findIntersections(
                        new Ray(new Point(1, 1, 0), new Vector(0, 0, 1))),
                "ERROR: ray start before the plane, vertical to plane, should return the point");
        //TC07: ray start inside the plane, vertical to plane
        assertNull(planeSurface.findIntersections(
                        new Ray(new Point(1, 1, 1), new Vector(0, 0, 1))),
                "ERROR: ray start inside the plane, vertical to plane, should return null");
        //TC08: ray start after the plane, vertical to plane
        assertNull(planeSurface.findIntersections(
                        new Ray(new Point(1, 1, 2), new Vector(0, 0, 1))),
                "ERROR: ray start after the plane, vertical to plane, should return null");
        //TC09:ray start inside the plane - not vertical and not parallel to plane
        assertNull(planeSurface.findIntersections(
                        new Ray(new Point(1, 1, 1), new Vector(1, 1, 1))),
                "ERROR: ray start inside the plane - not vertical and not parallel to plane, should return null");
        //TC10: the ray start inside the plane in plane q0 point - not vertical and not parallel to plane
        assertNull(planeSurface.findIntersections(
                        new Ray(new Point(1, 0, 1), new Vector(1, 1, 1))),
                "ERROR: the ray start inside the plane in plane q0 point, should return null");
    }
}