package geometries;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import primitives.*;
/**
 * Testing Cylinder
 *
 * @author Avidan and Ziv
 */
class CylinderTests {
/**
     * Test method for {@link geometries.Cylinder#getNormal(primitives.Point)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: on round surface
        Cylinder cylinder = new Cylinder(new Ray(new Point(0, 0, 0), new Vector(0, 0, 1)), 1, 10);
        //point (0.5, sqrt(3)/2, 5) is on the round surface
        Vector res = cylinder.getNormal(new Point(0.5,Math.sqrt(3)/2.d , 5));
        //check if it is orthogonal to the point
        if(!isZero(res.dotProduct(new Vector(0.5,Math.sqrt(3)/2.d , 5))))
            fail("ERROR: Normal is not the orthogonal to ray");

        // TC02: on the base
        res = cylinder.getNormal(new Point(0.25, 0.5, 0));
        if(!res.equals(cylinder.axisRay.getDir()) && !res.equals(cylinder.axisRay.getDir().scale(-1)))
            fail("ERROR: Normal of base is not the same as ray");
        // TC03: on the top
        res = cylinder.getNormal(new Point(0.25, 0.5, 10));
        if(!res.equals(cylinder.axisRay.getDir()) && !res.equals(cylinder.axisRay.getDir().scale(-1)))
            fail("ERROR: Normal of top is not the same as ray");
        // =============== Boundary Values Tests ==================
        // TC04: on the axis
        res = cylinder.getNormal(new Point(0, 0, 5));
        if(!res.equals(cylinder.axisRay.getDir()) && !res.equals(cylinder.axisRay.getDir().scale(-1)))
            fail("ERROR: Normal of axis is not the same as ray");
        // TC05: on the axis ray
        res = cylinder.getNormal(new Point(0, 0, 0));
        if(!res.equals(cylinder.axisRay.getDir()) && !res.equals(cylinder.axisRay.getDir().scale(-1)))
            fail("ERROR: Normal of axis ray is not the same as ray");
        // TC06: on the axis ray's head
        res = cylinder.getNormal(new Point(0, 0, 10));
        if(!res.equals(cylinder.axisRay.getDir()) && !res.equals(cylinder.axisRay.getDir().scale(-1)))
            fail("ERROR: Normal of axis ray's head is not the same as ray");
    }
}