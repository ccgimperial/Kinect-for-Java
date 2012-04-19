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

    // POSITION_TRACKING_STATE
    public static final int POSITION_NOT_TRACKED = 0;
    public static final int POSITION_INFERRED = 1;
    public static final int POSITION_TRACKED = 2;


    private Skeleton skeleton;
    public int JointID;

    public Joint(Skeleton skeleton, int jointID) {
        this.skeleton = skeleton;
        this.JointID = jointID;
    }

    public Position getPosition() {
        Position p = new Position();
        p.x = getJointPositionByIndex(skeleton.ID, JointID, 0);
        p.y = getJointPositionByIndex(skeleton.ID, JointID, 1);
        p.z = getJointPositionByIndex(skeleton.ID, JointID, 2);
        return p;
    }

    public boolean isTracking() {
        return skeleton.isTracking() && getTrackingState() == POSITION_TRACKED;
    }

    public double getHeight(Plane floor) {
        return floor.getDistanceFromPlane(getPosition());
    }

    public Skeleton getSkeleton() {
        return skeleton;
    }

    public int getTrackingState() {
        return getJointTrackingState(skeleton.ID,JointID);
    }

    @Override
    public String toString() {
        return "Joint{" + JointID + "," + getTrackingState() + "," + getPosition() + '}';
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////
    // NATIVE INTERFACE
    //////////////////////////////////////////////////////////////////////////////////////////////////
    static native int getJointTrackingState(int SkeletonID, int JointID);
    static native float getJointPositionByIndex(int SkeletonID, int JointID, int PositionIndex);


}
