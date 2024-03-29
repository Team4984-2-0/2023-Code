// RobotBuilder Version: 5.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

// ROBOTBUILDER TYPE: PIDSubsystem.

package frc.robot.subsystems;

import frc.robot.commands.*;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.math.MathUtil;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxRelativeEncoder;

public class PIDDriveTrain extends PIDSubsystem {

    private static AHRS m_DriveTrainGyro;
    private CANSparkMax leftBackMotor;
    private CANSparkMax leftFrontMotor;
    private MotorControllerGroup leftMotors;
    private CANSparkMax rightBackMotor;
    private CANSparkMax rightFrontMotor;
    private MotorControllerGroup rightMotors;
    private DifferentialDrive differentialDrive1;
    private int loopcounter;
    private RelativeEncoder leftBackEncoder;
    private RelativeEncoder leftFrontEncoder;
    public RelativeEncoder rightBackEncoder;
    private RelativeEncoder rightFrontEncoder;
    private String MotorMode;
    private double groundincline;
    private PowerDistribution PDP;
    // P I D Variables
    private static final double kP = 1.0;
    private static final double kI = 0.0;
    private static final double kD = 0.0;
    private static final double kF = 1.0;
    private int timecount;

    private static final String robottype = "";

    // Initialize your subsystem here
    public PIDDriveTrain() {

        super(new PIDController(kP, kI, kD));
        getController().setTolerance(0.2);
        MotorMode = "Coast";
        try {
            m_DriveTrainGyro = new AHRS(SPI.Port.kMXP);
        } catch (RuntimeException ex) {
            // Driver Station.reportError("Error instantiating navX MXP: " +
            // ex.getMessage(), true);
        }
        if (m_DriveTrainGyro != null)
            ;
        {
            m_DriveTrainGyro.reset();
        }

        if (Constants.robottype) {
            // Left Motors
            leftBackMotor = new CANSparkMax(Constants.CANBackLeft, MotorType.kBrushed);
            leftFrontMotor = new CANSparkMax(Constants.CANFrontLeft, MotorType.kBrushed);
            leftMotors = new MotorControllerGroup(leftBackMotor, leftFrontMotor);
            addChild("Motor Controller Group 1", leftMotors);
            // Right Motors
            rightBackMotor = new CANSparkMax(Constants.CANBackRight, MotorType.kBrushed);
            rightFrontMotor = new CANSparkMax(Constants.CANFrontRight, MotorType.kBrushed);
            rightMotors = new MotorControllerGroup(rightBackMotor, rightFrontMotor);
            addChild("Motor Controller Group 2", rightMotors);

            leftFrontMotor.setOpenLoopRampRate(0);
            leftBackMotor.setOpenLoopRampRate(0);
            rightFrontMotor.setOpenLoopRampRate(0);
            rightBackMotor.setOpenLoopRampRate(0);
        
        } else {
            // Left Motors
            leftBackMotor = new CANSparkMax(Constants.CANBackLeft, MotorType.kBrushless);
            leftFrontMotor = new CANSparkMax(Constants.CANFrontLeft, MotorType.kBrushless);
            leftMotors = new MotorControllerGroup(leftBackMotor, leftFrontMotor);
            addChild("Motor Controller Group 1", leftMotors);
            // Right Motors
            rightBackMotor = new CANSparkMax(Constants.CANBackRight, MotorType.kBrushless);
            rightFrontMotor = new CANSparkMax(Constants.CANFrontRight, MotorType.kBrushless);
            rightMotors = new MotorControllerGroup(rightBackMotor, rightFrontMotor);
            addChild("Motor Controller Group 2", rightMotors);
            setCoastMode();
            leftFrontMotor.setOpenLoopRampRate(0.3);
            leftBackMotor.setOpenLoopRampRate(0.3);
            rightFrontMotor.setOpenLoopRampRate(0.3);
            rightBackMotor.setOpenLoopRampRate(0.3);

            leftBackEncoder = leftBackMotor.getEncoder();
            leftFrontEncoder = leftFrontMotor.getEncoder();
            rightBackEncoder = rightBackMotor.getEncoder();
            rightFrontEncoder = rightFrontMotor.getEncoder();


        }

        differentialDrive1 = new DifferentialDrive(leftMotors, rightMotors);
        addChild("Differential Drive 1", differentialDrive1);
        differentialDrive1.setSafetyEnabled(true);
        differentialDrive1.setExpiration(0.1);
        differentialDrive1.setMaxOutput(1.0);
        waitfornavx();
        groundincline = m_DriveTrainGyro.getRoll();


        // Shuffle board
        ShuffleboardTab showdiff = Shuffleboard.getTab("PID Drive Train");
        showdiff.add("Drive Train",differentialDrive1);

        PDP = new PowerDistribution(0, ModuleType.kCTRE);
        ShuffleboardTab showpower = Shuffleboard.getTab("Power");
        showpower.add("pdp",PDP);
        timecount = 0;


    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        super.periodic();
        if(timecount == 30) {
            SmartDashboard.putNumber("NavX Roll Value",getNavXRoll());
            SmartDashboard.putString("Motor Mode",getMotorMode());
            timecount = 0;
        }
        timecount++;
        // SmartDashboard.putNumber("leftBackEncoder",leftBackEncoder.getPosition());
        // SmartDashboard.putNumber("leftFrontEncoder",leftFrontEncoder.getPosition());
        // SmartDashboard.putNumber("rightBackEncoder",rightBackEncoder.getPosition());
        // SmartDashboard.putNumber("rightFrontEncoder",rightFrontEncoder.getPosition());
    }

