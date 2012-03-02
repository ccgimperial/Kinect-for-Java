package kinect.skeleton;

import kinect.geometry.Position;

public class Skeleton {

    public static final int SKELETON_COUNT = 6;
    // TRACKING_STATE
    public static final int SKELETON_NOT_TRACKED = 0;
    public static final int SKELETON_POSITION_ONLY = 1;
    public static final int SKELETON_TRACKED = 2;
    // POSITION_TRACKING_STATE
    public static final int POSITION_NOT_TRACKED = 0;
    public static final int POSITION_INFERRED = 1;
    public static final int POSITION_TRACKED = 2;
    // POSITION_INDEX
    public static final int POSITION_HIP_CENTER = 0;
    public static final int POSITION_SPINE = 1;
    public static final int POSITION_SHOULDER_CENTER = 2;
    public static final int POSITION_HEAD = 3;
    public static final int POSITION_SHOULDER_LEFT = 4;
    public static final int POSITION_ELBOW_LEFT = 5;
    public static final int POSITION_WRIST_LEFT = 6;
    public static final int POSITION_HAND_LEFT = 7;
    public static final int POSITION_SHOULDER_RIGHT = 8;
    public static final int POSITION_ELBOW_RIGHT = 9;
    public static final int POSITION_WRIST_RIGHT = 10;
    public static final int POSITION_HAND_RIGHT = 11;
    public static final int POSITION_HIP_LEFT = 12;
    public static final int POSITION_KNEE_LEFT = 13;
    public static final int POSITION_ANKLE_LEFT = 14;
    public static final int POSITION_FOOT_LEFT = 15;
    public static final int POSITION_HIP_RIGHT = 16;
    public static final int POSITION_KNEE_RIGHT = 17;
    public static final int POSITION_ANKLE_RIGHT = 18;
    public static final int POSITION_FOOT_RIGHT = 19;
    public static final int POSITION_COUNT = 20;


//    typedef struct _FRAME
//    {
//        LARGE_INTEGER         liTimeStamp;            8
//        DWORD                 dwFrameNumber;              4
//        DWORD                 dwFlags;                     4
//        Vector4              vFloorClipPlane;              16
//        Vector4              vNormalToGravity;             16
//        DATA     SkeletonData[6];             6 * 436
//    } FRAME;

    //    typedef struct _DATA
//    {
//        TRACKING_STATE eTrackingState;   4
//        DWORD dwTrackingID;                             4
//        DWORD dwEnrollmentIndex_NotUsed;                 4
//        DWORD dwUserIndex;                                4
//        Vector4 Position;                                  16
//        Vector4 SkeletonPositions[20];                       16 * 20
//        POSITION_TRACKING_STATE eSkeletonPositionTrackingState[20];  4*20
//        DWORD dwQualityFlags;    4
//    } DATA;

    //////////////////////////////////////////////////////////////////////////////////////
    // NATIVE INTERFACE
    //////////////////////////////////////////////////////////////////////////////////////
    public final static native boolean isTrackingSomeSkeleton();

    public final static native int getTrackedSkeletonId();

    final static native int getSkeletonTrackingState(int SkeletonID);

    final static native int getJointTrackingState(int SkeletonID, int JointID);

    final static native float getJointPositionByIndex(int SkeletonID, int JointID, int PositionIndex);

    final static native float getSkeletonNormalToGravityByIndex(int PositionIndex);

    final static native float getSkeletonFloorClipPlaneByIndex(int PositionIndex);


    //////////////////////////////////////////////////////////////////////////////////////
    // INSTANCE SPECIFIC
    //////////////////////////////////////////////////////////////////////////////////////
    private int SkeletonID;

    public void setSkeletonID(int skeletonID) {
        SkeletonID = skeletonID;
    }

    public Skeleton() {
        setSkeletonID(getTrackedSkeletonId());
    }

    public Skeleton(int skeletonID) {
        setSkeletonID(skeletonID);
    }

    public Position getJointPosition(int JointID) {
        Position p = new Position();
        p.x = getJointPositionByIndex(SkeletonID, JointID, 0);
        p.y = getJointPositionByIndex(SkeletonID, JointID, 1);
        p.z = getJointPositionByIndex(SkeletonID, JointID, 2);
        return p;
    }

    public int getTrackingState() {
        return getSkeletonTrackingState(SkeletonID);
    }

    public boolean isTracking() {
        return getSkeletonTrackingState(SkeletonID) == SKELETON_TRACKED;
    }

    public int getJointTrackingState(int JointID) {
        return getJointTrackingState(SkeletonID, JointID);
    }

    public boolean isTrackingJoint(int JointID) {
        return isTracking()
                && getJointTrackingState(JointID) == POSITION_TRACKED;
    }

    public static Position getNormalToGravity_NOT_WORKING() {
        Position p = new Position();
        p.x = getSkeletonNormalToGravityByIndex(0);
        p.y = getSkeletonNormalToGravityByIndex(1);
        p.z = getSkeletonNormalToGravityByIndex(2);
        return p;
    }

    public static Position getFloorClipPlane_NOT_WORKING() {
        Position p = new Position();
        p.x = getSkeletonFloorClipPlaneByIndex(0);
        p.y = getSkeletonFloorClipPlaneByIndex(1);
        p.z = getSkeletonFloorClipPlaneByIndex(2);
        return p;
    }


}
