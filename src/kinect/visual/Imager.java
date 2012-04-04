package kinect.visual;

import kinect.Kinect;
import kinect.geometry.Pixel;
import kinect.geometry.Position;
import kinect.skeleton.Joint;
import kinect.skeleton.Skeleton;
import kinect.world.depth.Depth;
import kinect.world.Projection;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Created by IntelliJ IDEA.
 * User: John
 * Date: 21/02/12
 * Time: 14:43
 *
 * Handle visualisation of Kinect data
 * Probably needs improving, we could do this
 * by perhaps changing the format of the data buffers
 * and the java images
 */
public class Imager {

    /////////////////////////////////////////////////////////////////////////////////////////////
    // Buffers using for updating images - passed to raster setPixel functions
    /////////////////////////////////////////////////////////////////////////////////////////////
    static int[] img_data_rgb = new int[640 * 480 * 4];
    static int[] img_data_depth = new int[320 * 240];
    static int[] img_data_depth_rgb = new int[320 * 240 * 4];

    /////////////////////////////////////////////////////////////////////////////////////////////
    // functions for updating target buffered images
    /////////////////////////////////////////////////////////////////////////////////////////////

    public static void updateColour640ImageWithVideo(BufferedImage img_video) {
        ByteBuffer bb = Kinect.VIDEO_BUFFER;

        for (int index = 0; index < (640 * 480 * 4); index += 4) {
            img_data_rgb[index] = bb.get(index + 2);
            img_data_rgb[index + 1] = bb.get(index + 1);
            img_data_rgb[index + 2] = bb.get(index);
            img_data_rgb[index + 3] = 255;
        }

        img_video.getRaster().setPixels(0, 0, 640, 480, img_data_rgb);

    }

    public static void updateGrey320ImageWithDepthGrey(BufferedImage img_depth) {

        int index = 0;
        for (int row = 0; row < 240; row++) {
            for (int col = 0; col < 320; col++) {

                int depth = Depth.getDepth(row, col);
                img_data_depth[index] = 255 * depth / 7000;

                index++;
            }
        }

        img_depth.getRaster().setPixels(0, 0, 320, 240, img_data_depth);
    }

    public static void updateGrey320ImageWithPlayerMask(BufferedImage img_depth) {

        int index = 0;
        for (int row = 0; row < 240; row++) {
            for (int col = 0; col < 320; col++) {

                if (Depth.getPlayerId(row, col) != 0)
                    img_data_depth[index] = 255;
                else
                    img_data_depth[index] = 0;

                index++;
            }
        }

        img_depth.getRaster().setPixels(0, 0, 320, 240, img_data_depth);

    }

    public static void updateColour320ImageWithDepthAndPlayer(BufferedImage img_depth){

        int index = 0;
        for (int row = 0; row < 240; row++) {
            for (int col = 0; col < 320; col++) {

                int l = 255 * Depth.getDepth(row, col) / 7000;
                int playerId = Depth.getPlayerId(row, col);

                int r = 0;
                int g = 0;
                int b = 0;

                switch( playerId )
                {
                    case 0:r = l / 2; g = l / 2;b = l / 2;break;
                    case 1:r = l;break;
                    case 2:g = l;break;
                    case 3:r = l / 4;g = l;b = l;break;
                    case 4:r = l;g = l;b = l / 4;break;
                    case 5:r = l;g = l / 4;b = l;break;
                    case 6:r = l / 2;g = l / 2;b = l;break;
                    case 7:r = 255 - ( l / 2 );g = 255 - ( l / 2 );b = 255 - ( l / 2 );
                }

                img_data_depth_rgb[index] = r;
                img_data_depth_rgb[index + 1] = g;
                img_data_depth_rgb[index + 2] = b;
                img_data_depth_rgb[index + 3] = 255;

                index += 4;
            }
        }

        img_depth.getRaster().setPixels(0,0,320,240,img_data_depth_rgb);

    }

    public static void updateColour320ImageWithVideoOverlaidDepth(BufferedImage img_depth){

        int index = 0;
        Pixel pd = new Pixel();
        for (pd.row = 0; pd.row < 240; pd.row++) {
            for (pd.col = 0; pd.col < 320; pd.col++) {

                int d = Depth.getDepth(pd);
                int[] c = Projection.depthPixelToVideoColour(pd,d);

                img_data_depth_rgb[index] = c[0];
                img_data_depth_rgb[index + 1] = c[1];
                img_data_depth_rgb[index + 2] = c[2];
                img_data_depth_rgb[index + 3] = 255;
                index+=4;
            }
        }

        img_depth.getRaster().setPixels(0,0,320,240,img_data_depth_rgb);

    }

