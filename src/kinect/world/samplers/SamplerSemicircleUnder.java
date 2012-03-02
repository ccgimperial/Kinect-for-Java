package kinect.world.samplers;

/**
 * Created by IntelliJ IDEA.
 * User: John
 * Date: 23/02/12
 * Time: 17:38
 * <p/>
 * Semi-circluar sampler below the base pixel
 */
public class SamplerSemicircleUnder extends SamplerCircle {

    public SamplerSemicircleUnder(double radius, int sample_count) {
        super(radius, sample_count, Math.PI, Math.PI * 2);
    }

}
