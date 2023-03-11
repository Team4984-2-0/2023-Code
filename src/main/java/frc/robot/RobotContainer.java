// RobotBuilder Version: 5.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

// ROBOTBUILDER TYPE: RobotContainer.

package frc.robot;

import frc.robot.commands.*;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command.InterruptionBehavior;

import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.TankDrive;
import frc.robot.commands.MoveWinch;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.Trigger;

// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;

// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.subsystems.Grabber;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.cscore.VideoSink;
import edu.wpi.first.cscore.VideoSource;
import edu.wpi.first.cscore.VideoSource;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot
 * (including subsystems, commands, and button mappings) should be declared
 * here.
 */
public class RobotContainer {

  private static RobotContainer m_robotContainer = new RobotContainer();

  // The robot's subsystems
  public final PIDDriveTrain m_driveTrain = new PIDDriveTrain();
  public final Winch m_Winch = new Winch();
  public final Grabber m_Grabber = new Grabber();

  // XboxController
  private final XboxController driver = new XboxController(0);
  private final XboxController operator = new XboxController(1);

  // A chooser for autonomous commands
  SendableChooser<Command> m_chooser = new SendableChooser<>();

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  private RobotContainer() {
    // Smartdashboard Subsystems

    // SmartDashboard Buttons
    // SmartDashboard.putData("Autonomous Command", new AutonomousCommand(m_Grabber,
    // m_Winch, m_driveTrain));
    SmartDashboard.putNumber("NavX Roll Value", m_driveTrain.getNavXRoll());
    SmartDashboard.putString("Motor Mode", m_driveTrain.getMotorMode());
    // Configure the button bindings
    configureButtonBindings();

    // Configure default commands

    // Configure autonomous sendable chooser
    m_chooser.setDefaultOption("Autonomous Command", new
    AutonomousCommand(m_Grabber, m_Winch, m_driveTrain));   
    m_driveTrain.setDefaultCommand(new TankDrive(driver, m_driveTrain));
    m_Winch.setDefaultCommand(new MoveWinch(operator, m_Winch));

    SmartDashboard.putData("Auto Mode", m_chooser);
    CameraThread myCameraThread = null;

    try {
      myCameraThread = new CameraThread();
      CameraServer.getServer("test");
      // CameraServer.startAutomaticCapture();
      usbCamera1 = CameraServer.startAutomaticCapture(myCameraThread.CAMERA1);
      // usbCamera2 = CameraServer.startAutomaticCapture(myCameraThread.CAMERA2);
      // CameraServer.getServer();
      myCameraThread.server = CameraServer.getServer();
      usbCamera1.setConnectionStrategy(VideoSource.ConnectionStrategy.kKeepOpen);
      myCameraThread.setCameraConfig();

      myCameraThread.start();
      myCameraThread.setResolutionHigh();
      // myCameraThread.getCameraConfig();
    } finally {
      myCameraThread = null;
    }

  }

  public static RobotContainer getInstance() {
    return m_robotContainer;
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing
   * it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    // Create some buttons
    // CommandXboxController operator2 = new CommandXboxController(2);
    // Trigger xbutton = operator2.x();
    // xbutton.onTrue();
    // new JoystickButton(operator, Button.kX.value)
    // .whileTrue(new MoveGrabber(operator,m_Grabber));
    Trigger aBalance = new JoystickButton(driver, XboxController.Button.kA.value)
        .whileTrue(new BalanceCommand(m_driveTrain));
    if (!Constants.robottype) {
      Trigger aButton = new JoystickButton(operator, XboxController.Button.kA.value)
          .whileTrue(new OpenGrabber(m_Grabber));
      Trigger bButton = new JoystickButton(operator, XboxController.Button.kB.value)
          .whileTrue(new CloseGrabber(m_Grabber));
    }

    Trigger ToggleMotorMode = new JoystickButton(driver, XboxController.Button.kY.value)
        .onTrue(new ToggleMotorMode(m_driveTrain));
    Trigger SetCoastMode = new JoystickButton(driver, XboxController.Button.kX.value)
        .onTrue(new SetCoastMode(m_driveTrain));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // The selected command will be run in autonomous
    return m_chooser.getSelected();
  }

  public class configureButtonBindings {
  }

  public static UsbCamera usbCamera1 = null;

  // public static UsbCamera usbCamera2 = null;
  public class CameraThread extends Thread {
    final int CAMERA1 = 0;
    // final int CAMERA2 = 1;
    private final int currentCamera = CAMERA1; // UNCOMMENT WHEN RUNNING THE PROGRAM THRU ROBORIO!!!!

    VideoSink server;

    public void run() {
      System.out.println("CameraThread running");

    }

    public void setResolutionLow() {
      System.out.println("CameraThread rsetResolutionLow running");
      usbCamera1.setResolution(150, 150);
      usbCamera1.setFPS(Constants.CAMERA1_FPS);

    }

    public void setResolutionHigh() {
      System.out.println("CameraThread rsetResolutionHigh running");
      usbCamera1.setResolution(150, 150);
      usbCamera1.setFPS(Constants.CAMERA1_FPS);
    }

    public void setCameraSource() {
      System.out.println("CameraThread setCameraSource running");
      server.setSource(usbCamera1);
      SmartDashboard.putString("My Key", "Hello");
    }

    public void getCameraConfig() {
      System.out.println("CameraThread getPrintCameraConfig running");
      String cameraConfig;
      // issue when camera is not plugged in at start
      cameraConfig = usbCamera1.getConfigJson();
      if (cameraConfig.isEmpty() == false) {
        // System.out.println(cameraConfig.toString()); //print to console
      }
    }

    public void setCameraConfig() {
      System.out.println("CameraThread setPrintCameraConfig running");

      usbCamera1.setFPS(Constants.CAMERA1_FPS);
      usbCamera1.setBrightness(Constants.CAMERA1_BRIGHTNESS);
      usbCamera1.setExposureAuto();
    }
  }
}
