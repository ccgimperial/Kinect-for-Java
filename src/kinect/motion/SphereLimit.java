package kinect.motion;

import kinect.geometry.Position;
import kinect.geometry.Vector;
import kinect.stores.PositionRecorder;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: John
 * Date: 23/02/12
 * Time: 19:04
 * <p/>
 * Class to identify the size and average center of
 * the last n positions from a position recorder
 */
public class SphereLimit {

    public Position average;
    public double radius;

    private SphereLimit() {

    }

    /**
     * provides the limiting sphere for the last countback
     * positions recorded in pr.
     *
     * @param pr        - position recorder to use
     * @param countback - number of positions to consider
     * @return limiting sphere as SphereLimit object
     */
    public static SphereLimit simpleAverage(PositionRecorder pr, int countback) {

        SphereLimit sl = new SphereLimit();
        sl.average = pr.getMovingAverage(countback);
        ArrayList<Position> ps = pr.getPreviousNPositions(countback);
        for (Position p : ps) {
            Vector displacement = new Vector(sl.average, p);
            sl.radius = Math.max(sl.radius, displacement.getLength());
        }
        return sl;

    }


    /**
     * This provides the limiting sphere for the previous <i>countback</i>
     * points after excluding those which are more than <i>sds</i> standard
     * deviations out from a first attempt at finding the average.
     * <p/>
     * Normal dist'n e.g. 68.2% within 2 sds, 95% within 2, 99.7% within 3
     *
     * @param pr        PositionRecorder for the points
     * @param countback number of positions to consider
     * @param sds       number of standard deviations to include, exclude points outside these limits
     * @return SphereLimit with average and radius of sds standard deviation equivalents
     */
    public static SphereLimit averageWithinStandardDeviations___BUGGY____(PositionRecorder pr, int countback, float sds) {

        Position ave = pr.getMovingAverage(countback);
        ArrayList<Position> ps = pr.getPreviousNPositions(countback);
        double sd_total = 0;
        // calulate the standard deviation
        for (Position p : ps) {
            Vector displacement = new Vector(ave, p);
            sd_total += displacement.getLength() * displacement.getLength();
        }
        double sd = Math.sqrt(sd_total / countback);

        // go through to select only those within given range
        PositionRecorder pr_subset = new PositionRecorder();
        int sub_count = 0;
        for (Position p : ps) {
            Vector displacement = new Vector(ave, p);
            // keep only those within certain number of sds
            if (displacement.getLength() <= sd * sds)
                pr_subset.addPosition(p);
        }

        // return the result based upon that sub-set
        return simpleAverage(pr_subset, sub_count);

    }


}
