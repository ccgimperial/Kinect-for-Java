package main;

import kinect.geometry.Pixel;
import kinect.geometry.Position;
import kinect.world.Projection;

/**
 * Created by IntelliJ IDEA.
 * User: John
 * Date: 05/03/12
 * Time: 21:48
 * To change this template use File | Settings | File Templates.
 */
public class SimpleTests {
    public static void main(String[] args) {
        System.out.println("hi");

        Pixel pos_d = new Pixel();
        pos_d.col = 160;
        pos_d.row = 120;
        System.out.println(pos_d);
        Position p = Projection.depthPixelToDepthWorld(pos_d, 1);
        System.out.println(p);
        System.out.println(Projection.depthWorldToDepthPixel(p));

        Position vw = Projection.depthWorldToVideoWorld(p);
        Pixel vp = Projection.videoWorldToVideoPixel(vw);


//        System.out.println("-------------------------");
//
//        Pixel pos_v = new Pixel();
//        pos_v.col = 320;
//        pos_v.row = 240;
//        System.out.println(pos_v);
//        Position p2 = Projection.videoPixelToVideoWorld(pos_v, 1);
//        System.out.println(p2);
//        System.out.println(Projection.videoWorldToVideoPixel(p2));

        System.out.println("-------------------------");
        System.out.println("depth_pixel:" + pos_d);
        System.out.println("depth_world:" + p);
        System.out.println("video_world:" + vw);
        System.out.println("video_pixel:" + vp);
        
        Position vw2 = Projection.videoPixelToVideoWorld(vp,1);

        System.out.println("video_world:" + vw2);
        Position dw2 = Projection.videoWorldToDepthWorld(vw2);
        System.out.println("depth_world:" + dw2);
        System.out.println("depth_pixel:" + Projection.depthWorldToDepthPixel(dw2));



        
        

    }
}
