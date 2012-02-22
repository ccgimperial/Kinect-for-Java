package kinect;


public interface KinectObserver {

    public void DepthEvent();
    public void VideoEvent();
    public void SkeletonEvent();

}
