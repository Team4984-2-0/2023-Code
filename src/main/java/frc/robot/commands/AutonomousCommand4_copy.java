 
package frc.robot.commands;
import java.util.concurrent.TimeUnit;

import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Grabber;
import frc.robot.subsystems.Winch;
import frc.robot.subsystems.PIDDriveTrain;
import java.util.concurrent.TimeUnit;



 
public class AutonomousCommand4_copy extends CommandBase {

    private Grabber m_Grabber;
    private Winch m_Winch;
    private PIDDriveTrain m_DriveTrain;
    private int sleepCounter;
    private int sleepCounterMax;
    private double NAVXLevel;
    private double count = 0;
    private boolean end = false;


    public AutonomousCommand4_copy(Grabber Grabber_sub, Winch Winch_sub, PIDDriveTrain DriveTrain_sub) {
        m_Grabber = Grabber_sub;
        m_Winch = Winch_sub;
        m_DriveTrain = DriveTrain_sub;
        sleepCounter = 0;
        sleepCounterMax = 80;
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        NAVXLevel = m_DriveTrain.getNavXRoll();
        m_DriveTrain.setBrakeMode();
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
        if (RobotState.isAutonomous()) {
            
            while ((Math.abs(m_DriveTrain.getNavXRoll() - NAVXLevel) < 1.5) && count == 0){
                sleepCounter = sleepCounter + 1;

                if (sleepCounter == 1) {
                    m_Winch.moveservo180();
                    if(RobotState.isTeleop()) {
                        break;
                    }
                }
                else if (sleepCounter == 45){
                    m_Grabber.open();
                    if(RobotState.isTeleop()) {
                        break;
                    }
                }
                else if (sleepCounter == 76){
                    m_Grabber.stop();
                    if(RobotState.isTeleop()) {
                        break;
                    }
                }
                else if (sleepCounter > 76){
                    m_DriveTrain.drive(-0.45,0.45);
                    if(RobotState.isTeleop()) {
                        break;
                    }
                }
            }

            count ++;

            while ((Math.abs(m_DriveTrain.getNavXRoll() - NAVXLevel) > 1.5) && count != 0){
                
                if((m_DriveTrain.getNavXRoll()) < NAVXLevel){
                    m_DriveTrain.drive(-0.45,0.45);
                    System.out.println("Going Down");
                }
                else if((m_DriveTrain.getNavXRoll()) > NAVXLevel){
                    m_DriveTrain.drive(0.45,-0.45);
                    System.out.println("Going Up");
                }
                if(RobotState.isTeleop()) {
                    break;
                }
            }

            if ((Math.abs(m_DriveTrain.getNavXRoll() - NAVXLevel) < 1.5) && count != 0){
                m_DriveTrain.drive(0, 0);
                m_DriveTrain.setCoastMode();
                end = true;
            }
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        if (end == true || RobotState.isTeleop())
        {
            if(RobotState.isTeleop()) {
                System.out.println("Teleop has started fiinishing Autonomous");
            }
            System.out.println("Autonomous Finished");
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
