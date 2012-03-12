package kinect.world;

import com.sun.xml.internal.bind.v2.TODO;
import kinect.geometry.Pixel;
import kinect.geometry.Position;
import kinect.geometry.SquareMatrix;
import kinect.skeleton.Joint;
import kinect.visual.Imager;

/**
 * Created by IntelliJ IDEA.
 * User: John
 * Date: 23/02/12
 * Time: 17:07
 * <p/>
 * Functions for converting between pixel and Kinect world positions
 *
 * I recommend you experiment with the camera centres to get something
 * appropriate for your device.
 *
 */
public class Projection {

    // from the NUI header files
    public static double NUI_CAMERA_COLOR_NOMINAL_FOCAL_LENGTH_IN_PIXELS = 531.15;
    public static double NUI_CAMERA_DEPTH_NOMINAL_FOCAL_LENGTH_IN_PIXELS = 285.63;

    public static double ROT_DEGREES = 0.25;  // rotation between cameras
    public static double TRANSLATION = 0.25; // translation between cameras

    // do all up-front matrix calculation
    static {
//        calculateMatrices(320, 248, 160, 120);       // some values for my work Kinect
        calculateMatrices(314, 256, 160, 120);       // some values for my home Kinect

    }

    // store calculated multipliers
    private static double fx_d;  // intrinsic depth
    private static double fy_d;
    private static double cx_d;
    private static double cy_d;
    private static double d_fx;  // intrinsic depth inverted
    private static double d_fy;
    private static double d_cx;
    private static double d_cy;

    private static double r_cos;     // rotation between cameras
    private static double r_sin;
    private static double r_cos_inv;
    private static double r_sin_inv;

    private static double fx_v;  // intrinsic video
    private static double fy_v;
    private static double cx_v;
    private static double cy_v;
    private static double v_fx;  // intrinsic video inverted
    private static double v_fy;
    private static double v_cx;
    private static double v_cy;


    /**  Calculates the intrinsic matrix multipliers for the cameras
     * based on the provided centres for the cameras.
     * Found that this was the biggest difference between Kinect devices.
     *
     * @param video_cx - video camera centre - x
     * @param video_cy - video camera centre - y
     * @param depth_cx - depth camera centre - x
     * @param depth_cy - depth camera centre - y
     */
    public static void calculateMatrices(int video_cx, int video_cy, int depth_cx, int depth_cy) {

        ////////////////////////////////////////////////////////////////////////////////////
        // MULTIPLIERS FOR GOING FROM DEPTH TO RGB
        ////////////////////////////////////////////////////////////////////////////////////

        // set up depth intrinsic multipliers
        fx_d = NUI_CAMERA_DEPTH_NOMINAL_FOCAL_LENGTH_IN_PIXELS;
        fy_d = NUI_CAMERA_DEPTH_NOMINAL_FOCAL_LENGTH_IN_PIXELS;
        cx_d = depth_cx;
        cy_d = depth_cy;

        SquareMatrix DepthIntr = new SquareMatrix(4);
        DepthIntr.set(0,0,fx_d);
        DepthIntr.set(1,1,fy_d);
        DepthIntr.set(0,2,cx_d);
        DepthIntr.set(1,2,cy_d);
        DepthIntr.set(2,2,1.0);
        DepthIntr.set(3,3,1.0);
        SquareMatrix DepthIntrInv = DepthIntr.getInverse();
        d_fx = DepthIntrInv.at(0,0);
        d_fy = DepthIntrInv.at(1,1);
        d_cx = DepthIntrInv.at(0,2);
        d_cy = DepthIntrInv.at(1,2);

        // set up rotation/translation multipliers
        r_cos = Math.cos(ROT_DEGREES * Math.PI / 180.0);
        r_sin = Math.sin(ROT_DEGREES * Math.PI / 180.0);

        ////////////////////////////////////////////////////////////////////////////////////
        // MULTIPLIERS FOR GOING FROM RGB TO DEPTH
        ////////////////////////////////////////////////////////////////////////////////////

        // set up rgb intrinsic multipliers
        fx_v = NUI_CAMERA_COLOR_NOMINAL_FOCAL_LENGTH_IN_PIXELS;
        fy_v = NUI_CAMERA_COLOR_NOMINAL_FOCAL_LENGTH_IN_PIXELS ;
        cx_v = video_cx;
        cy_v = video_cy;

        // set up rgb intrinsic multipliers
        SquareMatrix VideoIntr = new SquareMatrix(4);
        VideoIntr.set(0,0,fx_v);
        VideoIntr.set(1,1,fy_v);
        VideoIntr.set(0,2,cx_v);
        VideoIntr.set(1,2,cy_v);
        VideoIntr.set(2,2,1.0);
        VideoIntr.set(3,3,1.0);
        SquareMatrix VideoIntrInv = VideoIntr.getInverse();
        v_fx = VideoIntrInv.at(0,0);
        v_fy = VideoIntrInv.at(1,1);
        v_cx = VideoIntrInv.at(0,2);
        v_cy = VideoIntrInv.at(1,2);

        // set up rotation/translation multipliers
        r_cos_inv = Math.cos(-ROT_DEGREES * Math.PI / 180.0);
        r_sin_inv = Math.sin(-ROT_DEGREES * Math.PI / 180.0);

    }


