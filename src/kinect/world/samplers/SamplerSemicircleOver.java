package kinect.world.samplers;

/**
 * Created by IntelliJ IDEA.
 * User: John
 * Date: 23/02/12
 * Time: 17:38
 * <p/>
 * Semi-circluar sampler above the base pixel
 */
public class SamplerSemicircleOver extends SamplerCircle {

    public SamplerSemicircleOver(double radius, int sample_count) {
        super(radius, sample_count, 0, Math.PI);
    }

}
