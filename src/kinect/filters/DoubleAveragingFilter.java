package kinect.filters;

/**
 * Created by IntelliJ IDEA.
 * User: John
 * Date: 23/03/12
 * Time: 15:34
 * To change this template use File | Settings | File Templates.
 */
public class DoubleAveragingFilter extends AveragingFilter {

    AveragingFilter second_degree_averager;

    public DoubleAveragingFilter(int samples_to_keep){
        super(samples_to_keep);
        second_degree_averager = new AveragingFilter(samples_to_keep);
    }

    public double get(){
        return super.get() + (super.get() - second_degree_averager.get());
    }

    public void put(double sample){
        super.put(sample);
        second_degree_averager.put(get());
    }

    public void reset(){
        super.reset();
        second_degree_averager.reset();
    }

    public double forecast(int k){
        return super.get() + ((super.get() - second_degree_averager.get()) * k);
    }

}
    
    
    
