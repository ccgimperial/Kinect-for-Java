package kinect.world.depth;

import kinect.geometry.Pixel;
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
        for( int row = 0; row < dr.height; row++ ){
            for( int col = 0; col < dr.width; col++ ){

                int d = dr.getDepth(row, col);
                int p = dr.getPlayerId(row, col);

                // highlight only player pixels within depth cut off
                br.setValueAtIndex(index++,p > 0 && d <= depth_limit);

            }
        }

        return br;

    }

    public static int getFirstRowOfPlayer(DepthRegion dr) {
        for( int row = 0; row < dr.height; row++ ){
            for( int col = 0; col < dr.width; col++ ){
                if(dr.getPlayerId(row, col) > 0)
                    return row;
            }
        }
        return -1;
    }

    public static Pixel getNearestPoint(DepthRegion dr) {
        Pixel p = new Pixel();
        int min_d = 7000;
        for( int row = 0; row < dr.height; row++ ){
            for( int col = 0; col < dr.width; col++ ){
                int d = dr.getDepth(row, col);
                if(d > 0 && d < min_d){
                    min_d = d;
                    p.row = row;
                    p.col = col;
                }
            }
        }
        return p;
    }


    public static int getAverageDepthUsingBooleanMask(DepthRegion dr, BooleanRegion br) {
        if(dr.width != br.width || dr.height != br.height)
            return -1;
        double depth_total = 0;
        int depth_count = 0;
        for( int row = 0; row < br.height; row++ ){
            for( int col = 0; col < br.width; col++ ){
                if(br.getValue(row,col)){
                    depth_count++;
                    depth_total += dr.getDepth(row,col);
                }
            }
        }

        return (int) Math.round(depth_total / depth_count);
    }



}
