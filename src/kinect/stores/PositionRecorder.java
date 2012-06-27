package kinect.stores;

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
public class PositionRecorder {

    private ArrayList<Position> ps = new ArrayList<Position>();
    private Position overall_sum = new Position();
    private Position overall_average = new Position();
    int capacity;

    public PositionRecorder(int capacity) {
        this.capacity = capacity;
    }

    public void addPosition(Position p) {
        ps.add(p);
        overall_sum = overall_sum.add(p);
        if (ps.size() > capacity) {
            Position removed = ps.remove(0);
            overall_sum = overall_sum.subtract(removed);
        }
        overall_average = overall_sum.divide(ps.size());
    }

    public Position getAverage() {
        return overall_average;
    }

    public Position getAverageLastN(int countback) {
        Position ave = new Position();
        for (int i = 0; i < countback && i < ps.size(); i++)
            ave = ave.add(ps.get(ps.size() - 1 - i));
        return ave.divide(countback);
    }


    public double getRadius() {
        return getRadiusLastN(ps.size());
    }

    public double getRadiusLastN(int countback) {
        double radius = 0;
        Position ave = getAverageLastN(countback);
        ArrayList<Position> ps = getPreviousNPositions(countback);
        for (Position p : ps) {
            Vector displacement = new Vector(ave, p);
            radius = Math.max(radius, displacement.getLength());
        }
        return radius;
    }

    public Position getPreviousPosition(int countback) {
        if (countback >= ps.size())
            return null;
        return ps.get(ps.size() - 1 - countback);
    }

    public ArrayList<Position> getPreviousNPositions(int n) {
        int start_index = Math.max(0, ps.size() - n);
        ArrayList<Position> sub_list = new ArrayList<Position>();
        sub_list.addAll(ps.subList(start_index, ps.size()));
        return sub_list;
    }

    public int getRecordCount() {
        return ps.size();
    }

}
