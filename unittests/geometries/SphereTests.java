package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;

import static org.junit.jupiter.api.Assertions.*;

class SphereTests {

    @Test
    void testGetNormal() {
        // ================== Equivalence Partitions Tests ==================
        // TC01: There is a simple single test here
        Point[] points = {new Point(0, 1, 0), new Point(0, 0, 1), new Point(1, 0, 0)};
        Sphere s = new Sphere(1, points[0], points[1], points[2]);
        // ensure that the normal is correct
        assertEquals(s.getNormal(new Point(1, 0, 0)), new Point(1, 0, 0), "Bad normal to sphere");
        // ensure the result is orthogonal to all the edges
        for (int i = 0; i < 3; ++i)
            assertEquals(0, s.getNormal(new Point(1, 0, 0)).dotProduct(points[i]), "Bad normal to sphere");
    }
}