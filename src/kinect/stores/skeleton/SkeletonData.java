package kinect.stores.skeleton;

import kinect.skeleton.Joint;
import kinect.skeleton.Skeleton;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: John
 * Date: 19/04/12
 * Time: 16:31
 * To change this template use File | Settings | File Templates.
 */
public class SkeletonData {

    public int ID;
    public int TRACKING_STATE;
    private JointData[] joints = new JointData[20];

    protected SkeletonData(Skeleton s) {
        ID = s.ID;
        TRACKING_STATE = s.getTrackingState();
        for (int i = 0; i < joints.length; i++) {
            joints[i] = new JointData(s.getJoints()[i]);
        }
    }

    public JointData getFootLeft(){return joints[Skeleton.POSITION_FOOT_LEFT];}
    public JointData getFootRight(){return joints[Skeleton.POSITION_FOOT_RIGHT];}
    public JointData getAnkleLeft(){return joints[Skeleton.POSITION_ANKLE_LEFT];}
    public JointData getAnkleRight(){return joints[Skeleton.POSITION_ANKLE_RIGHT];}
    public JointData getKneeLeft(){return joints[Skeleton.POSITION_KNEE_LEFT];}
    public JointData getKneeRight(){return joints[Skeleton.POSITION_KNEE_RIGHT];}
    public JointData getHipLeft(){return joints[Skeleton.POSITION_HIP_LEFT];}
    public JointData getHipRight(){return joints[Skeleton.POSITION_HIP_RIGHT];}
    public JointData getHipCenter(){return joints[Skeleton.POSITION_HIP_CENTER];}
    public JointData getHandLeft(){return joints[Skeleton.POSITION_HAND_LEFT];}
    public JointData getHandRight(){return joints[Skeleton.POSITION_HAND_RIGHT];}
    public JointData getWristLeft(){return joints[Skeleton.POSITION_WRIST_LEFT];}
    public JointData getWristRight(){return joints[Skeleton.POSITION_WRIST_RIGHT];}
    public JointData getElbowLeft(){return joints[Skeleton.POSITION_ELBOW_LEFT];}
    public JointData getElbowRight(){return joints[Skeleton.POSITION_ELBOW_RIGHT];}
    public JointData getShoulderLeft(){return joints[Skeleton.POSITION_SHOULDER_LEFT];}
    public JointData getShoulderRight(){return joints[Skeleton.POSITION_SHOULDER_RIGHT];}
    public JointData getShoulderCenter(){return joints[Skeleton.POSITION_SHOULDER_CENTER];}
    public JointData getHead(){return joints[Skeleton.POSITION_HEAD];}
    public JointData getSpine(){return joints[Skeleton.POSITION_SPINE];}

    public void writeToFile(File output_file) throws IOException {

        FileWriter fwt = new FileWriter(output_file);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < joints.length; i++) {
            sb.append(joints[i].toString());
            sb.append("\n");
        }
        fwt.write(sb.toString());
        fwt.flush();
        fwt.close();

    }


}
