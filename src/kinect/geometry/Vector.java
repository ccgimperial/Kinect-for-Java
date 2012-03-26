package kinect.geometry;

/**
 * Created by IntelliJ IDEA.
 * User: John
 * Date: 23/02/12
 * Time: 15:42
 * <p/>
 * Simple utility vector class
 */
public class Vector {

    public double x = 0;
    public double y = 0;
    public double z = 0;

    Double length = null;

    public Vector() {
    }

    public Vector(Position from, Position to) {
        x = to.x - from.x;
        y = to.y - from.y;
        z = to.z - from.z;
    }

    public Vector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getLength() {
        if (length == null)
            length = Math.sqrt(x * x + y * y + z * z);
        return length;
    }

    public Vector cross(Vector other) {
        Vector res = new Vector();
        res.x = y * other.z - z * other.y;
        res.y = z * other.x - x * other.z;
        res.z = x * other.y - y * other.x;
        return res;
    }

    public double dot(Vector other) {
        return x * other.x + y * other.y + z * other.z;
    }

    public double angleWithOther(Vector other) {
        // from a.b = |a||b|cos(angle)
        return Math.acos(dot(other) / (getLength() * other.getLength()));
    }

    public double lengthOfProjectionOnto(Vector other) {
        // projection of this onto other is in same 
        // direction as other with length equal to a.b/|b|
        return dot(other) / other.getLength();
    }


    public Vector projectionOnto(Vector other) {
        return (Vector) other.multiply(1 / dot(other));
    }

    public Vector multiply(double multiplicand) {
        Vector r = new Vector();
        r.x = this.x * multiplicand;
        r.y = this.y * multiplicand;
        r.z = this.z * multiplicand;
        return r;
    }

    public Vector divide(double divisor) {
        Vector r = new Vector();
        r.x = this.x / divisor;
        r.y = this.y / divisor;
        r.z = this.z / divisor;
        return r;
    }

    public Vector normalise() {
        return this.divide(this.getLength());
    }
}
