// RobotBuilder Version: 5.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

// ROBOTBUILDER TYPE: Command.

package frc.robot.commands;

import com.fasterxml.jackson.annotation.JacksonInject.Value;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.Robot;

import frc.robot.subsystems.Grabber;

/**
 *
 */
public class CloseGrabber extends CommandBase {

    private final Grabber m_Grabber;
    private boolean ButtonX;
    private boolean ButtonY;
    private double motorspeed = 1;
    private double motoroff = 0;
    private double MoveValue;
    private XboxController xbox;

    public CloseGrabber(Grabber subsystem) {

        m_Grabber = subsystem;
        addRequirements(m_Grabber);

    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override

    public void execute() {
        m_Grabber.close();

    }

    // while xbox button x is pressed
    // call open
    // outside of while call grabber stop

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        m_Grabber.stop();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public boolean runsWhenDisabled() {
        return false;
    }

}
