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

import frc.robot.subsystems.Winch;

public class MoveWinch extends CommandBase {

    
    private final Winch m_Winch;
    private double MoveValue;
    private XboxController xbox;



    public MoveWinch(XboxController Controller, Winch subsystem) {


        xbox = Controller;

         m_Winch = subsystem;
        addRequirements(m_Winch);


    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {   
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        MoveValue = xbox.getLeftY();
        
        m_Winch.move(MoveValue);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        m_Winch.move(0);
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
