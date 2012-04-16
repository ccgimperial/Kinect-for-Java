package kinect.world.depth;

import kinect.Kinect;
import kinect.geometry.Pixel;
import kinect.world.booleans.BooleanRegion;

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
public class DepthRegion {

    public int top;
    public int left;
    public int width;
    public int height;

    ByteBuffer DEPTH_BUFFER = Kinect.DEPTH_BUFFER;

    public DepthRegion(int top, int left, int width, int height) {
        this.top = top;
        this.left = left;
        this.width = width;
        this.height = height;
    }

    public boolean inBounds() {
        return left >= 0 && top >= 0 & left < 320 - width || top < 240 - height;
    }

    public int getDepth(Pixel p) {
        int index = (((top + p.row) * 320) + (left + p.col)) * 2;
        byte a = DEPTH_BUFFER.get(index);
        byte b = DEPTH_BUFFER.get(index + 1);
        return Depth.getDepthValue(a, b);
    }

    public int getDepth(int row, int col) {
        int index = ((top + row) * 320 + (left + col)) * 2;
        byte a = DEPTH_BUFFER.get(index);
        byte b = DEPTH_BUFFER.get(index + 1);
        return Depth.getDepthValue(a, b);
    }

    public int getPlayerId(Pixel p) {
        int index = ((top + p.row) * 320 + (left + p.col)) * 2;
        byte byte_a = DEPTH_BUFFER.get(index);
        return byte_a & 7;
    }

    public int getPlayerId(int row, int col) {
        int index = ((top + row) * 320 + (left + col)) * 2;
        byte byte_a = DEPTH_BUFFER.get(index);
        return byte_a & 7;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" + "top=" + top + ", left=" + left + ", width=" + width + ", height=" + height +'}';
    }

    public BooleanRegion getSurroundingPlayerWithinDepth(int depth_limit) {
        return DepthRegionAnalyser.getSurroundingPlayerWithinDepth(this, depth_limit);
    }

    /** @return the row id where a player is first seen */
    public int getFirstRowOfPlayer() {
        return DepthRegionAnalyser.getFirstRowOfPlayer(this);
    }

    // File Handling
    public void saveToFile(String filename) {DepthRegionFileHandler.saveToFile(this,filename);}
    public void loadFromFile(String filename){DepthRegionFileHandler.loadFromFile(this, filename);}
    public void loadFromFileOld(String filename){DepthRegionFileHandler.loadFromFileOld(this, filename);}

    // lock and unlock
    public void lock(){DEPTH_BUFFER = Kinect.DEPTH_BUFFER.duplicate();}
    public void unlock(){DEPTH_BUFFER = Kinect.DEPTH_BUFFER;}
    public boolean isLocked(){return DEPTH_BUFFER == Kinect.DEPTH_BUFFER;}


    public BufferedImage toBufferedImage(){return DepthRegionImager.toBufferedImage(this);}


    public Pixel getNearestPoint() {
        return DepthRegionAnalyser.getNearestPoint(this);
    }

    public int getAverageDepthUsingBooleanMask(BooleanRegion br) {
        return DepthRegionAnalyser.getAverageDepthUsingBooleanMask(this, br);
    }

}