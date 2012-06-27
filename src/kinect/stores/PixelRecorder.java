package kinect.stores;

import kinect.geometry.Pixel;

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
    int capacity;

    public PixelRecorder(int capacity) {
        this.capacity = capacity;
    }

    public void addPixel(Pixel p) {
        ps.add(p);
        overall_sum = overall_sum.add(p);
        if (ps.size() > capacity) {
            Pixel removed = ps.remove(0);
            overall_sum = overall_sum.subtract(removed);
        }
        overall_average = overall_sum.divide(ps.size());
    }

    public Pixel getAverage() {
        return overall_average;
    }

    public Pixel getAverageLastN(int countback) {
        Pixel ave = new Pixel();
        for (int i = 0; i < countback && i < ps.size(); i++)
            ave = ave.add(ps.get(ps.size() - 1 - i));
        return ave.divide(countback);
    }

    public double getRadius() {
        return getRadiusLastN(ps.size());
    }

    public double getRadiusLastN(int countback) {
        double radius = 0;
        Pixel ave = getAverageLastN(countback);
        ArrayList<Pixel> ps = getPreviousNPixels(countback);
        for (Pixel p : ps) {
            double displacement = p.distanceTo(ave);
            radius = Math.max(radius, displacement);
        }
        return radius;
    }


    public Pixel getPreviousPosition(int countback) {
        if (countback >= ps.size())
            return null;
        return ps.get(ps.size() - 1 - countback);
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

}
