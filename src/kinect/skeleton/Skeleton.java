package kinect.skeleton;

import kinect.geometry.Position;

public class Skeleton {

    static final int SKELETON_COUNT = 6;
    // TRACKING_STATE
    static final int SKELETON_NOT_TRACKED = 0;
    static final int SKELETON_POSITION_ONLY = 1;
    static final int SKELETON_TRACKED = 2;
    // POSITION_TRACKING_STATE
    static final int POSITION_NOT_TRACKED = 0;
    static final int POSITION_INFERRED = 1;
    static final int POSITION_TRACKED = 2;
    // POSITION_INDEX
    static final int POSITION_HIP_CENTER = 0;
    static final int POSITION_SPINE = 1;
    static final int POSITION_SHOULDER_CENTER = 2;
    static final int POSITION_HEAD = 3;
    static final int POSITION_SHOULDER_LEFT = 4;
    static final int POSITION_ELBOW_LEFT = 5;
    static final int POSITION_WRIST_LEFT = 6;
    static final int POSITION_HAND_LEFT = 7;
    static final int POSITION_SHOULDER_RIGHT = 8;
    static final int POSITION_ELBOW_RIGHT = 9;
    static final int POSITION_WRIST_RIGHT = 10;
    static final int POSITION_HAND_RIGHT = 11;
    static final int POSITION_HIP_LEFT = 12;
    static final int POSITION_KNEE_LEFT = 13;
    static final int POSITION_ANKLE_LEFT = 14;
    static final int POSITION_FOOT_LEFT = 15;
    static final int POSITION_HIP_RIGHT = 16;
    static final int POSITION_KNEE_RIGHT = 17;
    static final int POSITION_ANKLE_RIGHT = 18;
    static final int POSITION_FOOT_RIGHT = 19;
    static final int POSITION_COUNT = 20;

    public Joint FOOT_LEFT;
    public Joint FOOT_RIGHT;
    public Joint ANKLE_LEFT;
    public Joint ANKLE_RIGHT;
    public Joint KNEE_LEFT;
    public Joint KNEE_RIGHT;
    public Joint HIP_LEFT;
    public Joint HIP_RIGHT;
    public Joint HIP_CENTRE;
    public Joint HAND_LEFT;
    public Joint HAND_RIGHT;
    public Joint WRIST_LEFT;
    public Joint WRIST_RIGHT;
    public Joint ELBOW_LEFT;
    public Joint ELBOW_RIGHT;
    public Joint SHOULDER_LEFT;
    public Joint SHOULDER_RIGHT;
    public Joint SHOULDER_CENTRE;
    public Joint SPINE;
    public Joint HEAD;

    ////////////////////////////////////////////////////////////////////
    // ENOUGH STATIC STORAGE FOR 7 SKELETONS
    ////////////////////////////////////////////////////////////////////
    static Skeleton[] skeletons = null;
    static{
        skeletons = new Skeleton[7];
        for (int i = 0; i < skeletons.length; i++) {
            skeletons[i] = new Skeleton(i);
        }
    }
    
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
    public int ID;

    public static Skeleton getSkeleton(int SkeletonID){
        return skeletons[SkeletonID];
    }
    
    
    private Skeleton(int skeletonID) {
        ID = skeletonID;
        FOOT_LEFT = new Joint(this,Skeleton.POSITION_FOOT_LEFT);
        FOOT_RIGHT = new Joint(this,Skeleton.POSITION_FOOT_RIGHT);
        ANKLE_LEFT = new Joint(this,Skeleton.POSITION_ANKLE_LEFT);
        ANKLE_RIGHT = new Joint(this,Skeleton.POSITION_ANKLE_RIGHT);
        KNEE_LEFT = new Joint(this,Skeleton.POSITION_KNEE_LEFT);
        KNEE_RIGHT = new Joint(this,Skeleton.POSITION_KNEE_RIGHT);
        HIP_LEFT = new Joint(this,Skeleton.POSITION_HIP_LEFT);
        HIP_RIGHT = new Joint(this,Skeleton.POSITION_HIP_RIGHT);
        HIP_CENTRE = new Joint(this,Skeleton.POSITION_HIP_CENTER);
        HAND_LEFT = new Joint(this,Skeleton.POSITION_HAND_LEFT);
        HAND_RIGHT = new Joint(this,Skeleton.POSITION_HAND_RIGHT);
        WRIST_LEFT = new Joint(this,Skeleton.POSITION_WRIST_LEFT);
        WRIST_RIGHT = new Joint(this,Skeleton.POSITION_WRIST_RIGHT);
        ELBOW_LEFT = new Joint(this,Skeleton.POSITION_ELBOW_LEFT);
        ELBOW_RIGHT = new Joint(this,Skeleton.POSITION_ELBOW_RIGHT);
        SHOULDER_LEFT = new Joint(this,Skeleton.POSITION_SHOULDER_LEFT);
        SHOULDER_RIGHT = new Joint(this,Skeleton.POSITION_SHOULDER_RIGHT);
        SHOULDER_CENTRE = new Joint(this,Skeleton.POSITION_SHOULDER_CENTER);
        SPINE = new Joint(this,Skeleton.POSITION_SPINE);
        HEAD = new Joint(this,Skeleton.POSITION_HEAD);
    }

    public int getTrackingState() {
        return getSkeletonTrackingState(ID);
    }

    public boolean isTracking() {
        return getSkeletonTrackingState(ID) == SKELETON_TRACKED;
    }

    public int getJointTrackingState(int JointID) {
        return getJointTrackingState(ID, JointID);
    }

    public boolean isTrackingJoint(int JointID) {
        return isTracking()
                && getJointTrackingState(JointID) == POSITION_TRACKED;
    }

    public Position getJointPosition(int JointID) {
        Position p = new Position();
        p.x = getJointPositionByIndex(ID, JointID, 0);
        p.y = getJointPositionByIndex(ID, JointID, 1);
        p.z = getJointPositionByIndex(ID, JointID, 2);
        return p;
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
