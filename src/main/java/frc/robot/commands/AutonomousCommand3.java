package frc.robot.commands;
import java.util.concurrent.TimeUnit;

import edu.wpi.first.wpilibj.RobotState;
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
    private double NAVXLevel;
    private int sleepCounterMax;
    private int NAVXwait;


    public AutonomousCommand3(Grabber Grabber_sub, Winch Winch_sub, PIDDriveTrain DriveTrain_sub) {
        m_Grabber = Grabber_sub;
        m_Winch = Winch_sub;
        m_DriveTrain = DriveTrain_sub;
        sleepCounter = 0;
        sleepCounterMax = 87;

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
        if (RobotState.isAutonomous()) {
            sleepCounter = sleepCounter + 1;
            switch(sleepCounter) {
                case 1:
                    System.out.println("STARTING PHASE 1: Initial Release");
                    m_DriveTrain.setBrakeMode();
                    //m_Winch.moveservo180();
                    NAVXLevel = m_DriveTrain.getNavXRoll();
                    System.out.println("PHASE 1 Finished");
                    break;
                case 45:
                    System.out.println("STARTING PHASE 2: Open Grabber");
                    m_Grabber.open();
                    System.out.println("PHASE 2 Finished");
                    break;
                case 76:
                    m_Grabber.stop();
                    System.out.println("STARTING PHASE 3: Drive backwards 14ft");
                    while(Constants.RevPerFoot*(-12) < m_DriveTrain.rightBackEncoder.getPosition()) {
                        m_DriveTrain.drive(-0.55,0.55);
                        if(RobotState.isTeleop()) {
                            m_Winch.moveservo180();
                            break;
                        }
                    }
                    System.out.println("PHASE 3 Finished");
                    break;
                case 77: 
                    System.out.println("STARTING PHASE 4: Drive forward 8ft");
                    while(Constants.RevPerFoot*(-8) > m_DriveTrain.rightBackEncoder.getPosition()) {
                        m_DriveTrain.drive(0.55,-0.55);
                        if(RobotState.isTeleop()) {
                            m_Winch.moveservo180();
                            break;
                        }
                    }
                    m_DriveTrain.drive(0,0);
                    System.out.println("PHASE 4 Finished");
                    break;
                case 85:
                    System.out.println("STARTING PHASE 5: Balance");
                    System.out.println("NAVX: " + NAVXLevel);
                    NAVXwait=0;
                    while((Math.abs(m_DriveTrain.getNavXRoll() - NAVXLevel) >  1.5 )) {
                        NAVXwait++;
                        if(NAVXwait == 30){
                            if((m_DriveTrain.getNavXRoll()) < NAVXLevel){
                                m_DriveTrain.drive(-0.10,0.10);
                            }
                            else if((m_DriveTrain.getNavXRoll()) > NAVXLevel){
                                m_DriveTrain.drive(0.10,-0.10);
                            }
                            NAVXwait = 0;
                        }
                        if(RobotState.isTeleop()) {
                            m_Winch.moveservo180();
                            break;
                        }
                    }
                    m_DriveTrain.drive(0, 0);
                    System.out.println("PHASE 5 Finished");
                    break;
                case 86:
                    System.out.println("STARTING PHASE 6: Cleanup");
                    m_DriveTrain.drive(0, 0);
                    m_Winch.moveservo180();
                    m_DriveTrain.setCoastMode();
                    System.out.println("PHASE 6 Finished");
                    break;
            }   
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
        if (sleepCounter > sleepCounterMax | RobotState.isTeleop())
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