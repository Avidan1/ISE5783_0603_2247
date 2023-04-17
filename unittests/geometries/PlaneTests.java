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
        Plane plane = new Plane(new Point(1, 2, 3), new Vector(4, 5, 6));
        // ensure there are no exceptions
        assertDoesNotThrow(() -> plane.getNormal(new Point(1, 2, 3)), "");
        // ensure the normal is correct
        assertEquals(new Vector(4, 5, 6).normalize(), plane.getNormal(), "ERROR: Bad normal to plane");
        // ensure the normal length is 1
        assertTrue(isZero(plane.getNormal().length() - 1), "ERROR: Bad normal!=1");
        // ensure there are no exceptions
        assertDoesNotThrow(() -> plane.getNormal(new Point(1, 2, 3)), "");
        // ensure the normal is correct with the same points

    }
}