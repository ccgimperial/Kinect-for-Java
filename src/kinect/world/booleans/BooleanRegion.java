package kinect.world.booleans;

import kinect.geometry.Pixel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

/**
 * Created by IntelliJ IDEA.
 * User: John
 * Date: 29/03/12
 * Time: 17:40
 *
 * Rectangular Boolean Region for analysing depth
 *
 */
public class BooleanRegion {

    public int width;
    public int height;
    boolean[] values;

    public BooleanRegion(int width, int height) {
        this.width = width;
        this.height = height;
        values = new boolean[width * height];
    }

    public BooleanRegion(BooleanRegion other) {
        this.width = other.width;
        this.height = other.height;
        this.values = Arrays.copyOf(other.values,other.values.length);
    }

    public BooleanRegion(int width, int height, int[] init) {
        this.width = width;
        this.height = height;
        values = new boolean[width * height];
        for (int i = 0; i < init.length; i++) {
            values[i] = init[i] == 1;
        }
    }

    public void setValue(int row, int col, boolean value) {
        values[row * width + col] = value;
    }
    public void setValueAtIndex(int index, boolean value) {
        values[index] = value;
    }
    public boolean getValue(int row, int col) {
        return values[row * width + col];
    }
    public boolean getValueAtIndex(int index) {
        return values[index];
    }

    public void clear(){
        Arrays.fill(values,false);
    }

    public void orWith(BooleanRegion other) {
        if(this.height != other.height || this.width != other.width) return;
        for (int i = 0; i < values.length; i++) {
            values[i] = values[i] || other.values[i];
        }
    }

    public void orWith(BooleanRegion mask, int row, int col) {
        // TODO checks
        int index = 0;
        for (int i = row; i < row + mask.height; i++) {
            for (int j = col; j < col + mask.width; j++) {
                setValue(i,j,getValue(i,j) || mask.getValueAtIndex(index));
                index++;
            }
        }
    }

    public void andWith(BooleanRegion mask, int row, int col) {
        // TODO checks
        int index = 0;
        for (int i = row; i < row + mask.height; i++) {
            for (int j = col; j < col + mask.width; j++) {
                setValue(i,j,getValue(i,j) && mask.getValueAtIndex(index));
                index++;
            }
        }
    }

    public void andWith(BooleanRegion other) {
        if(this.height != other.height || this.width != other.width) return;
        for (int i = 0; i < values.length; i++) {
            values[i] = values[i] && other.values[i];
        }
    }

    public BooleanRegion smoothEdges(){
        return BooleanRegionSmoother.smoothEdges(this);
    }
    public BooleanRegion smoothEdges(int mask_id) {
        return BooleanRegionSmoother.smoothEdgesUsingMask(this,mask_id);
    }


    public Pixel getFirstTrue() {
        Pixel p = new Pixel();
        int index = 0;
        for (p.row = 0; p.row < height; p.row++){
            for (p.col = 0; p.col < width; p.col++){
                if(values[index++])
                    return p;
            }
        }
        return null;
    }


    public String toStringFormatted(){
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < width+2; j++)
            sb.append("-");
        sb.append("\n");
        int index = 0;
        for (int i = 0; i < height; i++) {
            sb.append("|");
            for (int j = 0; j < width; j++) {
                sb.append((values[index++] ? '*' : ' '));
            }
            sb.append("|\n");
        }
        for (int j = 0; j < width+2; j++)
            sb.append("-");
        return sb.toString();
    }

    public BufferedImage toBufferedImage() {
        BufferedImage b = new BufferedImage(width,height,BufferedImage.TYPE_BYTE_GRAY);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                b.setRGB(j,i,(getValue(i,j) ? 0 : Integer.MAX_VALUE));
            }
        }
        return b;
    }

    public BooleanRegion getSubRegion(int row, int col, int width, int height) {
        BooleanRegion br = new BooleanRegion(width,height);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                br.setValue(i,j,getValue(i+row,j+col));
            }
        }
        return br;
    }

    public Pixel getAverageTrue(Pixel centre, int search_width, int search_height) {

        // basic search rectangle
        Rectangle sample_region = new Rectangle(
                Math.max(centre.col-search_width/2,0),  // x
                Math.max(centre.row-search_height/2,0), // y
                search_width,                           // width
                search_height                           // height
        );

        // adjust for limits of region
        sample_region.y = Math.min(sample_region.y, height - search_height);
        sample_region.x = Math.min(sample_region.x, width - search_width);

        return getAverageTrue(sample_region);

    }

    public Pixel getAverageTrue(Rectangle r) {
        Pixel average = new Pixel();
        int trueCount = 0;
        int index = r.y * width + r.x;
        for (int sample_region_row = 0; sample_region_row < r.height; sample_region_row++) {
            for(int sample_region_col = 0; sample_region_col < r.width; sample_region_col++){
                if(values[index++]){
                    trueCount++;
                    average.row += sample_region_row;
                    average.col += sample_region_col;
                }
            }
            index += (width - r.width);
        }
        Pixel ave_in_region = average.divide(trueCount);
        Pixel avePixel = new Pixel(
                r.y + ave_in_region.row,
                r.x + ave_in_region.col
        );
        return avePixel;

    }




}
