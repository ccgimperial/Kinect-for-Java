package kinect.skeleton;

import kinect.geometry.Plane;
import kinect.geometry.Position;

/**
 * Created by IntelliJ IDEA.
 * User: John
 * Date: 23/02/12
 * Time: 14:55
 * <p/>
 * Represenation of a particular Joint on a skeleton
 * Provides an abstraction for getting at the skeleton
 * data in the Kinect for a particular joint.
 */
public class Joint {

    private Skeleton skeleton;
    private int JointID;

    public Joint(Skeleton skeleton, int jointID) {
        this.skeleton = skeleton;
        this.JointID = jointID;
    }

    public Position getPosition() {
        return skeleton.getJointPosition(JointID);
    }

    public boolean isTracking() {
        return skeleton.isTrackingJoint(JointID);
    }

    public double getHeight(Plane floor) {
        return floor.getDistanceFromPlane(getPosition());
    }

    public Skeleton getSkeleton() {
        return skeleton;
    }
}
