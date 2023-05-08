package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RayTests {

    @Test
    void testGetPoint() {
        Ray ray = new Ray(new Point(1, 2, 3), new Vector(1, 0, 0));
        // ============ Equivalence Partitions Tests ==============
        // TC01: The point is on the ray
        assertEquals(new Point(1, 2, 3), ray.getPoint(0), "ERROR: getPoint() does not return the correct point");
        // TC02: The point is ahead of the ray
        assertEquals(new Point(2, 2, 3), ray.getPoint(1), "ERROR: getPoint() does not return the correct point");

    }
}