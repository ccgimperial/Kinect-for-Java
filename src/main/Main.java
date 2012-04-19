package main;

import kinect.Kinect;
import kinect.KinectObserver;
import kinect.skeleton.Skeleton;
import kinect.visual.Imager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Main implements KinectObserver {

    static BufferedImage img_video = Imager.getNewVideoImage();
    static BufferedImage img_depth = Imager.getNewDepthGreyscale();
    static BufferedImage img_player = Imager.getNewDepthGreyscale();
    static JFrame frame;

    public static void main(String[] args) {

        // create pkg.old_stuff.display frame
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(new JLabel(new ImageIcon(img_video)), BorderLayout.NORTH);
        JPanel j = new JPanel(new FlowLayout());
        j.add(new JLabel(new ImageIcon(img_depth)));
        j.add(new JLabel(new ImageIcon(img_player)));

        frame.getContentPane().add(j, BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);

        Kinect.init(new Main());

    }

    // implement the Depth Event Handler
    public void DepthEvent() {
        Imager.updateGrey320ImageWithDepthGrey(img_depth);
        Imager.updateGrey320ImageWithPlayerMask(img_player);
        frame.repaint();
    }

    // implement the Video Event Handler
    public void VideoEvent() {
        Imager.updateColour640ImageWithVideo(img_video);
        frame.repaint();
    }

    // implement the Skeleton Event Handler
    public void SkeletonEvent() {

        int id = Skeleton.getTrackedSkeletonId();
        frame.setTitle("no skeleton " + id);
        if (Skeleton.isTrackingSomeSkeleton())
            frame.setTitle("got skeleton " + Skeleton.getTrackedSkeletonId());

    }

}
