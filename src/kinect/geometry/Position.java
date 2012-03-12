package kinect.geometry;

import java.text.DecimalFormat;

public class Position {

    protected static DecimalFormat formatter = new DecimalFormat(" 0.000;-0.000");

    public double x = 0;
    public double y = 0;
    public double z = 0;

    public Position add(Position other) {
        Position r = new Position();
        r.x = this.x + other.x;
        r.y = this.y + other.y;
        r.z = this.z + other.z;
        return r;
    }

    public Position subtract(Position other) {
        Position r = new Position();
        r.x = this.x - other.x;
        r.y = this.y - other.y;
        r.z = this.z - other.z;
        return r;
    }

    public Position divide(double divisor) {
        Position r = new Position();
        r.x = this.x / divisor;
        r.y = this.y / divisor;
        r.z = this.z / divisor;
        return r;
    }

    public Position multiply(double divisor) {
        Position r = new Position();
        r.x = this.x * divisor;
        r.y = this.y * divisor;
        r.z = this.z * divisor;
        return r;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{"
                + formatter.format(x) + ","
                + formatter.format(y) + ","
                + formatter.format(z) + '}';
    }

}