    public static void updateColour320ImageWithVideoOverlaidPlayer(BufferedImage img_depth, int playerId){

        int index = 0;
        Pixel pd = new Pixel();
        for (pd.row = 0; pd.row < 240; pd.row++) {
            for (pd.col = 0; pd.col < 320; pd.col++) {

                int d = Depth.getDepth(pd);
                int pid = Depth.getPlayerId(pd);

                int[] c = Projection.depthPixelToVideoColour(pd,d);

                img_data_depth_rgb[index] = c[0];
                img_data_depth_rgb[index + 1] = c[1];
                img_data_depth_rgb[index + 2] = c[2];
                if(pid == playerId)
                    img_data_depth_rgb[index + 3] = 255;
                else
                    img_data_depth_rgb[index + 3] = 0;

                index+=4;
            }
        }

        img_depth.getRaster().setPixels(0,0,320,240,img_data_depth_rgb);

    }



    /** returns the colour stored at the requested pixel as an int[]
     *
     * @param p - Pixel to retrieve
     * @return rgb array of requested Pixel
     */
    public static int[] getVideoColour(Pixel p) {
        int[] colour = new int[3];
        int index = (p.row * 640 + p.col) * 4;
        colour[0] = Kinect.VIDEO_BUFFER.get(index + 2);
        colour[1] = Kinect.VIDEO_BUFFER.get(index + 1);
        colour[2] = Kinect.VIDEO_BUFFER.get(index);
        return colour;
    }

    public static void updateColour320ImageWithSkeleton(BufferedImage img_depth, Skeleton s){

        Graphics2D g = img_depth.createGraphics();

        for (Joint joint : s.getJoints()) {

            Position dwp = joint.getPosition();
            Pixel dp = Projection.depthWorldToDepthPixel(dwp);
            Ellipse2D.Double e = new Ellipse2D.Double(dp.col - 5, dp.row - 5, 10, 10);

            switch (joint.getTrackingState()){
                case Joint.POSITION_TRACKED:
                    g.setColor(Color.green);
                    break;
                case Joint.POSITION_INFERRED:
                    g.setColor(Color.orange);
                    break;
                case Joint.POSITION_NOT_TRACKED:
                default:
                    g.setColor(Color.red);
                    break;

            }
            g.fill(e);
        }

        g.dispose();

    }





    /////////////////////////////////////////////////////////////////////////////////////////////
    // Allocate BufferedImages of the correct size
    /////////////////////////////////////////////////////////////////////////////////////////////
    public static BufferedImage getNewVideoImage() {
        return new BufferedImage(640, 480, BufferedImage.TYPE_4BYTE_ABGR);
    }

    public static BufferedImage getNewDepthImage() {
        return new BufferedImage(320, 240, BufferedImage.TYPE_4BYTE_ABGR);
    }

    public static BufferedImage getNewDepthGreyscale() {
        return new BufferedImage(320, 240, BufferedImage.TYPE_BYTE_GRAY);
    }


    public static void updateColour640ImageWithPlayerCutout(BufferedImage target, int pid) {
        ByteBuffer bb = Kinect.VIDEO_BUFFER;

        Arrays.fill(img_data_rgb,0); // clear the storage array

        // go through depth image to cut out player
        Pixel pd = new Pixel();
        for (pd.row = 0; pd.row < 240; pd.row++) {
            for (pd.col = 0; pd.col < 320; pd.col++) {

                int d = Depth.getDepth(pd);
                int playerId = Depth.getPlayerId(pd);

                if(playerId == pid){
                    Pixel vp = Projection.depthPixelToVideoPixel(pd); // this and surrounding pixels should be active
                    int index = (640 * vp.row + vp.col) * 4;
                    img_data_rgb[index] = bb.get(index + 2);
                    img_data_rgb[index + 1] = bb.get(index + 1);
                    img_data_rgb[index + 2] = bb.get(index);
                    img_data_rgb[index + 3] = 255;
                    img_data_rgb[index + 4] = bb.get(index + 6);
                    img_data_rgb[index + 5] = bb.get(index + 5);
                    img_data_rgb[index + 6] = bb.get(index + 4);
                    img_data_rgb[index + 7] = 255;
                    index += (640 -2) * 4;
                    img_data_rgb[index] = bb.get(index + 2);
                    img_data_rgb[index + 1] = bb.get(index + 1);
                    img_data_rgb[index + 2] = bb.get(index);
                    img_data_rgb[index + 3] = 255;
                    img_data_rgb[index + 4] = bb.get(index + 6);
                    img_data_rgb[index + 5] = bb.get(index + 5);
                    img_data_rgb[index + 6] = bb.get(index + 4);
                    img_data_rgb[index + 7] = 255;
                }
            }
    }

    target.getRaster().setPixels(0,0,640,480,img_data_rgb);

}
}
