// RobotBuilder Version: 5.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package frc.robot;
/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean
 * constants. This class should not be used for any other purpose. All constants
 * should be
 * declared globally (i.e. public static). Do not put anything functional in
 * this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the
 * constants are needed, to reduce verbosity.
 */
public class Constants {
    public static final int CANBackLeft = 4;
    public static final int CANFrontLeft = 3;
    public static final int CANBackRight = 2;
    public static final int CANFrontRight = 1;
    public static final int CANWinch = 5;
    public static final int CANGrabber = 6;
    public static final int CAMERA1_FPS = 30;
    public static final int CAMERA1_BRIGHTNESS = 50;
    public static boolean robottype = false; 
    public static int sleepCounterConstant = 77;
    public static double encoderfullrotation = 13.2143182; 
    public static double wheelcircumference = (6*3.14)/12;
    public static double RevPerFoot = encoderfullrotation/wheelcircumference;
}


