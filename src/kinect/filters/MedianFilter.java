package kinect.filters;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: John
 * Date: 23/03/12
 * Time: 16:48
 * To change this template use File | Settings | File Templates.
 */
public class MedianFilter implements Filter {

    ArrayList<Double> samples = new ArrayList<Double>();
    ArrayList<Double> sorted_samples = new ArrayList<Double>();
    int max_samples;

    public MedianFilter(int samples_to_keep){
        max_samples = samples_to_keep;
        reset();
    }

    public double get(){
        return sorted_samples.get(sorted_samples.size()/2);
    }

    public void put(double sample){
        samples.add(sample);
        sorted_samples.add(sample);
        if(samples.size() > max_samples){
            double lost = samples.remove(0);
            sorted_samples.remove(lost);
        }
        Collections.sort(sorted_samples);
    }

    public double forecast(int k){
        return get();
    }

    public void reset(){
        samples.clear();
        sorted_samples.clear();
    }

}
