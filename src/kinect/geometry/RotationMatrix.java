package kinect.geometry;

/**
 * Created by IntelliJ IDEA.
 * User: John
 * Date: 13/03/12
 * Time: 16:26
 * To change this template use File | Settings | File Templates.
 */
public class RotationMatrix extends SquareMatrix {

    /** creates a rotation matrix for given angles around x,y and z axes
     *
     * @param x_angle
     * @param y_angle
     * @param z_angle
     */
    public RotationMatrix(double x_angle, double y_angle, double z_angle) {
        super(3);

        SquareMatrix Rx = new SquareMatrix(3);
        SquareMatrix Ry = new SquareMatrix(3);
        SquareMatrix Rz = new SquareMatrix(3);

        Rx.set(0,0,1);
        Rx.set(1,1, Math.cos(x_angle));
        Rx.set(1,2,-Math.sin(x_angle));
        Rx.set(2,1, Math.sin(x_angle));
        Rx.set(2,2, Math.cos(x_angle));

        Ry.set(0,0, Math.cos(y_angle));
        Ry.set(0,2, Math.sin(y_angle));
        Ry.set(1,1,1);
        Ry.set(2,0,-Math.sin(y_angle));
        Ry.set(2,2, Math.cos(y_angle));

        Rz.set(0,0, Math.cos(z_angle));
        Rz.set(0,1,-Math.sin(z_angle));
        Rz.set(1,0, Math.sin(z_angle));
        Rz.set(1,1, Math.cos(z_angle));
        Rz.set(2,2,1);

        SquareMatrix Rxy = Rx.multiplyMatrix(Ry);
        SquareMatrix Rxyz = Rxy.multiplyMatrix(Rz);

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                this.set(row,col,Rxyz.at(row,col));
            }
        }
    }
    
    public Position rotatePosition(Position p){

        Position r = new Position();
        r.x = at(0,0) * p.x + at(0,1) * p.y + at(0,2) * p.z;
        r.y = at(1,0) * p.x + at(1,1) * p.y + at(1,2) * p.z;
        r.z = at(2,0) * p.x + at(2,1) * p.y + at(2,2) * p.z;
        return r;

    }
    
    
    
}
