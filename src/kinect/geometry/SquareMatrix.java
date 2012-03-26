package kinect.geometry;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: John
 * Date: 05/03/12
 * Time: 17:34
 *
 * Simple matrix class for doing manipulations.
 * Really heavyweight so don't use for performance!
 *
 */
public class SquareMatrix {

    int n;
    ArrayList<ArrayList<Double>> elements = new ArrayList<ArrayList<Double>>();

    public static String output_format = "0.00";

    /** Create a new square matrix of size n
     *
     * @param n
     */
    public SquareMatrix(int n) {
        if(n <= 0)
            n = 1;
        this.n = n;
        for (int i = 0; i < n; i++) {
            ArrayList<Double> row = new ArrayList<Double>();
            for (int j = 0; j < n; j++) {
                row.add(0.0);
            }
            elements.add(row);
        }
    }

    /** get the value at a particular row/column
     *
     * @param row
     * @param col
     * @return value at (row,col)
     */
    public double at(int row, int col) {
        return elements.get(row).get(col);
    }

    /** Set the value at a particular row/column
     *
     * @param row
     * @param col
     * @param value
     */
    public void set(int row, int col, double value) {
        elements.get(row).set(col,value);
    }

    /** Calculate the determinant.
     * Uses cofactors.
     *
     * @return determinant as double.
     */
    public double getDeterminant(){

        if(n==1) {
            return at(0, 0);
        }

        if(n==2)
            return (at(0,0) * at(1,1)) - (at(0,1) * at(1,0));

        // go across top row and multiply element value by
        // determinant of cofactors (n-1 x n-1 matrices missing
        // that row / column
        double res = 0;
        for (int i = 0; i < n; i++) {
            res += at(0,i) * getCofactor(0,i);
        }
        return res;

    }


    /** Calculates the matrix minor (sort of sub-matrix)
     * formed by removing the designated row and column.
     *
     * @param row
     * @param col
     * @return minor matrix
     */
    public SquareMatrix getMinorByRemoval(int row, int col){

        if(n == 1)
            return null;

        SquareMatrix cofactor = new SquareMatrix(n-1);
        int target_row = 0;
        for (int i = 0; i < n; i++) { // loop rows
            if(i == row)
                continue;
            int target_col = 0;
            for (int j = 0; j < n; j++) { // loop cols
                if(j == col)
                    continue;
                cofactor.set(target_row,target_col,at(i,j));
                target_col++;
            }
            target_row ++;
        }
        return cofactor;

    }

    /** Calculates the matrix transpose.
     * Doesn't affect the underlying matrix.
     *
     * @return transpose of the matrix
     */
    public SquareMatrix getTranspose(){
        SquareMatrix trans = new SquareMatrix(n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                trans.set(i, j, at(j, i));
            }
        }
        return trans;
    }

    /** Get the matrix cofactor for a given row and column
     *
     * @param row
     * @param col
     * @return cofactor for given position
     */
    public double getCofactor(int row, int col){
        double sgn = Math.pow(-1,row+col);
        return sgn * getMinorByRemoval(row,col).getDeterminant();
    }

    /** Returns a matrix of the cofactors at each row/col.
     * Matrix itself is unaffected.
     *
     * @return cofactor matrix
     */
    public SquareMatrix getCofactorMatrix(){

        SquareMatrix cof = new SquareMatrix(n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                cof.set(i,j,getCofactor(i,j));
            }
        }
        return cof;

    }

    /** Returns the inverse of the matrix.
     * Matrix itself is unaffected.
     *
     * @return inverted matrix
     */
    public SquareMatrix getInverse(){

        return getCofactorMatrix().getTranspose().multiplyScalar(1 / getDeterminant());

    }

    /** Returns the result of muliplying through by a factor.
     * Matrix itself is unaffected.
     *
     * @return multiplied matrix
     */
    public SquareMatrix multiplyScalar(double m) {

        SquareMatrix sm = new SquareMatrix(n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                sm.set(i,j,at(i,j)*m);
            }
        }
        return sm;

    }

    /** Returns the result of muliplying through by a factor.
     * Matrix itself is unaffected.
     *
     * @return multiplied matrix
     */
    public SquareMatrix multiplyMatrix(SquareMatrix other) {

        if(n != other.n)
            return null;

        SquareMatrix sm = new SquareMatrix(n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // vector product row i in this with col j in other

                ArrayList<Double> row = this.getRow(i);
                ArrayList<Double> column = other.getColumn(j);

                double res = 0;
                for (int k = 0; k < n; k++) {
                    res += row.get(k) * column.get(k);
                }
                sm.set(i,j,res);
            }
        }
        return sm;

    }

    /** Returns an ArrayList for a particular column
     *
     * @param column
     * @return ArrayList of that column
     */
    public ArrayList<Double> getColumn(int column) {
        ArrayList<Double> res = new ArrayList<Double>();
        for (int i = 0; i < n; i++) {
            res.add(at(i,column));
        }
        return res;
    }

    /** Returns an ArrayList for a particular row
     *
     * @param row
     * @return ArrayList of that row
     */
    public ArrayList<Double> getRow(int row) {
        ArrayList<Double> res = new ArrayList<Double>();
        for (int i = 0; i < n; i++) {
            res.add(at(row,i));
        }
        return res;
    }


    @Override
    public String toString() {

        DecimalFormat df = new DecimalFormat(output_format);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            if(i != 0)
                sb.append("\n");
            for (int j = 0; j < n; j++) {
                if(j != 0)
                    sb.append(",");
                sb.append(df.format(at(i,j)));
            }
        }
        return sb.toString();

    }

}
