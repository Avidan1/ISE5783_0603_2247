package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
/**
 * Unit tests for primitives.Vector class
 *
 * @author Avidan and Ziv
 */
class VectorTests {
    Vector v1 = new Vector(1, 2, 3);
    Vector v2 = new Vector(-2, -4, -6);
    Vector v3 = new Vector(0, 3, -2);

    /**
     * Test method for {@link primitives.Vector#add(primitives.Vector)}.
     */
    @Test
    void testAdd() {
        Vector v1 = new Vector(1.0, 2.0, 3.0);
        Vector v2 = new Vector(4.0, 5.0, 6.0);
        Vector expected = new Vector(5.0, 7.0, 9.0);
        Vector result = v1.add(v2);
        assertEquals(expected, result, "ERROR: Vector + Vector does not work correctly");
    }

    /**
     * Test method for {@link primitives.Vector#scale} (primitives.Vector)}.
     */
    @Test
    void testScale() {
        Vector v1 = new Vector(1.0, 2.0, 3.0);
        double k = 2.0;
        Vector expected = new Vector(2.0, 4.0, 6.0);
        Vector result = v1.scale(k);
        assertEquals(expected, result);
        assertEquals(new Vector(2, 4, 6), v1.scale(2), "ERROR: Vector * Vector does not work correctly");

    }

    /**
     * Test method for {@link primitives.Vector#dotProduct} (primitives.Vector)}.
     */
    @Test
    void testDotProduct() {
        assertEquals(0, v1.dotProduct(v3), "ERROR: dotProduct() for orthogonal vectors is not zero");
        assertEquals(-28, v1.dotProduct(v2), "ERROR: dotProduct() wrong value");
    }

    /**
     * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
     */
    @Test
    public void testCrossProduct() {
        Vector v1 = new Vector(1, 2, 3);
        // ============ Equivalence Partitions Tests ==============
        Vector v2 = new Vector(0, 3, -2);
        Vector vr = v1.crossProduct(v2);
        // TC01: Test that length of cross-product is proper (orthogonal vectors taken
        // for simplicity)
        assertEquals(v1.length() * v2.length(), vr.length(), 0.00001, "crossProduct() wrong result length");
        // TC02: Test cross-product result orthogonality to its operands
        assertTrue(isZero(vr.dotProduct(v1)), "crossProduct() result is not orthogonal to 1st operand");
        assertTrue(isZero(vr.dotProduct(v2)), "crossProduct() result is not orthogonal to 2nd operand");
        // =============== Boundary Values Tests ==================
        // TC11: test zero vector from cross-productof co-lined vectors
        Vector v3 = new Vector(-2, -4, -6);
        assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(v3),
                "crossProduct() for parallel vectors does not throw an exception");

    }

    /**
     * Test method for {@link primitives.Vector#lengthSquared()}.
     */
    @Test
    void testLengthSquared() {
        // ============ Equivalence Partitions Tests ==============

        assertEquals(14, v1.lengthSquared(), "ERROR: lengthSquared() wrong value");
    }

    /**
     * Test method for {@link primitives.Vector#length()}.
     */
    @Test
    void testLength() {
        assertEquals(5, new Vector(0, 3, 4).length(), "ERROR: length() wrong value");
    }

    /**
     * Test method for {@link primitives.Vector#normalize()}.
     */
    @Test
    void testNormalize() {
        assertEquals(new Vector(0.26726, 0.53452, 0.80178), v1.normalize(), "ERROR: normalize() result is not a unit vector");
    }
}