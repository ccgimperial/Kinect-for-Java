package kinect;

import java.nio.ByteBuffer;
import java.util.Vector;

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
    public static final int POSITION_HIP_CENTER        = 0;
    public static final int POSITION_SPINE             = 1;
    public static final int POSITION_SHOULDER_CENTER   = 2;
    public static final int POSITION_HEAD = 3;
    public static final int POSITION_SHOULDER_LEFT     = 4;
    public static final int POSITION_ELBOW_LEFT        = 5;
    public static final int POSITION_WRIST_LEFT        = 6;
    public static final int POSITION_HAND_LEFT         = 7;
    public static final int POSITION_SHOULDER_RIGHT    = 8;
    public static final int POSITION_ELBOW_RIGHT       = 9;
    public static final int POSITION_WRIST_RIGHT       = 10;
    public static final int POSITION_HAND_RIGHT        = 11;
    public static final int POSITION_HIP_LEFT          = 12;
    public static final int POSITION_KNEE_LEFT         = 13;
    public static final int POSITION_ANKLE_LEFT        = 14;
    public static final int POSITION_FOOT_LEFT = 15;
    public static final int POSITION_HIP_RIGHT         = 16;
    public static final int POSITION_KNEE_RIGHT        = 17;
    public static final int POSITION_ANKLE_RIGHT       = 18;
    public static final int POSITION_FOOT_RIGHT        = 19;
    public static final int POSITION_COUNT             = 20;


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



    public static Position GetJointPosition(int SkeletonID, int JointID) {
        Position p = new Position();
        p.x = Kinect.GetJointPositionByIndex(SkeletonID, JointID, 0);
        p.y = Kinect.GetJointPositionByIndex(SkeletonID, JointID, 1);
        p.z = Kinect.GetJointPositionByIndex(SkeletonID, JointID, 2);
        return p;
    }

    public static boolean IsTrackingSkeleton(){
        return Kinect.IsTrackingSkeleton();
    }
    
    public static int GetTrackedSkeletonId(){
        return Kinect.GetTrackedSkeletonId();
    }

    public static int GetSkeletonTrackingState(int SkeletonID){
        return Kinect.GetSkeletonTrackingState(SkeletonID);
    }
    
    public static int  GetJointTrackingState(int SkeletonID, int JointID){
        return Kinect.GetJointTrackingState(SkeletonID,JointID);
    }

    public static Position GetNormalToGravity_NOT_WORKING(){
        Position p = new Position();
        p.x = Kinect.GetSkeletonNormalToGravityByIndex(0);
        p.y = Kinect.GetSkeletonNormalToGravityByIndex(1);
        p.z = Kinect.GetSkeletonNormalToGravityByIndex(2);
        return p;
    }

    public static Position GetFloorClipPlane_NOT_WORKING(){
        Position p = new Position();
        p.x = Kinect.GetSkeletonFloorClipPlaneByIndex(0);
        p.y = Kinect.GetSkeletonFloorClipPlaneByIndex(1);
        p.z = Kinect.GetSkeletonFloorClipPlaneByIndex(2);
        return p;
    }


    
}
