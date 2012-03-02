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

    public Joint(int jointID) {
        skeleton = new Skeleton();
        setJointID(jointID);
    }

    public Joint(int skeletonID, int jointID) {
        skeleton = new Skeleton(skeletonID);
        setJointID(jointID);
    }

    public void setSkeletonID(int SkeletonID) {
        skeleton.setSkeletonID(SkeletonID);
    }

    public void setJointID(int jointID) {
        JointID = jointID;
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
}
