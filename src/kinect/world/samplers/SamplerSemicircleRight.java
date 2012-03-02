package kinect.world.samplers;

/**
 * Created by IntelliJ IDEA.
 * User: John
 * Date: 23/02/12
 * Time: 17:38
 * <p/>
 * Semi-circluar sampler to the right of the base pixel
 */
public class SamplerSemicircleRight extends SamplerCircle {

    public SamplerSemicircleRight(double radius, int sample_count) {
        super(radius, sample_count, -Math.PI / 2, Math.PI / 2);
    }

}
