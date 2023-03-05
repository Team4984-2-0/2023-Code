// RobotBuilder Version: 5.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

// ROBOTBUILDER TYPE: Subsystem.

package frc.robot.subsystems;


import frc.robot.Robot;
import frc.robot.commands.*;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import com.fasterxml.jackson.annotation.JacksonInject.Value;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;

 
public class Grabber extends SubsystemBase {

private CANSparkMax GrabMotor;
private DigitalInput grabberSwitch;


  
    public Grabber() {
        // Create motor

GrabMotor = new CANSparkMax(6,MotorType.kBrushless);
GrabMotor.setIdleMode(IdleMode.kBrake);
grabberSwitch = new DigitalInput(1);
 
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run

    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run when in simulation

    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.





    
    public void open ( )
    {
        // moves the motor
        //Robot.printYellow("Grabber open");

        if(grabberSwitch.get()) {
            GrabMotor.set(-0.05);
        }
        else {
            GrabMotor.set(0);
        }

    }
    public void close()
    {
        // moves the motor
        //Robot.printYellow("closing grabber");
        GrabMotor.set(0.15);
    }
    public void stop( )
    {
        // moves the motor
        //Robot.printYellow("stopping grabber");
        GrabMotor.set(0);
    }
    
}

