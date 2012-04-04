    package kinect.world.depth;

    import java.io.*;
    import java.nio.ByteBuffer;

    /**
     * Created with IntelliJ IDEA.
     * User: John
     * Date: 04/04/12
     * Time: 05:09
     * To change this template use File | Settings | File Templates.
     */
    public class DepthRegionFileHandler {

        static void saveToFile(DepthRegion dr, String filename) {

            try {
                BufferedWriter out = new BufferedWriter(new FileWriter(filename));
                for (int i = dr.top; i < dr.top + dr.height; i++) {
                    for (int j = dr.left; j < dr.left + dr.width; j++) {
                        int index = (i * 320 + j) * 2;
                        byte a = dr.DEPTH_BUFFER.get(index);
                        byte b = dr.DEPTH_BUFFER.get(index + 1);
                        out.write(a);
                        out.write(b);
                    }
                }
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

        }

        static void loadFromFile(DepthRegion dr, String filename) {
            try {
                BufferedReader f = new BufferedReader(new FileReader(new File(filename)));
                dr.DEPTH_BUFFER = ByteBuffer.allocate(320 * 240 * 2);
                for (int i = dr.top; i < dr.top + dr.height; i++) {
                    for (int j = dr.left; j < dr.left + dr.width; j++) {
                        int index = (i * 320 + j) * 2;
                        dr.DEPTH_BUFFER.put(index, (byte) f.read());
                        dr.DEPTH_BUFFER.put(index+1, (byte) f.read());
                    }
                }
                f.close();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }


    }
