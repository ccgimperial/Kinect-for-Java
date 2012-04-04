package kinect.world.depth;

import java.awt.image.BufferedImage;

/**
 * Created with IntelliJ IDEA.
 * User: John
 * Date: 04/04/12
 * Time: 05:37
 * To change this template use File | Settings | File Templates.
 */
public class DepthRegionImager {


    public static BufferedImage toBufferedImage(DepthRegion dr){

        int[] img_data_depth = new int[dr.height * dr.width];
        BufferedImage img = new BufferedImage(dr.width,dr.height,BufferedImage.TYPE_BYTE_GRAY);

        int index = 0;
        for (int row = dr.top; row < dr.top +  dr.height; row++) {
            for (int col = dr.left; col < dr.left + dr.width; col++) {

                int depth = dr.getDepth(row, col);
                img_data_depth[index] = 255 * depth / 7000;

                index++;
            }
        }
        img.getRaster().setPixels(0, 0, dr.width, dr.height, img_data_depth);
        return img;
    }


}
