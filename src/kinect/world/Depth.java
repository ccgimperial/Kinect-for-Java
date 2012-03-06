package kinect.world;

import kinect.Kinect;
import kinect.geometry.Pixel;

/**
 * Created by IntelliJ IDEA.
 * User: John
 * Date: 14/02/12
 * Time: 18:19
 * <p/>
 * Handles information from Depth field and its analysis
 */
public class Depth {

    public static int getDepthValue(byte a, byte b) {
        return ((a >> 3) & 31) | ((b & 255) << 5);
    }

    public static int getDepth(Pixel p) {
        int index = (p.row * 320 + p.col) * 2;
        byte a = Kinect.DEPTH_BUFFER.get(index);
        byte b = Kinect.DEPTH_BUFFER.get(index + 1);
        return getDepthValue(a, b);
    }

    public static int getDepth(int row, int col) {
        int index = (row * 320 + col) * 2;
        byte a = Kinect.DEPTH_BUFFER.get(index);
        byte b = Kinect.DEPTH_BUFFER.get(index + 1);
        return getDepthValue(a, b);
    }

    public static int getPlayerId(Pixel p) {
        int index = (p.row * 320 + p.col) * 2;
        byte byte_a = Kinect.DEPTH_BUFFER.get(index);
        return byte_a & 7;
    }


    public static int getPlayerId(int row, int col) {
        int index = (row * 320 + col) * 2;
        byte byte_a = Kinect.DEPTH_BUFFER.get(index);
        return byte_a & 7;
    }

}