    @Override
    public double getMeasurement() {
        
        return m_DriveTrainGyro.getPitch();
    }

    @Override
    public void useOutput(double output, double setpoint) {
        if ((setpoint - output) > 2.0) {
            leftMotors.set(-0.3);
            rightMotors.set(0.3);
        } else if ((setpoint - output) < -2.0) {
            leftMotors.set(0.3);
            rightMotors.set(-0.3);
        } else {
            leftMotors.set(0);
            rightMotors.set(0);
        }

    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run when in simulation

    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void drive(double leftDrive, double rightDrive) {
        if (Math.abs(rightDrive) < 0.08)
            rightDrive = 0.0;
        if (Math.abs(leftDrive) < 0.08)
            leftDrive = 0.0;

        differentialDrive1.tankDrive(leftDrive, -rightDrive);
        //System.out.println("LeftDrive: " + leftDrive + "RightDrive: " + rightDrive);
        loopcounter++;
        if (loopcounter > 3) {
            //System.out.println("DriveTrain pitch = " + m_DriveTrainGyro.getPitch());
            loopcounter = 0;

            // Robot.printYellow(leftDrive + "," + rightDrive);
            //System.out.println(leftDrive + "," + rightDrive);
        }

    }

    public void setCoastMode() {
        leftFrontMotor.setIdleMode(IdleMode.kCoast);
        leftBackMotor.setIdleMode(IdleMode.kCoast);
        rightFrontMotor.setIdleMode(IdleMode.kCoast);
        rightBackMotor.setIdleMode(IdleMode.kCoast);
        MotorMode = "Coast";
    }

    public void setBrakeMode() {
        leftFrontMotor.setIdleMode(IdleMode.kBrake);
        leftBackMotor.setIdleMode(IdleMode.kBrake);
        rightFrontMotor.setIdleMode(IdleMode.kBrake);
        rightBackMotor.setIdleMode(IdleMode.kBrake);
        MotorMode = "Brake";
    }

    public void ToggleMotorMode() {
        setBrakeMode();

        /*
         * if (leftFrontMotor.getIdleMode() == IdleMode.kBrake &&
         * rightFrontMotor.getIdleMode() == IdleMode.kBrake)
         * {
         * setCoastMode();
         * MotorMode = IdleMode.kCoast;
         * }
         * else if (leftFrontMotor.getIdleMode() == IdleMode.kCoast &&
         * rightFrontMotor.getIdleMode() == IdleMode.kCoast) {
         * setBrakeMode();
         * MotorMode = IdleMode.kBrake;
         * } else {
         * System.out.println("mismatched motor modes setting all motors to coast mode"
         * );
         * setCoastMode();
         * MotorMode = IdleMode.kCoast;
         * }
         */
    }

    public double getNavXRoll() {
        return m_DriveTrainGyro.getRoll();
    }

    public String getMotorMode() {

        if (leftFrontMotor.getIdleMode() == IdleMode.kBrake && rightFrontMotor.getIdleMode() == IdleMode.kBrake) {
            return "Brake";

        } else if (leftFrontMotor.getIdleMode() == IdleMode.kCoast
                && rightFrontMotor.getIdleMode() == IdleMode.kCoast) {
            return "Coast";

        } else {
            return "Error";

        }
    }
    private void waitfornavx(){
        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    
    }
}
