package kinect.filters.pixel;

import kinect.geometry.Pixel;

/**
 * Created by IntelliJ IDEA.
 * User: John
 * Date: 23/03/12
 * Time: 15:30
 * Superclass for all PixelFilters
 */
public interface PixelFilter {
        public abstract void reset();
        public abstract void put(Pixel sample);
        public abstract Pixel get();
        public abstract Pixel forecast(int k);
}
