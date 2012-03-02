package kinect.geometry;

/**
 * Created by IntelliJ IDEA.
 * User: John
 * Date: 23/02/12
 * Time: 15:40
 * <p/>
 * Simple Plane class
 * Uses a point on the plane + normal representation
 */
public class Plane {

    public Position point_on_plane = new Position();
    public Vector normal = new Vector();

    public double getDistanceFromPlane(Position p) {

        // take vector from a point on the plane to the position
        // project it onto the normal and take the projection length
        Vector point_on_plane_to_position = new Vector(point_on_plane, p);
        return point_on_plane_to_position.lengthOfProjectionOnto(normal);

    }

    @Override
    public String toString() {
        return "Plane{" +
                "point_on_plane=" + point_on_plane +
                ", normal=" + normal +
                '}';
    }
}
