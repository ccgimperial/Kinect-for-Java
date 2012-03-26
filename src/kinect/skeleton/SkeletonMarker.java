package kinect.skeleton;

import kinect.geometry.Pixel;
import kinect.geometry.Position;
import kinect.world.Projection;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

/**
 * Created by IntelliJ IDEA.
 * User: John
 * Date: 08/03/12
 * Time: 15:17
 *
 * Class for overlaying skeleton positions on images
 *
 */
public class SkeletonMarker {

    public static void markSkeletonOnVideo(Skeleton s, BufferedImage img) {

        for (Joint j : s.getJoints()) {
            
            switch(j.getTrackingState()){
                case Joint.POSITION_INFERRED:
                    markSkeletonPositionOnVideo(j.getPosition(), img, Color.orange);
                    break;
                case Joint.POSITION_TRACKED:
                    markSkeletonPositionOnVideo(j.getPosition(), img, Color.green);
                    break;
                case Joint.POSITION_NOT_TRACKED:
                default:
                    markSkeletonPositionOnVideo(j.getPosition(), img, Color.red);
                    break;
            }
        }
        
    }
    
    public static void markSkeletonOnDepth(Skeleton s, BufferedImage img) {

        for (Joint j : s.getJoints()) {
            
            switch(j.getTrackingState()){
                case Joint.POSITION_INFERRED:
                    markSkeletonPositionOnDepth(j.getPosition(), img, Color.orange);
                    break;
                case Joint.POSITION_TRACKED:
                    markSkeletonPositionOnDepth(j.getPosition(), img, Color.green);
                    break;
                case Joint.POSITION_NOT_TRACKED:
                default:
                    markSkeletonPositionOnDepth(j.getPosition(), img, Color.red);
                    break;
            }
        }
        
    }
    
    
    public static void markSkeletonPositionOnVideo(Position pos, BufferedImage img, Color c) {

        Pixel vp = Projection.skeletonWorldToVideoPixel(pos);

        Graphics2D g = img.createGraphics();
        g.setColor(c);
        g.fill(new Ellipse2D.Double(vp.col-5,vp.row-5,10,10));
        g.dispose();
    }

    public static void markSkeletonPositionOnDepth(Position pos, BufferedImage img, Color c) {
        Pixel dp = Projection.depthWorldToDepthPixel(pos);
        Graphics2D g = img.createGraphics();
        g.setColor(c);
        g.fill(new Ellipse2D.Double(dp.col-5,dp.row-5,10,10));
        g.dispose();
    }

}
