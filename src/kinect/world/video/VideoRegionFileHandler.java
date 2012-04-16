    package kinect.world.video;

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
    public class VideoRegionFileHandler {

        static void saveToFile(VideoRegion dr, String filename) {

            try {
                BufferedWriter out = new BufferedWriter(new FileWriter(filename));

                // save placement information
                out.write(dr.top + "|" + dr.left + "|" + dr.width + "|" + dr.height + "\n");

                // save video information
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

        static void loadFromFile(VideoRegion vr, String filename) {
            try {
                BufferedReader f = new BufferedReader(new FileReader(new File(filename)));

                // load placement information
                String placement_string = f.readLine();
                StringTokenizer st = new StringTokenizer(placement_string,"|");
                vr.top = Integer.parseInt(st.nextToken());
                vr.left = Integer.parseInt(st.nextToken());
                vr.width = Integer.parseInt(st.nextToken());
                vr.height = Integer.parseInt(st.nextToken());

                // load video information
                vr.VIDEO_BUFFER = ByteBuffer.allocate(640 * 480 * 4);
                for (int i = vr.top; i < vr.top + vr.height; i++) {
                    for (int j = vr.left; j < vr.left + vr.width; j++) {
                        int index = (i * 640 + j) * 4;
                        vr.VIDEO_BUFFER.put(index, (byte) f.read());
                        vr.VIDEO_BUFFER.put(index+1, (byte) f.read());
                        vr.VIDEO_BUFFER.put(index+2, (byte) f.read());
                        vr.VIDEO_BUFFER.put(index+3, (byte) f.read());
                    }
                }
                f.close();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }


    }
