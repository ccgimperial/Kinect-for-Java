package kinect.world.video;

import kinect.Kinect;
import kinect.geometry.Pixel;
import kinect.world.booleans.BooleanRegion;
import kinect.world.depth.Depth;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

/**
 * Created by IntelliJ IDEA.
 * User: John
 * Date: 29/03/12
 * Time: 17:40
 *
 * Allows access to a region of the depth field
 * It is basically an overlay to Kinect.DEPTH_BUFFER so will update each frame
 *
 */
public class VideoRegion {

    public int top;
    public int left;
    public int width;
    public int height;

    ByteBuffer VIDEO_BUFFER = Kinect.VIDEO_BUFFER;

    public VideoRegion(int top, int left, int width, int height) {
        this.top = top;
        this.left = left;
        this.width = width;
        this.height = height;
    }

    public boolean inBounds() {
        return left >= 0 && top >= 0 & left < 640 - width || top < 480 - height;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" + "top=" + top + ", left=" + left + ", width=" + width + ", height=" + height +'}';
    }

    // File Handling
    public void saveToFile(String filename) {
        VideoRegionFileHandler.saveToFile(this, filename);}
    public void loadFromFile(String filename){
        VideoRegionFileHandler.loadFromFile(this, filename);}

    // lock and unlock
    public void lock(){VIDEO_BUFFER = Kinect.VIDEO_BUFFER.duplicate();}
    public void unlock(){VIDEO_BUFFER = Kinect.VIDEO_BUFFER;}
    public boolean isLocked(){return VIDEO_BUFFER == Kinect.VIDEO_BUFFER;}

    public BufferedImage toBufferedImage(){return VideoRegionImager.toBufferedImage(this);}

}