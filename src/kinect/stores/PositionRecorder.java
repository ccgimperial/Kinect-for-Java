package kinect.stores;

import kinect.geometry.Position;

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

    public void addPosition(Position p) {
        ps.add(p);

        overall_sum = overall_sum.add(p);
        overall_average = overall_sum.divide(ps.size());

    }

    public Position getPreviousPosition(int countback) {
        if (countback >= ps.size())
            return null;
        return ps.get(ps.size() - 1 - countback);
    }

    public Position getMovingAverage(int countback) {
        Position ave = new Position();
        for (int i = 0; i < countback && i < ps.size(); i++)
            ave = ave.add(ps.get(ps.size() - 1 - i));
        return ave.divide(countback);
    }

    public Position getAverage() {
        return overall_average;
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