    public static Position depthPixelToDepthWorld(Pixel p, double d) {
        Position world_point = new Position();
        world_point.x =  d * (d_fx * p.col + d_cx);
        world_point.y = -d * (d_fy * p.row + d_cy);
        world_point.z =  d;
        return world_point;
    }

    public static Position depthPixelToDepthWorld(Pixel p){
        int d = Depth.getDepth(p);
        return depthPixelToDepthWorld(p, d);
    }

    public static Pixel depthWorldToDepthPixel(Position dwp) {
        Pixel pixel = new Pixel();
        pixel.col = (int) Math.round(( fx_d * dwp.x + cx_d * dwp.z) / dwp.z);
        pixel.row = (int) Math.round((-fy_d * dwp.y + cy_d * dwp.z) / dwp.z);
        return pixel;
    }

    public static Position videoPixelToVideoWorld(Pixel p, double d) {

        Position world_point = new Position();
        world_point.x =  d * (v_fx * p.col + v_cx);
        world_point.y = -d * (v_fy * p.row + v_cy);
        world_point.z =  d;
        return world_point;

    }

    public static Pixel videoWorldToVideoPixel(Position vwp) {

        Pixel pixel = new Pixel();
        pixel.col = (int) Math.round((  fx_v * vwp.x + cx_v * vwp.z) / vwp.z);
        pixel.row = (int) Math.round(( -fy_v * vwp.y + cy_v * vwp.z) / vwp.z);
        return pixel;

    }

    public static Position videoWorldToDepthWorld(Position vwp) {

        // see above
        Position dwp = new Position();
        vwp.x -= TRANSLATION;  // translate first
        dwp.x = r_cos_inv * vwp.x + r_sin_inv * vwp.z;
        dwp.y = vwp.y;  // no movement in y
        dwp.z = -r_sin_inv * vwp.x + r_cos_inv * vwp.z;
        return dwp;
    }

    public static Position depthWorldToVideoWorld(Position dwp) {

        // world point rotated and translated - assume just along x axis
        // i.e. a spin in the z axis and an x translation
        Position vwp = new Position(); // rotated position i.e. pov rgb camera
        vwp.x =  r_cos * dwp.x + r_sin * dwp.z + TRANSLATION;
        vwp.y = dwp.y;  // no movement in y
        vwp.z = -r_sin * dwp.x + r_cos * dwp.z;
        return vwp;

    }

    // colour constants
    static final int[] red = {255,0,0};
    public static int[] depthPixelToVideoColour( Pixel dp, double d)
    {
        Position dwp = depthPixelToDepthWorld(dp, d);
        Position vwp = depthWorldToVideoWorld(dwp);
        Pixel vp = videoWorldToVideoPixel(vwp);

        if(vp.isInBounds640())
            return Imager.getVideoColour(vp);
        return red;
    }


    public static Pixel depthWorldToVideoPixel(Position dwp) {
        Position vwp = depthWorldToVideoWorld(dwp);
        return videoWorldToVideoPixel(vwp);
    }

    /** Introduced this to account for the fact that skeleton
     * points are set inside the body.  Matching of depth pixels
     * to video pixels works OK.  Therefore, we first rebase 
     * the skeleton point to the depth pixel and take it from there.
     * If this approach results in a depth pixel with zero depth
     * it will return a zero result.  Change your input position.
     *
     * @param swp world point from skeleton
     * @return corresponding video point
     */
    public static Pixel skeletonWorldToVideoPixel(Position swp) {
        Pixel dp = depthWorldToDepthPixel(swp);
        return depthPixelToVideoPixel(dp);

    }

    public static Pixel depthPixelToVideoPixel(Pixel dp) {
        int d = Depth.getDepth(dp);
        Position dwp = depthPixelToDepthWorld(dp, d);
        Position vwp = depthWorldToVideoWorld(dwp);
        return videoWorldToVideoPixel(vwp);
    }

}
