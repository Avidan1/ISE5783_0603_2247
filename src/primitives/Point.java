package primitives;
/**
* Class Point is the basic class representing a point~
 * 3-Dimensional coordinate system.
 * @author Ziv Farjun and Avidan Maatuk
 */
public class Point {
      Double3 xyz;

     @Override
     public boolean equals(Object obj) {
          if (this == obj) return true;
          if (obj instanceof Point other)
               return this.xyz.equals(other.xyz);
          return false;
     }

     @Override
     public String toString() {
          return "Point{" +
                  xyz.toString() +
                  '}';
     }

     Point(Double3 xyz) {
          this.xyz =xyz;
     }
     public Point(double x,double y,double z) {
          this.xyz = new Double3(x,y,z);
     }

     public Vector subtract(Point point1){
          return new Vector(point1.xyz.subtract(point1.xyz));
     }
     public Point add(Vector vector){
          return new Point(vector.xyz.add(vector.xyz));
     }

     public double distanceSquared(Point point){
          return ((this.xyz.d1-point.xyz.d1)*(this.xyz.d1-point.xyz.d1)+
                  (this.xyz.d2-point.xyz.d2)*(this.xyz.d2-point.xyz.d2)+
                  (this.xyz.d3-point.xyz.d3)*(this.xyz.d3-point.xyz.d3));
     }
     public double distance(Point point){
          return Math.sqrt(distanceSquared(point));
     }
}