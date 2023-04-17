package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

class PlaneTests {

    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that the normal is correct
        Plane plane = new Plane(new Point(1, 2, 3), new Point(4, 5, 6), new Point(7, 8, 9));
        // ensure there are no exceptions
        assertDoesNotThrow(() -> plane.getNormal(new Point(1, 2, 3)), "");
        // ensure the normal is correct
        assertEquals(new Vector(0, 0, 1), plane.getNormal(new Point(1, 2, 3)), "ERROR: Bad normal");
        // ensure the normal length is 1
        assertTrue(isZero(plane.getNormal().length() - 1), "ERROR: Bad normal!=1");
        // ensure there are no exceptions
        assertDoesNotThrow(() -> plane.getNormal(new Point(1, 2, 3)), "");
        // ensure the normal is correct with the same points

    }
}