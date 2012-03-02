package kinect.world.samplers;

import kinect.geometry.Pixel;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: John
 * Date: 23/02/12
 * Time: 17:31
 * <p/>
 * Interface defines sampler behaviour
 * Samplers provide the set of pixels in their sample
 * Concrete samples encapsulate a method of determining
 * a set of sample pixels
 */
public interface Sampler {

    public ArrayList<Pixel> getSamples(Pixel base_pixel);

}
