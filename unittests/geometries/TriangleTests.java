package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing Triangle
 *
 * @author Avidan and Ziv
 */
class TriangleTests {
    /**
     * Test method for {@link geometries.Triangle#getNormal(primitives.Point)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that the normal is correct and ensure there are no exceptions
        try {
            Triangle triangle = new Triangle(new Point(1, 2, 3), new Point(2, 3, 4), new Point(3, 4, 5));
            assertDoesNotThrow(() -> triangle.getNormal(new Point(1, 2, 3)), "");
        } catch (Exception e) {
            fail("Failed constructing a triangle");
        }
    }
}