package kinect.world.samplers;

import kinect.geometry.Pixel;
import kinect.geometry.Vector;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: John
 * Date: 23/02/12
 * Time: 17:49
 * <p/>
 * SamplerCircle provides a sample of n points in
 * a circle of a specified radius around a base pixel
 */
public class SamplerCircle implements Sampler {

    private double radius;
    private int sample_count;
    private double start_angle;
    private double end_angle;

    ArrayList<Vector> pixel_offsets = null;

    public SamplerCircle(double radius, int sample_count) {
        this.radius = radius;
        this.sample_count = sample_count;
        this.start_angle = 0;
        this.end_angle = Math.PI * 2 * sample_count / (sample_count + 1);
    }

    public SamplerCircle(double radius, int sample_count, double start_angle, double end_angle) {
        this.radius = radius;
        this.sample_count = sample_count;
        this.start_angle = start_angle;
        this.end_angle = end_angle;
    }

    @Override
    public ArrayList<Pixel> getSamples(Pixel base_pixel) {

        if (pixel_offsets == null) {
            pixel_offsets = new ArrayList<Vector>();
            double angle_per_sample = (end_angle - start_angle) / (sample_count - 1);
            for (int i = 0; i < sample_count; i++) {
                double sample_angle = start_angle + i * angle_per_sample;

                Vector offset_vector = new Vector();
                offset_vector.x = radius * Math.cos(sample_angle);
                offset_vector.y = radius * Math.sin(sample_angle);
                pixel_offsets.add(offset_vector);

            }

        }

        ArrayList<Pixel> res = new ArrayList<Pixel>();
        for (Vector v : pixel_offsets) {
            Pixel sample_pixel = new Pixel();
            sample_pixel.col = base_pixel.col + (int) Math.round(v.x);
            sample_pixel.row = base_pixel.row + (int) Math.round(v.y);
            res.add(sample_pixel);
        }
        return res;

    }

}
