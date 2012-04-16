    package kinect.world.video;

    import java.io.*;
    import java.nio.ByteBuffer;

    /**
     * Created with IntelliJ IDEA.
     * User: John
     * Date: 04/04/12
     * Time: 05:09
     * To change this template use File | Settings | File Templates.
     */
    public class VideoRegionFileHandler {

        static void saveToFile(VideoRegion dr, String filename) {

            try {
                BufferedWriter out = new BufferedWriter(new FileWriter(filename));
                for (int i = dr.top; i < dr.top + dr.height; i++) {
                    for (int j = dr.left; j < dr.left + dr.width; j++) {
                        int index = (i * 640 + j) * 4;
                        byte a = dr.VIDEO_BUFFER.get(index);
                        byte b = dr.VIDEO_BUFFER.get(index + 1);
                        byte c = dr.VIDEO_BUFFER.get(index + 2);
                        byte d = dr.VIDEO_BUFFER.get(index + 3);
                        out.write(a);
                        out.write(b);
                        out.write(c);
                        out.write(d);
                    }
                }
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

        }

        static void loadFromFile(VideoRegion dr, String filename) {
            try {
                BufferedReader f = new BufferedReader(new FileReader(new File(filename)));
                dr.VIDEO_BUFFER = ByteBuffer.allocate(640 * 480 * 4);
                for (int i = dr.top; i < dr.top + dr.height; i++) {
                    for (int j = dr.left; j < dr.left + dr.width; j++) {
                        int index = (i * 640 + j) * 4;
                        dr.VIDEO_BUFFER.put(index, (byte) f.read());
                        dr.VIDEO_BUFFER.put(index+1, (byte) f.read());
                        dr.VIDEO_BUFFER.put(index+2, (byte) f.read());
                        dr.VIDEO_BUFFER.put(index+3, (byte) f.read());
                    }
                }
                f.close();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }


    }
