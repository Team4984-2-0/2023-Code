package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Winch;

public class WinchStop extends CommandBase{
    
    private final Winch m_Winch;

    public WinchStop(Winch subsystem){
        m_Winch = subsystem;
        addRequirements(m_Winch);
    }

    // Called every time the scheduler runs while the command is scheduled
    @Override public void execute(){
        m_Winch.move(0);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        m_Winch.move(0);
    }
}
