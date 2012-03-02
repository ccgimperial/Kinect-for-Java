package kinect.geometry;

/**
 * Created by IntelliJ IDEA.
 * User: John
 * Date: 23/02/12
 * Time: 15:42
 * <p/>
 * Simple utility vector class
 */
public class Vector extends Position {

    Double length = null;

    public Vector() {
    }

    public Vector(Position from, Position to) {
        x = to.x - from.x;
        y = to.y - from.y;
        z = to.z - from.z;
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


}
