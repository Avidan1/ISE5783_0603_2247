package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
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
}