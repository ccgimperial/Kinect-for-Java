package kinect.visual;

import kinect.Kinect;
import kinect.world.Depth;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

/**
 * Created by IntelliJ IDEA.
 * User: John
 * Date: 21/02/12
 * Time: 14:43
 * <p/>
 * Handle visualisation of Kinect data
 * Probably needs improving, we could do this
 * by perhaps changing the format of the data buffers
 * and the java images
 */
public class Imager {


    static int[] img_data_rgb = new int[640 * 480 * 4];
    static int[] img_data_depth = new int[320 * 240];


    public static void UpdateImageWithVideo(BufferedImage img_video) {
        ByteBuffer bb = Kinect.VIDEO_BUFFER;

        for (int index = 0; index < (640 * 480 * 4); index += 4) {
            img_data_rgb[index] = bb.get(index + 2);
            img_data_rgb[index + 1] = bb.get(index + 1);
            img_data_rgb[index + 2] = bb.get(index);
            img_data_rgb[index + 3] = 255;
        }

        img_video.getRaster().setPixels(0, 0, 640, 480, img_data_rgb);

    }

    public static void UpdateImageWithDepth(BufferedImage img_depth) {

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

    public static void UpdateImageWithPlayer(BufferedImage img_depth) {

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

    public static BufferedImage getNewVideoImage() {
        return new BufferedImage(640, 480, BufferedImage.TYPE_4BYTE_ABGR);
    }

    public static BufferedImage getNewDepthImage() {
        return new BufferedImage(320, 240, BufferedImage.TYPE_4BYTE_ABGR);
    }

    public static BufferedImage getNewDepthGreyscale() {
        return new BufferedImage(320, 240, BufferedImage.TYPE_BYTE_GRAY);
    }

}
