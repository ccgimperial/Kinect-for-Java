package kinect.filters.pixel;

import kinect.filters.MedianFilter;
import kinect.geometry.Pixel;

/**
 * Created by IntelliJ IDEA.
 * User: John
 * Date: 23/03/12
 * Time: 15:34
 * To change this template use File | Settings | File Templates.
 */
public class MedianPixelFilter implements PixelFilter {

    private MedianFilter row_filter;
    private MedianFilter col_filter;

    public MedianPixelFilter(int samples_to_keep){
        row_filter = new MedianFilter(samples_to_keep);
        col_filter = new MedianFilter(samples_to_keep);
    }

    public Pixel get(){
        return new Pixel((int) row_filter.get(),(int) col_filter.get());
    }

    public void put(Pixel sample) {
        row_filter.put(sample.row);
        col_filter.put(sample.col);
    }

    public Pixel forecast(int k){
        return get();
    }

    public void reset(){
        row_filter.reset();
        col_filter.reset();
    }

}