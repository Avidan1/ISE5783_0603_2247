package geometries;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import primitives.*;
/**
 * Testing Tube
 *
 * @author Avidan and Ziv
 */
class TubeTests {
/**
     * Test method for {@link geometries.Tube#getNormal(primitives.Point)}.
     */
    @Test
    void testGetNormal() {
        //============ Equivalence Partitions Tests ==============
        // TC01: simple test- check normal is a unit vector
        Tube tst = new Tube(new Ray(new Point(0, 0, 0), new Vector(0, 0, 1)), 1);
        assertEquals(1 , tst.getNormal(new Point(0, 1, 2)).length(),
                0.00001, "ERROR: Normal Length wrong value");
        // TC02: check normal is orthogonal to the ray
        assertTrue(Util.isZero(tst.getNormal(new Point(0, 1, 2)).dotProduct(new Vector(0, 0, 1))),
                "ERROR: Normal is not orthogonal to the ray");
        // TC03: all the point are the same
        assertEquals(tst.getNormal(new Point(0, 1, 2)), tst.getNormal(new Point(0, 1, 2)),
                "ERROR: Normal is not the same");

        // =============== Boundary Values Tests ==================
        //TC05: p - p0 is orthogonal to the ray, p is on the tube in front of p0
        //TODO: check if the normal is correct
    }
}