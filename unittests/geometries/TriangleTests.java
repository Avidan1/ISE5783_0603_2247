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
        // TC01: Test that the normal is correct
        Point p1 = new Point(1, 2, 3);
        Point p2 = new Point(4, 5, 6);
        Point p3 = new Point(7, 8, 0);
        Triangle triangle = new Triangle(p1, p2, p3);
        // ensure there are no exceptions
        assertDoesNotThrow(() -> triangle.getNormal(p1), "");
        // ensure the normal length is 1
        assertEquals(1, triangle.getNormal(p1).length(), 0.0000001, "ERROR: Bad normal!=1");
        assertEquals(0, triangle.getNormal(p1).dotProduct(p1.subtract(p2)), 0.0000001, "ERROR: The normal is not orthogonal to p1-p2");
        assertEquals(0, triangle.getNormal(p1).dotProduct(p2.subtract(p3)), 0.0000001,"ERROR: The normal is not orthogonal to p1-p2");

    }
}