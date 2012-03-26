package kinect.filters.pixel;

import kinect.filters.DoubleExponentialFilter;
import kinect.geometry.Pixel;

/**
 * Created by IntelliJ IDEA.
 * User: John
 * Date: 23/03/12
 * Time: 15:34
 * To change this template use File | Settings | File Templates.
 */
public class DoubleExponentialPixelFilter implements PixelFilter {

    private DoubleExponentialFilter row_filter;
    private DoubleExponentialFilter col_filter;

    public DoubleExponentialPixelFilter(double alpha, double gamma){
        row_filter = new DoubleExponentialFilter(alpha, gamma);
        col_filter = new DoubleExponentialFilter(alpha, gamma);
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