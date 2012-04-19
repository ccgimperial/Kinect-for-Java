package kinect.skeleton;

import kinect.geometry.Position;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Skeleton {

    // TRACKING_STATE
    public static final int SKELETON_NOT_TRACKED = 0;
    public static final int SKELETON_POSITION_ONLY = 1;
    public static final int SKELETON_TRACKED = 2;

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

    public static Skeleton getSkeleton(int SkeletonID){return skeletons[SkeletonID];}
    public static Skeleton getTrackedSkeleton() {return getSkeleton(getTrackedSkeletonId());}

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

    //////////////////////////////////////////////////////////////////////////////////////
    // NATIVE INTERFACE
    //////////////////////////////////////////////////////////////////////////////////////
    public final static native boolean isTrackingSomeSkeleton();
    public final static native int getTrackedSkeletonId();
    final static native int getSkeletonTrackingState(int SkeletonID);
    final static native float getSkeletonNormalToGravityByIndex(int PositionIndex);
    final static native float getSkeletonFloorClipPlaneByIndex(int PositionIndex);

    //////////////////////////////////////////////////////////////////////////////////////
    // INSTANCE SPECIFIC
    //////////////////////////////////////////////////////////////////////////////////////
    public int ID;
    private Joint[] joints = null;

    protected Skeleton(int skeletonID) {
        ID = skeletonID;
        joints = new Joint[POSITION_COUNT];
        for (int i = 0; i < joints.length; i++) {
            joints[i] = new Joint(this,i);
        }
    }

    public Joint getFootLeft(){return joints[POSITION_FOOT_LEFT];}
    public Joint getFootRight(){return joints[POSITION_FOOT_RIGHT];}
    public Joint getAnkleLeft(){return joints[POSITION_ANKLE_LEFT];}
    public Joint getAnkleRight(){return joints[POSITION_ANKLE_RIGHT];}
    public Joint getKneeLeft(){return joints[POSITION_KNEE_LEFT];}
    public Joint getKneeRight(){return joints[POSITION_KNEE_RIGHT];}
    public Joint getHipLeft(){return joints[POSITION_HIP_LEFT];}
    public Joint getHipRight(){return joints[POSITION_HIP_RIGHT];}
    public Joint getHipCenter(){return joints[POSITION_HIP_CENTER];}
    public Joint getHandLeft(){return joints[POSITION_HAND_LEFT];}
    public Joint getHandRight(){return joints[POSITION_HAND_RIGHT];}
    public Joint getWristLeft(){return joints[POSITION_WRIST_LEFT];}
    public Joint getWristRight(){return joints[POSITION_WRIST_RIGHT];}
    public Joint getElbowLeft(){return joints[POSITION_ELBOW_LEFT];}
    public Joint getElbowRight(){return joints[POSITION_ELBOW_RIGHT];}
    public Joint getShoulderLeft(){return joints[POSITION_SHOULDER_LEFT];}
    public Joint getShoulderRight(){return joints[POSITION_SHOULDER_RIGHT];}
    public Joint getShoulderCenter(){return joints[POSITION_SHOULDER_CENTER];}
    public Joint getHead(){return joints[POSITION_HEAD];}
    public Joint getSpine(){return joints[POSITION_SPINE];}

    public int getTrackingState() {
        return getSkeletonTrackingState(ID);
    }

    public boolean isTracking() {
        return getSkeletonTrackingState(ID) == SKELETON_TRACKED;
    }

    public Joint[] getJoints() {
        return joints;
    }

    public void writeToFile(File output_file) throws IOException {

        FileWriter fwt = new FileWriter(output_file);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < joints.length; i++) {
            sb.append(joints[i].toString());
            sb.append("\n");
        }
        fwt.write(sb.toString());
        fwt.flush();
        fwt.close();

    }

}
