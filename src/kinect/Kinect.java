package kinect;

import java.io.File;
import java.nio.ByteBuffer;

public final class Kinect {

    ///////////////////////////////////////////////////////////////////////////////
    // STATIC LIBRARY LOAD CODE
    ///////////////////////////////////////////////////////////////////////////////
    static {
        // look for KinectDLL.dll in following order
        // - current directory
        // - c++ sub-project
        try {
            if ((new File("KinectDLL.dll")).exists()) {
                System.loadLibrary("KinectDLL");
            } else
                System.loadLibrary("KinectDLL/x64/Release/KinectDLL");
        } catch (UnsatisfiedLinkError e) {
            System.err.println("Native code library failed to load.\n" + e);
            System.exit(1);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////
    // PRIVATE MEMBERS
    ///////////////////////////////////////////////////////////////////////////////
    static boolean keep_running = true;
    static boolean kinect_paused = false;
    static KinectObserver OBSERVER = null;

    ///////////////////////////////////////////////////////////////////////////////
    // PUBLIC INTERFACE
    ///////////////////////////////////////////////////////////////////////////////

    // Depth and Video Buffers
    public static ByteBuffer DEPTH_BUFFER = allocateDepth();
    public static ByteBuffer VIDEO_BUFFER = allocateVideo();

    // start the kinect and handle main loop
    public static void init(KinectObserver observer) {
        OBSERVER = observer;
        if (!start()) System.exit(1);
        while (keep_running) {
            if (kinect_paused) continue;
            int event_id = Kinect.getNextEvent();
            switch (event_id) {
                case 1:
                    observer.VideoEvent();
                    break;
                case 2:
                    observer.DepthEvent();
                    break;
                case 3:
                    observer.SkeletonEvent();
                    break;
                case 0:
                    System.out.println("Kinect shutdown event received - stopping");
                    keep_running = false;
                    stop();
                    break;
                case 258: /* sporadically get this - ignore it */
                    break;
                default:
                    System.out.println("Unknown Kinect event returned: " + event_id);
            }
        }
    }

    public static void setPaused(boolean b) {
        kinect_paused = b;
    }

    public static native void stop();

    ///////////////////////////////////////////////////////////////////////////////
    // PUBLIC NATIVE INTERFACE
    ///////////////////////////////////////////////////////////////////////////////
    public static native void setSensorAngle(int angle);

    public static native int getSensorAngle();

    ///////////////////////////////////////////////////////////////////////////////
    // PRIVATE NATIVE INTERFACE
    ///////////////////////////////////////////////////////////////////////////////
    static native boolean start();

    static native int getNextEvent();

    static native ByteBuffer allocateVideo();

    static native ByteBuffer allocateDepth();

}
