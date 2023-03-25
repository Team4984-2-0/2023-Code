 
package frc.robot.commands;
import java.util.concurrent.TimeUnit;

import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.Timer;
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
    private Timer timer = new Timer();
    private boolean end = false;


    public AutonomousCommand4_copy(Grabber Grabber_sub, Winch Winch_sub, PIDDriveTrain DriveTrain_sub) {
        m_Grabber = Grabber_sub;
        m_Winch = Winch_sub;
        m_DriveTrain = DriveTrain_sub;
        //sleepCounter = 0;
        //sleepCounterMax = 80;
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        timer.start(); //start timer ~ auto period
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
        int count = 0; //variable determines which while loop is execited
        double NAVXLevel = m_DriveTrain.getNavXRoll();

        if (RobotState.isAutonomous()) {
            
            //while loop controls servo open, grabber, and reverse until navX sees not flat
            while ((Math.abs(m_DriveTrain.getNavXRoll() - NAVXLevel) < 1.5) && count == 0){

                if (timer.hasElapsed(1)) {
                    //releases grabber
                    m_Winch.moveservo180();
                    if(RobotState.isTeleop() || timer.get() >= 15) {
                        break;
                    }
                }
                else if (timer.hasElapsed(1.5)){
                    //spits out game piece
                    m_Grabber.open();
                    if(RobotState.isTeleop() || timer.get() >= 15) {
                        break;
                    }
                }
                else if (timer.hasElapsed(2.5)){
                    //stops grabber wheels
                    m_Grabber.stop();
                    if(RobotState.isTeleop() || timer.get() >= 15) {
                        break;
                    }
                }
                else if (timer.get() > 2.5){
                    //reverses robot
                    m_DriveTrain.drive(-0.45,0.45);
                    if(RobotState.isTeleop() || timer.get() >= 15) {
                        break;
                    }
                }
            }

            count ++;

            //while loop controls balance once robot has reversed onto charge station
            while ((Math.abs(m_DriveTrain.getNavXRoll() - NAVXLevel) > 1.5) && count != 0){
                
                if(m_DriveTrain.getNavXRoll() < NAVXLevel){
                    //reverses robot if robot is pitching down relative to front of robot
                    m_DriveTrain.drive(-0.45,0.45);
                    //System.out.println("Going Down");
                }
                else if(m_DriveTrain.getNavXRoll() > NAVXLevel){
                    //moves robot forward if robot is pitching up relative to front of robot
                    m_DriveTrain.drive(0.45,-0.45);
                    //System.out.println("Going Up");
                }
                if(RobotState.isTeleop() || timer.get() >= 15) {
                    break;
                }
            }

            //sets end settings for robot and ends auto command
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
        timer.stop();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        if (end == true || RobotState.isTeleop() || timer.get() >= 15)
        {
            if(RobotState.isTeleop()) {
                System.out.println("Teleop has started fiinishing Autonomous");
            }
            System.out.println("Autonomous Finished");
            //sleepCounter = 0;
            timer.stop();
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
