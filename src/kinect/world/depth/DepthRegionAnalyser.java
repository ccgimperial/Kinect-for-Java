package kinect.world.depth;

import kinect.world.booleans.BooleanRegion;

/**
 * Created with IntelliJ IDEA.
 * User: John
 * Date: 04/04/12
 * Time: 06:15
 * To change this template use File | Settings | File Templates.
 */
public class DepthRegionAnalyser {
    public static BooleanRegion getSurroundingPlayerWithinDepth(DepthRegion dr, int depth_limit) {

        BooleanRegion br = new BooleanRegion(dr.width,dr.height);

        // analyse the hand region
        // create array for the region depth analysis
        int index = 0;
        for( int row = dr.top; row < dr.top + dr.height; row++ ){
            for( int col = dr.left; col < dr.left + dr.width; col++ ){

                int d = dr.getDepth(row, col);
                int p = dr.getPlayerId(row, col);

                // highlight only player pixels within depth cut off
                br.setValueAtIndex(index++,p > 0 && d <= depth_limit);

            }
        }

        return br;

    }

    public static int getFirstRowOfPlayer(DepthRegion dr) {
        for( int row = dr.top; row < dr.top + dr.height; row++ ){
            for( int col = dr.left; col < dr.left + dr.width; col++ ){
                if(dr.getPlayerId(row, col) > 0)
                    return row;
            }
        }
        return -1;
    }

}
