package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import geometries.Tube;

import static org.junit.jupiter.api.Assertions.*;

class TubeTests {

    @Test
    void testGetNormal() {
        // ================== Equivalence Partitions Tests ==================
        //ensure that the normal is correct
        assertEquals(new Tube(new Ray(new Point(0, 0, 0), new Vector(0, 0, 1)), 1).getNormal(new Point(1, 0, 0)), new Vector(1, 0, 0), "Bad normal to tube");
        //ensure the result is orthogonal to all the edges
        assertEquals(0, new Tube(new Ray(new Point(0, 0, 0), new Vector(0, 0, 1)), 1).getNormal(new Point(1, 0, 0)).dotProduct(new Vector(0, 0, 1)), "Bad normal to tube");
    }
}