package main;

import kinect.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Main implements KinectObserver {

    static BufferedImage img_video = new BufferedImage(640,480,BufferedImage.TYPE_4BYTE_ABGR);
    static BufferedImage img_depth = new BufferedImage(320,240,BufferedImage.TYPE_BYTE_GRAY);
    static BufferedImage img_player = new BufferedImage(320,240,BufferedImage.TYPE_BYTE_GRAY);
    static JFrame frame;

    public static void main(String[] args) {

        // create display frame
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(new JLabel(new ImageIcon(img_video)),BorderLayout.NORTH);
        JPanel j = new JPanel(new FlowLayout());
        j.add(new JLabel(new ImageIcon(img_depth)));
        j.add(new JLabel(new ImageIcon(img_player)));
        
        frame.getContentPane().add(j,BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);

        Kinect.Init(new Main());
        
    }

    // implement the Depth Event Handler
    public void DepthEvent() {
        Imager.UpdateImageWithDepth(img_depth);
        Imager.UpdateImageWithPlayer(img_player);
        frame.repaint();
    }

    // implement the Video Event Handler
    public void VideoEvent() {
        Imager.UpdateImageWithVideo(img_video);
        frame.repaint();
    }

    // implement the Skeleton Event Handler
    public void SkeletonEvent() {

        int id = Skeleton.GetTrackedSkeletonId();
        frame.setTitle("no skeleton " + id);
        if(Skeleton.IsTrackingSkeleton())
            frame.setTitle("got skeleton " + Skeleton.GetTrackedSkeletonId());

    }

}
