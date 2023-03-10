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
// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.DigitalInput;

public class Winch extends SubsystemBase {

    private CANSparkMax winchMotor;
    private DigitalInput winchlimitSwitch;

    public Winch() {
        // Create motor
        winchlimitSwitch = new DigitalInput(0);
        winchMotor = new CANSparkMax(Constants.CANWinch, MotorType.kBrushless);

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

    public void move(double Value) {
        if (winchlimitSwitch.get() && Value < 0) {
            System.out.println("1");
            winchMotor.set(Value);
        } else if (winchlimitSwitch.get() && Value > 0) {
            System.out.println("2");
            winchMotor.set(Value);
        } else if (winchlimitSwitch.get() == false && Value > 0) {
            System.out.println("3");
            winchMotor.set(Value);
        } else {
            winchMotor.set(0.01);
            System.out.println("else");
        }

        // moves the motor
        System.out.println("Limit Switch: " + winchlimitSwitch.get());

        // Robot.printYellow(Double.toString(Value));

    }

}
