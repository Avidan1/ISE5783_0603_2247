package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;
/**
 * Testing Plane
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
        Plane plane = new Plane(new Point(1, 2, 3), new Point(4, 5, 6), new Point(7, 8, 9));
        // ensure there are no exceptions
        assertDoesNotThrow(() -> plane.getNormal(new Point(1, 2, 3)), "");
        // ensure the normal is correct
        assertTrue(plane.getNormal(new Point(1, 2, 3)).equals(new Vector(0, 0, 1)), "ERROR: Bad normal");
        // ensure the normal length is 1
        assertTrue(isZero(plane.getNormal().length() - 1), "ERROR: Bad normal!=1");
        // ensure there are no exceptions
        assertDoesNotThrow(() -> plane.getNormal(new Point(1, 2, 3)), "");
        // ensure the normal is correct with the same points

    }
    /**
     * Test method for {@link geometries.Plane#Plane(primitives.Point, primitives.Point, primitives.Point)}.
     */
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that the constructor works correctly
        assertDoesNotThrow(() -> new Plane(new Point(0, 0  , 1), new Point(0, 1, 0), new Point(1, 0, 0)), "ERROR: The constructor does not work correctly");
        // =============== Boundary Values Tests ==================
        // TC02: Test that the constructor throws an exception when the points are on the same line
        assertThrows(IllegalArgumentException.class, () -> new Plane(new Point(1, 0, 0), new Point(2, 0, 0), new Point(3, 0, 0)), "ERROR: The constructor does not throw an exception when the points are on the same line");
        // TC03: Test that the constructor throws an exception when the points are the same
        assertThrows(IllegalArgumentException.class, () -> new Plane(new Point(1, 0, 0), new Point(1, 0, 0), new Point(1, 2, 0)), "ERROR: The constructor does not throw an exception when the points are the same");
        assertThrows(IllegalArgumentException.class, () -> new Plane(new Point(0, 2, 0), new Point(1, 0, 0), new Point(1, 0, 0)), "ERROR: The constructor does not throw an exception when the points are the same");
    }}