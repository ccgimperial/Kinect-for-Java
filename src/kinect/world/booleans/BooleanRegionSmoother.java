package kinect.world.booleans;

/**
 * Created with IntelliJ IDEA.
 * User: John
 * Date: 03/04/12
 * Time: 13:31
 * To change this template use File | Settings | File Templates.
 */
public class BooleanRegionSmoother {

    /** smooth the edges of the true region by setting all pixels
     *  neighbouring a true also to true.
     *
     * @return
     */
    public static BooleanRegion smoothEdges(BooleanRegion src) {

        BooleanRegion targ = new BooleanRegion(src.width,src.height);
        int index = 0;
        for (int i = 0; i < targ.height; i++) {
            for (int j = 0; j < targ.width; j++) {

                if(src.values[index]){

                    // this row
                    targ.values[index] = true;
                    if(j > 0        ) targ.values[index-1] = true;
                    if(j < src.width - 1) targ.values[index+1] = true;

                    // row above
                    if(i>0) {
                        targ.values[index - src.width] = true;
                        if(j > 0         ) targ.values[index-1-src.width] = true;// look back
                        if(j < src.width - 1 ) targ.values[index+1-src.width] = true; // look ahead
                    }

                    // row below
                    if (i < src.height - 1){
                        targ.values[index + src.width] = true;
                        if (j > 0         )  targ.values[index-1+src.width] = true; // look back
                        if (j < src.width - 1 )  targ.values[index+1+src.width] = true; // look ahead
                    }
                }
                index++;
            }
        }
        return targ;
    }


    public static BooleanRegion smoothEdgesUsingMask(BooleanRegion src, int mask_id) {

        BooleanRegion mask = getMaskById(mask_id);
        int space_at_edges = (mask.width - 1) / 2;

        BooleanRegion targ = new BooleanRegion(src.width + 2*space_at_edges,src.height + 2*space_at_edges);

        int index = 0;
        for (int i = space_at_edges; i < space_at_edges + src.height; i++) {
            for (int j = space_at_edges; j < space_at_edges + src.width; j++) {
                if(src.values[index++])
                    targ.orWith(mask,i-space_at_edges,j-space_at_edges);
            }
        }

        return targ.getSubRegion(space_at_edges,space_at_edges,src.width,src.height);

    }


    /** @return smoothing mask from a number of options
     */
    private static BooleanRegion getMaskById(int mask_id) {
        switch (mask_id) {
            case MASK_CROSS_3:
                return new BooleanRegion(3,3,new int[]{0,1,0,1,1,1,0,1,0});
            case MASK_SQUARE_3:
                return new BooleanRegion(3,3,new int[]{1,1,1,1,1,1,1,1,1});
            case MASK_STAR_5:
                return new BooleanRegion(5,5,new int[]{0,0,1,0,0,0,1,1,1,0,1,1,1,1,1,0,1,1,1,0,0,0,1,0,0});
            case MASK_CIRC_5:
                return new BooleanRegion(5,5,new int[]{0,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,1,1,1,0});
            case MASK_STAR_7:
                return new BooleanRegion(7,7,new int[]{0,0,0,1,0,0,0,0,0,1,1,1,0,0,0,1,1,1,1,1,0,1,1,1,1,1,1,1,0,1,1,1,1,1,0,0,0,1,1,1,0,0,0,0,0,1,0,0,0});
            case MASK_CIRC_7:
                return new BooleanRegion(7,7,new int[]{0,0,1,1,1,0,0,0,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,0,0,0,1,1,1,0,0});
            default:
                return null;
        }
    }
    public final static int MASK_CROSS_3 = 0;
    public final static int MASK_SQUARE_3 = 1;
    public final static int MASK_STAR_5 = 2;
    public final static int MASK_CIRC_5 = 3;
    public final static int MASK_STAR_7 = 4;
    public final static int MASK_CIRC_7 = 5;




}
