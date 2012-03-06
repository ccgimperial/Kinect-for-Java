package kinect.visual;

/**
 * Created by IntelliJ IDEA.
 * User: John
 * Date: 06/03/12
 * Time: 16:04
 *
 * Simple class to calculate frames per second.
 * Relies on the code calling the getFramesPerSecond
 * function once per frame.
 */
public class FPSCalculator {

    private static int frame_count = 0;
    private static long prev_time = 0;
    private static double fps = 0;

    /** Call this once per frame to get the frames per
     * second (only works if call each frame).
     * @return frames per second
     */
    public static double getFramesPerSecond() {
        if(++frame_count == 30){ // update once per second
            frame_count = 0;
            long now_time = System.currentTimeMillis();
            long duration = now_time - prev_time;
            prev_time = now_time;
            fps =  1000.0 / duration * 30.0;
        }
        return fps;
    }
}
