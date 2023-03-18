
package frc.robot.commands;
import java.util.concurrent.TimeUnit;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Grabber;
import frc.robot.subsystems.Winch;
import frc.robot.subsystems.PIDDriveTrain;
import java.util.concurrent.TimeUnit;



 
public class AutonomousCommand3 extends CommandBase {

    private Grabber m_Grabber;
    private Winch m_Winch;
    private PIDDriveTrain m_DriveTrain;
    private int sleepCounter;


    public AutonomousCommand3(Grabber Grabber_sub, Winch Winch_sub, PIDDriveTrain DriveTrain_sub) {
        m_Grabber = Grabber_sub;
        m_Winch = Winch_sub;
        m_DriveTrain = DriveTrain_sub;
        sleepCounter = 0;

    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
    }

    // Wait for a certain amount of time, easier to use this rather than repeating the try/catch
    private void wait(int timeInMS){
        try {
            Thread.sleep(timeInMS);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        //m_Winch.moveservo();
        // needs wait
        //m_Grabber.open();
        // needs wait
        while(Constants.RevPerFoot*(-17) < m_DriveTrain.rightBackEncoder.getPosition()) {
            //System.out.println(m_DriveTrain.rightBackEncoder.getPosition());
            m_DriveTrain.drive(-0.65,0.65);
            //System.out.println(m_DriveTrain.rightBackEncoder.getPosition());
            //System.out.println("testing");
        // add loop to check if it has gone distance
         //going backwards at 65% during auto 
        }

        // stop
        m_DriveTrain.drive(0,0);

        
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        if (sleepCounter > Constants.sleepCounterConstant)
        {
            m_DriveTrain.drive(0, 0);
            sleepCounter = 0;
            return true;
        }
        else 
            return false;


    }

    @Override
    public boolean runsWhenDisabled() {

        return false;


    }
}
