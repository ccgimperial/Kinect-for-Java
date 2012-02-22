package kinect;

import java.nio.ByteBuffer;

public class Kinect {

    static {
        try {
            System.loadLibrary("KinectDLL/x64/Release/KinectDLL");
        } catch (UnsatisfiedLinkError e) {
            System.err.println("Native code library failed to load.\n" + e);
            System.exit(1);
        }
    }

    static boolean keep_running = true;
    static boolean kinect_paused = false;
    static KinectObserver OBSERVER = null;

    // start the kinect and handle main loop
    public final static void Init(KinectObserver observer){
        OBSERVER = observer;
        if(!Start()) System.exit(1);
        while(keep_running){
            while(true){
                if(kinect_paused) continue;
                switch (Kinect.GetNextEvent()) {
                    case 1: observer.VideoEvent(); break;
                    case 2: observer.DepthEvent(); break;
                    case 3: observer.SkeletonEvent(); break;
                    default:System.exit(0);
                }
            }
        }
    }
    final static native boolean Start();

    // Depth and Video Buffers
    public static ByteBuffer DEPTH_BUFFER = AllocateDepth();
    public static ByteBuffer VIDEO_BUFFER = AllocateVideo();

    final static native void Stop();

    // public native interface
    public static void SetPaused(boolean b){kinect_paused = b;}

    // private native interface
    final static native int GetNextEvent();
    final static native ByteBuffer AllocateVideo();
    final static native ByteBuffer AllocateDepth();


    // skeleton access
    final static native boolean IsTrackingSkeleton();
    final static native int GetTrackedSkeletonId();
    final static native int GetSkeletonTrackingState(int SkeletonID);
    final static native int  GetJointTrackingState(int SkeletonID, int JointID);
    final static native float GetJointPositionByIndex(int SkeletonID, int JointID, int PositionIndex);
    final static native float GetSkeletonNormalToGravityByIndex(int PositionIndex);
    final static native float GetSkeletonFloorClipPlaneByIndex(int PositionIndex);


}
