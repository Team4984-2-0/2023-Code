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

import edu.wpi.first.wpilibj.SPI;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
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
    private RelativeEncoder rightBackEncoder;
    private RelativeEncoder rightFrontEncoder;
    private IdleMode MotorMode;

    // P I D Variables
    private static final double kP = 1.0;
    private static final double kI = 0.0;
    private static final double kD = 0.0;
    private static final double kF = 1.0;

    private static final String robottype = "";

    // Initialize your subsystem here
    public PIDDriveTrain() {

        super(new PIDController(kP, kI, kD));
        getController().setTolerance(0.2);

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

            //leftBackEncoder = leftBackMotor.getEncoder();// 4096 wil need
                                                                                                       // to
            // be changed
            //leftFrontEncoder = leftFrontMotor.getEncoder();
            //rightBackEncoder = rightBackMotor.getEncoder();
            //rightFrontEncoder = rightFrontMotor.getEncoder();
        }

        differentialDrive1 = new DifferentialDrive(leftMotors, rightMotors);
        addChild("Differential Drive 1", differentialDrive1);
        differentialDrive1.setSafetyEnabled(true);
        differentialDrive1.setExpiration(0.1);
        differentialDrive1.setMaxOutput(1.0);

        // Use these to get going:
        // setSetpoint() - Sets where the PID controller should move the system
        // to
        // enable() - Enables the PID controller.

    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        super.periodic();

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

        // Robot.printYellow(leftDrive + "," + rightDrive);
        // System.out.println(leftDrive + "," + rightDrive);
        differentialDrive1.tankDrive(leftDrive, -rightDrive);

        loopcounter++;
        if (loopcounter > 3) {
            System.out.println("DriveTrain pitch = " + m_DriveTrainGyro.getPitch());
            loopcounter = 0;
        }

    }

    public void setCoastMode() {
        leftBackMotor.setIdleMode(IdleMode.kCoast);
        leftBackMotor.setIdleMode(IdleMode.kCoast);
        rightFrontMotor.setIdleMode(IdleMode.kCoast);
        rightBackMotor.setIdleMode(IdleMode.kCoast);
    }

    public void setBrakeMode() {
        leftFrontMotor.setIdleMode(IdleMode.kBrake);
        leftBackMotor.setIdleMode(IdleMode.kBrake);
        rightFrontMotor.setIdleMode(IdleMode.kBrake);
        rightBackMotor.setIdleMode(IdleMode.kBrake);
    }

    public void ToggleMotorMode() {

        if (leftFrontMotor.getIdleMode() == IdleMode.kBrake && rightFrontMotor.getIdleMode() == IdleMode.kBrake)
         {
            setCoastMode();
            MotorMode = IdleMode.kCoast;
        }
        else if (leftFrontMotor.getIdleMode() == IdleMode.kCoast && rightFrontMotor.getIdleMode() == IdleMode.kCoast) {
            setBrakeMode();
            MotorMode = IdleMode.kBrake;
        } else {
            System.out.println("mismatched motor modes setting all motors to coast mode");
            setCoastMode();
            MotorMode = IdleMode.kCoast;
        }
    }
    public double getNavXRoll(){
        return m_DriveTrainGyro.getRoll();
    }
    
    public String getMotorMode() {

        if (leftFrontMotor.getIdleMode() == IdleMode.kBrake && rightFrontMotor.getIdleMode() == IdleMode.kBrake)
         {
            return "Brake Mode Enabled";

        }
        else if (leftFrontMotor.getIdleMode() == IdleMode.kCoast && rightFrontMotor.getIdleMode() == IdleMode.kCoast) {
            return "Coast Mode Enabled";

        } else {
            return "mismatched motor modes";


        }
    }
}
