package kinect.geometry;

import java.text.DecimalFormat;

/**
 * Created by IntelliJ IDEA.
 * User: John
 * Date: 23/02/12
 * Time: 17:10
 * <p/>
 * Simple Pixel class for representing screen co-ordinates
 */
public class Pixel {
    public int row = 0;
    public int col = 0;

    public boolean isInBounds320() {
        if (row < 0) return false;
        if (row >= 320) return false;
        if (col < 0) return false;
        if (col >= 240) return false;
        return true;
    }

    public boolean isInBounds640() {
        if (row < 0) return false;
        if (row >= 640) return false;
        if (col < 0) return false;
        if (col >= 480) return false;
        return true;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" + row + "," + col + '}';
    }

}
