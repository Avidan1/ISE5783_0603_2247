package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static primitives.Util.isZero;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for geometries.Plane class
 *
 * @author Avidan and Ziv
 */
class PlaneTests {
    /**
     * Test method for {@link geometries.Plane#getNormal(primitives.Point)}.
     */
    @Test
    void testGetNormal() {
        // ================== Equivalence Partitions Tests ==================
        // TC01: There is a simple single test here
        Point[] points = {new Point(0, 1, 0), new Point(0, 0, 1), new Point(1, 0, 0)};
        Plane p = new Plane(points[0], points[1], points[2]);
        // ensure there no exception
        assertDoesNotThrow(() -> p.getNormal(new Point(1, 0, 0)), "Bad normal to plane");
        // ensure that the normal is correct
        Vector result = p.getNormal(new Point(0, 0, 1));
        // ensure |result| = 1
        assertEquals(1, result.length(), 0.00000001, "plan's normal is not a unit vector");
        // ensure the result is orthogonal to all the edges
        for (int i = 0; i < 3; ++i) // todo - check if it is correct
            assertTrue(isZero(result.dotProduct(points[i].subtract(points[i == 0 ? 3 : i - 1]))),
                    "Polygon's normal is not orthogonal to one of the edges");
    }

    @Test
    void testConstructor() {
        // ================== Equivalence Partitions Tests ==================
        // TC01: check that the constructor is correct
        try {
            Plane p = new Plane(new Point(1, 1, 1), new Point(2, 2, 2), new Point(3, 3, 3));
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct plane");
        }

        // ================== Boundary Values Tests ==================
        // last point is the same as the first point
        assertThrows(IllegalArgumentException.class, () -> new Plane(new Point(1, 1, 1), new Point(2, 2, 2), new Point(1, 1, 1)));
        // TC12 Co-LOCATED points
        assertThrows(IllegalArgumentException.class, () -> new Plane(new Point(1, 1, 1), new Point(1, 1, 1), new Point(1, 1, 1)));
        //todo: add more TC
    }

}