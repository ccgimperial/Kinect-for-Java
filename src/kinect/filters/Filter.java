package kinect.filters;

/**
 * Created by IntelliJ IDEA.
 * User: John
 * Date: 23/03/12
 * Time: 15:30
 * Superclass for all PixelFilters
 */
public interface Filter {
    public abstract void reset();
    public abstract void put(double sample);
    public abstract double get();
    public abstract double forecast(int k);
}
