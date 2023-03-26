package primitives;

public class Ray {
 public Point p0;
 public Vector dir;

    @java.lang.Override
    public java.lang.String toString() {
        return "Ray{" +
                "p0=" + p0 +
                ", dir=" + dir +
                '}';
    }

    public Point getP0() {
        return p0;
    }

    public Vector getDir() {
        return dir;
    }

    public boolean equals(Object obj) { //TODO - FIT
        if (this == obj) return true;
        if (obj instanceof Ray other)
            return (this.p0.equals(other.p0) && this.dir.equals(other.dir));
        return false;
    }

    public Ray(Point p0, Vector dir) {
        this.p0 = p0;
        this.dir = dir.normalize() ;
    }
}