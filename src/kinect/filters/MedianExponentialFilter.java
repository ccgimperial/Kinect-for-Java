package kinect.filters;

/**
 * Created by IntelliJ IDEA.
 * User: John
 * Date: 23/03/12
 * Time: 16:57
 * To change this template use File | Settings | File Templates.
 */
public class MedianExponentialFilter implements Filter{

    MedianFilter median_filter;
    ExponentialFilter exp_filter;

    public MedianExponentialFilter(int samples_to_keep, float _alpha){
        median_filter = new MedianFilter(samples_to_keep);
        exp_filter = new ExponentialFilter(_alpha);
    }

    public double get(){
        return exp_filter.get();
    }

    public void put(double sample){
        median_filter.put(sample);
        exp_filter.put(median_filter.get());
    }

    public double forecast(int k){
        return exp_filter.forecast(k);
    }

    public void reset(){
        exp_filter.reset();
        median_filter.reset();
    }

}