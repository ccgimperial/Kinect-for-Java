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

    public Pixel() {}

    public Pixel(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public Pixel(Pixel other) {
        this.row = other.row;
        this.col = other.col;
    }

    public boolean isInBounds320() {
        if (row < 0) return false;
        if (row >= 240) return false;
        if (col < 0) return false;
        if (col >= 320) return false;
        return true;
    }

    public boolean isInBounds640() {
        if (row < 0) return false;
        if (row >= 480) return false;
        if (col < 0) return false;
        if (col >= 640) return false;
        return true;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" + row + "," + col + '}';
    }

    public Pixel add(Pixel other) {
        Pixel p = new Pixel();
        p.row = this.row + other.row;
        p.col = this.col + other.col;
        return p;
    }

    public Pixel divide(double d) {
        Pixel p = new Pixel();
        p.row = (int) Math.round(this.row / d);
        p.col = (int) Math.round(this.col / d);
        return p;
    }
}
