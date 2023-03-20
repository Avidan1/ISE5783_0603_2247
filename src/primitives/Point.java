package primitives;

import java.util.Objects;
import java.util.Vector;

public class Point {
     public Double3 xyz;

     @Override
     public boolean equals(Object o) {
          return false;
     }

     @Override
     public String toString() {
          return "Point{" +
                  "xyz=" + xyz +
                  '}';
     }

     Point(Double3 xyz) {
          this.xyz =xyz;
     }
     public Point(double x,double y,double z) {
          this.xyz = new Double3(x,y,z);
     }

     public Vector subract(Point point1){
          return new Vector(xyz.subtract(point1.xyz));
     }
     public Point subract(Vector vector){
          return new Point(xyz.add(vector.xyz));

     }
}