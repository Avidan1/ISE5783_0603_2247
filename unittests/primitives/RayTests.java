package primitives;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Testing Ray Class
 **/

class RayTests {

    /**
     * Test method for {@link primitives.Ray#Ray(Point, Vector)}.
     */

    @Test
    void testGetPoint() {
        Ray ray = new Ray(new Point(1, 2, 3), new Vector(1, 0, 0));
        // ============ Equivalence Partitions Tests ==============
        // TC01: The point is on the other way of the ray
        assertEquals(new Point(0, 2, 3), ray.getPoint(-1), "ERROR: getPoint() does not return the correct point");
        // TC02: The point is ahead of the ray
        assertEquals(new Point(2, 2, 3), ray.getPoint(1), "ERROR: getPoint() does not return the correct point");
        // ============ boundary values tests ==================
        // TC03: The point is on the ray
        assertEquals(new Point(1, 2, 3), ray.getPoint(0), "ERROR: getPoint() does not return the correct point");
    }

    /**
     * Test method for {@link primitives.Ray#findClosestPoint(List)}.
     */
    @Test
    void testFindClosestPoint() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: The closesest point is in the middle of the list
        Ray ray = new Ray(new Point(1, 2, 3), new Vector(1, 0, 0));
        List<Point> points = List.of(new Point(6, 2, 3), new Point(1.5, 2, 3), new Point(2, 2, 3));
        assertEquals(new Point(1.5, 2, 3), ray.findClosestPoint(points), "ERROR: findClosestPoint() does not return the correct point");
        // ================== Boundary Values Tests ==================
        // TC02: The list is empty
        assertNull(ray.findClosestPoint(List.of()), "ERROR: findClosestPoint() does not return null when the list is empty");
        // TC03: The closesest point is the first point in the list
        points = List.of(new Point(1.5, 2, 3), new Point(6, 2, 3), new Point(2, 2, 3));
        assertEquals(new Point(1.5, 2, 3), ray.findClosestPoint(points), "ERROR: findClosestPoint() does not return the correct point");
        // TC04: The closesest point is the last point in the list
        points = List.of(new Point(6, 2, 3), new Point(2, 2, 3), new Point(1.5, 2, 3));
        assertEquals(new Point(1.5, 2, 3), ray.findClosestPoint(points), "ERROR: findClosestPoint() does not return the correct point");
    }
}