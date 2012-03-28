package kinect.stores;

import kinect.geometry.Pixel;
import kinect.geometry.Position;
import kinect.geometry.Vector;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: John
 * Date: 23/02/12
 * Time: 13:25
 * <p/>
 * Records historic position data and provides methods
 * to analyse positions, such as averaging
 */
public class PixelRecorder {

    private ArrayList<Pixel> ps = new ArrayList<Pixel>();
    private Pixel overall_sum = new Pixel();
    private Pixel overall_average = new Pixel();

    public void addPixel(Pixel p) {
        ps.add(p);

        overall_sum = overall_sum.add(p);
        overall_average = overall_sum.divide(ps.size());

    }

    public Pixel getPreviousPosition(int countback) {
        if (countback >= ps.size())
            return null;
        return ps.get(ps.size() - 1 - countback);
    }

    public Pixel getMovingAverage(int countback) {
        Pixel ave = new Pixel();
        for (int i = 0; i < countback && i < ps.size(); i++)
            ave = ave.add(ps.get(ps.size() - 1 - i));
        return ave.divide(countback);
    }

    public Pixel getAverage() {
        return overall_average;
    }

    public ArrayList<Pixel> getPreviousNPixels(int n) {
        int start_index = Math.max(0, ps.size() - n);
        ArrayList<Pixel> sub_list = new ArrayList<Pixel>();
        sub_list.addAll(ps.subList(start_index, ps.size()));
        return sub_list;
    }

    public int getRecordCount() {
        return ps.size();
    }

    /**
     * provides the limiting sphere for the last countback positions recorded in pr.
     *
     * @param countback - number of positions to consider
     * @return limiting sphere as LimitingSphere object
     */
    public LimitingDisc simpleAverage(int countback) {

        LimitingDisc sl = new LimitingDisc();
        sl.average = getMovingAverage(countback);
        ArrayList<Pixel> ps = getPreviousNPixels(countback);
        for (Pixel p : ps) {
            double displacement = p.distanceTo(sl.average);
            sl.radius = Math.max(sl.radius, displacement);
        }
        return sl;

    }
}
