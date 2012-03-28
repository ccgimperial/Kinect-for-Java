package kinect.stores;

import kinect.geometry.Pixel;

/**
 * Created by IntelliJ IDEA.
 * User: John
 * Date: 28/03/12
 * Time: 12:23
 * To change this template use File | Settings | File Templates.
 */
public class LimitingDisc {

    public Pixel average;
    public double radius;

    LimitingDisc(){}

    @Override
    public String toString() {
        return "LimitingDisc{" +
                "average=" + average +
                ", radius=" + radius +
                '}';
    }
}
