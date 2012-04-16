package kinect.world.video;

import kinect.Kinect;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

/**
 * Created with IntelliJ IDEA.
 * User: John
 * Date: 04/04/12
 * Time: 05:37
 * To change this template use File | Settings | File Templates.
 */
public class VideoRegionImager {


    public static BufferedImage toBufferedImage(VideoRegion vr){

        int[] img_data_rgb = new int[vr.height * vr.width * 4];
        BufferedImage img = new BufferedImage(vr.width,vr.height,BufferedImage.TYPE_4BYTE_ABGR);

        int dest_index = 0;
        int src_index = (vr.top * 640 + vr.left) * 4;
        for (int row = vr.top; row < vr.top +  vr.height; row++) {
            for (int col = vr.left; col < vr.left + vr.width; col++) {

                img_data_rgb[dest_index    ] = vr.VIDEO_BUFFER.get(src_index + 2);
                img_data_rgb[dest_index + 1] = vr.VIDEO_BUFFER.get(src_index + 1);
                img_data_rgb[dest_index + 2] = vr.VIDEO_BUFFER.get(src_index);
                img_data_rgb[dest_index + 3] = 255;

                dest_index += 4;
                src_index += 4;

            }
            src_index += (640 - vr.width) * 4;
        }
        img.getRaster().setPixels(0, 0, vr.width, vr.height, img_data_rgb);
        return img;
    }


}
