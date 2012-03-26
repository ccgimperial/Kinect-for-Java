package kinect.filters;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: John
 * Date: 23/03/12
 * Time: 15:34
 * To change this template use File | Settings | File Templates.
 */
public class AveragingFilter implements Filter {

    private ArrayList<Double> samples = new ArrayList();
    private int max_samples;
    private double current_sum;
    private double current_average;

    public AveragingFilter(int samples_to_keep){
        max_samples = samples_to_keep;
    }

    public double get(){
        return current_average;
    }

    public void put(double sample){

        current_sum += sample;

        samples.add(sample);
        if(samples.size() > max_samples){
            current_sum -= samples.remove(0);
        }
        current_average = current_sum / samples.size();
    }

    public double forecast(int k){
        return current_average;
    }

    public void reset(){
        samples.clear();
        current_sum = 0;
        current_average = 0;
    }



}