package kinect.filters.pixel;

import kinect.filters.ExponentialFilter;
import kinect.geometry.Pixel;

/**
 * Created by IntelliJ IDEA.
 * User: John
 * Date: 23/03/12
 * Time: 15:34
 * To change this template use File | Settings | File Templates.
 */
public class ExponentialPixelFilter implements PixelFilter {
    
    private ExponentialFilter row_filter;
    private ExponentialFilter col_filter;

    public ExponentialPixelFilter(double alpha){
        row_filter = new ExponentialFilter(alpha);
        col_filter = new ExponentialFilter(alpha);
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