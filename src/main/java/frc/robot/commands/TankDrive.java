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

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.Robot;
import frc.robot.subsystems.PIDDriveTrain;

public class TankDrive extends CommandBase {

    private final PIDDriveTrain m_driveTrain;
    private double left;
    private double right;
    private XboxController xbox;

    public TankDrive(XboxController controller, PIDDriveTrain subsystem) {

        xbox = controller;

        m_driveTrain = subsystem;
        addRequirements(m_driveTrain);

    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        Robot.printYellow("~     We're starting!     ~");
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        // m_driveTrain.drive(m_LeftStick.getAsDouble(), m_RightStick.getAsDouble());
        left = xbox.getLeftY();
        right = xbox.getRightY();
        m_driveTrain.drive(left, right);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        m_driveTrain.drive(0, 0);
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
