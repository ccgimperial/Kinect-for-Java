package kinect.files;

import kinect.Kinect;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * Created with IntelliJ IDEA.
 * User: John
 * Date: 19/04/12
 * Time: 12:28
 * To change this template use File | Settings | File Templates.
 */
public class FileHandler {

    public static void writeDepthToFile(File file) throws IOException {
        FileChannel fc = new FileOutputStream(file, false).getChannel();
        Kinect.DEPTH_BUFFER.rewind();
        fc.write(Kinect.DEPTH_BUFFER);
        fc.close();
    }

    public static void writeVideoToFile(File file) throws IOException {
        FileChannel fc = new FileOutputStream(file, false).getChannel();
        Kinect.VIDEO_BUFFER.rewind();
        fc.write(Kinect.VIDEO_BUFFER);
        fc.close();
    }

    public static void readDepthFromFile(File file) throws IOException {
        FileChannel fc = new FileInputStream(file).getChannel();
        Kinect.DEPTH_BUFFER.rewind();
        fc.read(Kinect.DEPTH_BUFFER);
        fc.close();
    }

    public static void readVideoFromFile(File file) throws IOException {
        FileChannel fc = new FileInputStream(file).getChannel();
        Kinect.VIDEO_BUFFER.rewind();
        fc.read(Kinect.VIDEO_BUFFER);
        fc.close();
    }

}
