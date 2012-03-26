package kinect.filters.position;

import kinect.geometry.Position;

/**
 * Created by IntelliJ IDEA.
 * User: John
 * Date: 23/03/12
 * Time: 15:30
 * Superclass for all PixelFilters
 */
public interface PositionFilter {
        public abstract void reset();
        public abstract void put(Position sample);
        public abstract Position get();
        public abstract Position forecast(int k);
}
