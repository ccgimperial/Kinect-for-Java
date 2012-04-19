package kinect.stores.skeleton;

import kinect.geometry.Position;
import kinect.skeleton.Joint;

public class JointData {

    public int JOINT_ID;
    public int TRACKING_STATE;
    public Position POSITION;

    public JointData(Joint joint) {
        JOINT_ID = joint.JointID;
        TRACKING_STATE = joint.getTrackingState();
        POSITION = joint.getPosition();
    }

    @Override
    public String toString() {
        return "JointData{" + JOINT_ID + "," + TRACKING_STATE + "," + POSITION + '}';
    }

}
