package kinect;

import kinect.Kinect;

/**
 * Created by IntelliJ IDEA.
 * User: John
 * Date: 14/02/12
 * Time: 18:19
 * To change this template use File | Settings | File Templates.
 */
public class Depth {
    
    public static int DepthValue(byte a, byte b){
        return ((a >> 3) & 31) | ((b & 255) << 5);
    }
    
    public static int DepthAtPoint(int row, int col){
        
        int index = (row * 320 + col) * 2;
        byte a = Kinect.DEPTH_BUFFER.get(index);
        byte b = Kinect.DEPTH_BUFFER.get(index + 1);                
        return DepthValue(a,b);
        
    }
    
    public static int PlayerIdAtPoint(int row, int col){
        int index = (row * 320 + col) * 2;
        byte byte_a = Kinect.DEPTH_BUFFER.get(index);
        return (int)(byte_a & 7);

    }
    
    
    
    
}
