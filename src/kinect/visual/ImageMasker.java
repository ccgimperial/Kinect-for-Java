package kinect.visual;

import kinect.geometry.Pixel;
import kinect.visual.Imager;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by IntelliJ IDEA.
 * User: John
 * Date: 08/03/12
 * Time: 15:17
 *
 * Class for grabbing sections of a given image
 */
public class ImageMasker {

    /** Grabs part of an image.  Draws a line of a given width on
     * a mask image and uses that mask to grab part of the source image.
     * The line can have rounded or flat ends as necessary.
     *
     * @param img - BufferedImage to grab bits from
     * @param width - Stroke width to use
     * @param start - line start point
     * @param end - line end point
     * @param rounded - stroke has rounded ends or not
     * @return new image with only stroked part
     */
    public static BufferedImage createImageMaskStroke(BufferedImage img, int width, Pixel start, Pixel end, boolean rounded) {

        BufferedImage mask = Imager.getNewVideoImage();

        Graphics2D g = mask.createGraphics();

        g.setColor(Color.white);
        if(rounded)
            g.setStroke(new BasicStroke(width, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        else
            g.setStroke(new BasicStroke(width, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
        g.drawLine(start.col,start.row,end.col,end.row);

        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_IN);
        g.setComposite(ac);
        g.drawImage(img, 0, 0, 640, 480, null, null);
        g.dispose();

        return mask;

    }
}
