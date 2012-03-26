package kinect.filters;

/**
 * Created by IntelliJ IDEA.
 * User: John
 * Date: 23/03/12
 * Time: 16:07
 * To change this template use File | Settings | File Templates.
 */
public class DoubleExponentialFilter implements Filter {

    double alpha,gamma;
    double value;
    double trend;

    public DoubleExponentialFilter(double alpha, double gamma)
    {
        this.alpha = alpha;
        this.gamma = gamma;
    }

    public double get(){
        return value;
    }

    public void put(double sample){
        double previous_value = value;
        value = (1.0 - alpha) * (value + trend) + alpha * sample;
        trend = gamma * (value - previous_value) + (1.0f - gamma) * trend;
    }

    public void reset(){
        value = 0;
        trend = 0;
    }

    public double forecast(int k){
        return value + trend * k;
    }

}
