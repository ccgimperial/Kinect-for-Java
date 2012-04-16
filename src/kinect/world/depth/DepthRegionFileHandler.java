    package kinect.world.depth;

    import java.io.*;
    import java.nio.ByteBuffer;
    import java.util.StringTokenizer;

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

                // save placement information
                out.write(dr.top + "|" + dr.left + "|" + dr.width + "|" + dr.height + "\n");

                // save depth information
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

        static void loadFromFileOld(DepthRegion dr, String filename) {
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


        public static void loadFromFile(DepthRegion dr, String filename) {
            try {
                BufferedReader f = new BufferedReader(new FileReader(new File(filename)));

                // load placement information
                String placement_string = f.readLine();
                StringTokenizer st = new StringTokenizer(placement_string,"|");
                dr.top = Integer.parseInt(st.nextToken());
                dr.left = Integer.parseInt(st.nextToken());
                dr.width = Integer.parseInt(st.nextToken());
                dr.height = Integer.parseInt(st.nextToken());

                // load information for depth field
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
