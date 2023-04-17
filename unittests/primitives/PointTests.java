package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for primitives.Point class
 *
 * @author Avidan and Ziv
 */
class PointTests {
    Vector v1 = new Vector(1, 2, 3);
  Point p1 = new Point(1, 2, 3);
  Point p2 = new Point(2, 4, 6);
    /**
     * Test method for {@link primitives.Point#subtract(primitives.Point)}.
     */
    @Test
    void testSubtract() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that the subtract method works correctly
        assertEquals(v1, p2.subtract(p1), "ERROR: Point subtract does not work correctly");

        // =============== Boundary Values Tests ==================
        //TC11: Test that the subtract method works correctly when the point is the same
        assertThrows(IllegalArgumentException.class, () -> p1.subtract(p1), "ERROR: Point subtract from it self must throw an exception");


    }

    /**
     * Test method for {@link primitives.Point#add(primitives.Vector)}.
     */
    @Test
    void testAdd() {
        // ============ Equivalence Partitions Tests ==============
        assertEquals(p2,p1.add(v1), "ERROR: Point add does not work correctly");

        // =============== Boundary Values Tests ==================
        //There are no boundary values for this test
    }

    /**
     * Test method for {@link primitives.Point#distanceSquared(primitives.Point)}.
     */
    @Test
    void testDistanceSquared() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that the distanceSquared method works correctly
        assertEquals(14d, p1.distanceSquared(p2), 0.0001, "ERROR: Point distanceSquared does not work correctly");

        // =============== Boundary Values Tests ==================
        //TC11: Test that the distanceSquared method works correctly when the point is the same
        assertEquals(0d, p1.distanceSquared(p1), 0.0001, "ERROR: Point distanceSquared from itself does not work correctly");
    }

    /**
     * Test method for {@link primitives.Point#distance(primitives.Point)}.
     */
    @Test
    void testDistance() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that the distance method works correctly
        assertEquals(Math.sqrt(14), p1.distance(p2), 0.0001, "ERROR: Point distance does not work correctly");

        // =============== Boundary Values Tests ==================
        //TC11: Test that the distance method works correctly when the point is the same
        assertEquals(0d, p1.distance(p1), 0.0001, "ERROR: Point distance from itself does not work correctly");
    }
}