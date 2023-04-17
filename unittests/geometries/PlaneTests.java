package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

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
        assertEquals(0, n.dotProduct(p2.subtract(p3)), 0.0000001,"ERROR: The normal is not orthogonal to p1-p2");
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
}