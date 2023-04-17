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
        // ============ Equivalence Partitions Tests ==============

        // TC01: Test that the add method works correctly
        assertEquals(new Vector(-1, -2, -3), v1.add(v2), "ERROR: Vector addition does not work correctly");

        // =============== Boundary Values Tests ==================
        assertThrows(IllegalArgumentException.class, () -> v1.add(new Vector(-1, -2, -3)), "ERROR: Vector 0 need to throw an exception");
    }

    /**
     * Test method for {@link primitives.Vector#scale} (primitives.Vector)}.
     */
    @Test
    void testScale() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that the scale method works correctly
        assertEquals(new Vector(2, 4, 6), v1.scale(2), "ERROR: Vector scale does not work correctly");

        // =============== Boundary Values Tests ==================
        //TC02: Test that the scale method works correctly when the vector is the same
        assertThrows(IllegalArgumentException.class, () -> v1.scale(0), "ERROR: Vector 0 need to throw an exception");
    }

    /**
     * Test method for {@link primitives.Vector#dotProduct} (primitives.Vector)}.
     */
    @Test
    void testDotProduct() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that the dotProduct method works correctly
        assertEquals(-28d, v1.dotProduct(v2), "ERROR: Vector dotProduct does not work correctly");
        //TC02: Test that the dotProduct method works correctly when the vector is the same
        assertEquals(14d, v1.dotProduct(v1), "ERROR: Vector dotProduct does not work correctly");
        // =============== Boundary Values Tests ==================
        //TC11: Test orthogonal vectors for zero
        assertEquals(0d, v1.dotProduct(v3), "ERROR: dotProduct() for orthogonal vectors is not zero");

    }

    /**
     * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
     */
    @Test
    public void testCrossProduct() {
        Vector vr = v1.crossProduct(v3);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that length of cross-product is proper (orthogonal vectors take for simplicity
        assertEquals(v1.length() * v3.length(), vr.length(), 0.00001, "crossProduct() wrong result length");

        // TC02: Test cross-product result orthogonality to its operands
        assertTrue(isZero(vr.dotProduct(v1)), "crossProduct() result is not orthogonal to 1st operand");
        assertTrue(isZero(vr.dotProduct(v3)), "crossProduct() result is not orthogonal to 2nd operand");

        // =============== Boundary Values Tests ==================
        // TC11: test zero vector from cross-productof co-lined vectors
        assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(v2), "crossProduct() for parallel vectors does not throw an exception");

    }

    /**
     * Test method for {@link primitives.Vector#lengthSquared()}.
     */
    @Test
    void testLengthSquared() {
        // ============ Equivalence Partitions Tests ==============
        assertEquals(14d, v1.lengthSquared(),0.0001, "ERROR: lengthSquared() wrong value");
        // =============== Boundary Values Tests ==================
        //there is no boundary values for this method
    }

    /**
     * Test method for {@link primitives.Vector#length()}.
     */
    @Test
    void testLength() {
        // ============ Equivalence Partitions Tests ==============
        assertEquals(Math.sqrt(14), v1.length(),0.0001, "ERROR: length() wrong value");
        // =============== Boundary Values Tests ==================
        //there is no boundary values for this method
    }

    /**
     * Test method for {@link primitives.Vector#normalize()}.
     */
    @Test
    void testNormalize() {
        Vector v = new Vector(0, 3, 4);
        Vector n = v.normalize();
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that the normalize method works correctly
        assertEquals(new Vector(0, 0.6, 0.8), n, "ERROR: normalize result is not a correct vector");
        // TC02: Test the unit length of the vector
        assertEquals(1d, n.lengthSquared(), 0.0001, "ERROR: normalized result is not a unit vector");
        // =============== Boundary Values Tests ==================
        //there is no boundary values for this method
    }
}