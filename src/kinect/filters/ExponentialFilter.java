package kinect.filters;

/**
 * Created by IntelliJ IDEA.
 * User: John
 * Date: 23/03/12
 * Time: 16:02
 * Exponential filter for pixels
 */
public class ExponentialFilter implements Filter {

    double alpha;
    double value;

    public ExponentialFilter(double _alpha)
    {
        alpha = _alpha;
    }

    public double get(){
        return value;
    }

    public void put(double sample){
        value = (1.0 - alpha) * value + alpha * sample;
    }

    public void reset(){
        value = 0;
    }


    public double forecast(int k){
        return value;
    }

}
