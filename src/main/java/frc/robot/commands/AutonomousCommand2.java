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
import java.util.concurrent.TimeUnit;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Grabber;
import frc.robot.subsystems.Winch;
import frc.robot.subsystems.PIDDriveTrain;
import java.util.concurrent.TimeUnit;



 
public class AutonomousCommand2 extends CommandBase {

    private Grabber m_Grabber;
    private Winch m_Winch;
    private PIDDriveTrain m_DriveTrain;
    private Servo m_Dropper;
    private int sleepCounter;
/*     private double conversion = 18.85; //18.85 inches per revolution
    private double toChargeStation = 5; //feet to charge station
    private double overChargeStation = 5; //feet over charge station
    private double revolutionsToTravel;
    private double initialPosition; */
    private double initialHeading;
    private double initialYaw;
   // private Command grabber_close = new CloseGrabber(m_Grabber);


    public AutonomousCommand2(Grabber Grabber_sub, Winch Winch_sub, PIDDriveTrain DriveTrain_sub, Servo Servo_sub) {
        m_Grabber = Grabber_sub;
        m_Winch = Winch_sub;
        m_DriveTrain = DriveTrain_sub;
        m_Dropper = Servo_sub;
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
        //System.out.println("Autonomous 1");
        //m_DriveTrain.drive(-0.5, -0.5);

        //System.out.println(m_DriveTrain.leftBackEncoder.getPosition());
        //System.out.println("Autonomous 2");
       
        //System.out.println("Autonomous 3");
        //m_DriveTrain.drive(0, 0);
        //System.out.println("Autonomous 4 counter = " + sleepCounter);
        //sleepCounter ++;

        // instantiate grabber to close
        // turn servo
        // open grabber
        // move backwards (facing backwards) - check navx
        // reach other side of charge station - mobility
        // turn 180 degrees
        // move backwards (facing backwards) - check navx
        // balance command

        m_Grabber.close();
        // grabber holds onto cube

        m_Dropper.setAngle(180);
        // servo turns to drop grabber

        m_Grabber.open();
        // grabber lets go (scores) cube

        for (int i = 0; i<5; i++) {
            m_DriveTrain.drive(-0.7, -0.7);

            if (m_DriveTrain.getNavXRoll() > m_DriveTrain.getLevel()) {
                break;

            }
        }
        // travels backwards until navx sees robot going down other side of charge station

        for (int i = 0; i<5; i++) {
            m_DriveTrain.drive(-0.5, -0.5);

            if (m_DriveTrain.getNavXRoll() == m_DriveTrain.getLevel() || (m_DriveTrain.getNavXRoll() >= (m_DriveTrain.getLevel() - 1) && m_DriveTrain.getNavXRoll() <= (m_DriveTrain.getLevel() + 1))) {
                break;

            }
        }
        // travels until navx is level (ie robot traversed charge station)

        initialHeading = m_DriveTrain.getHeading();

        for (int i = 0; i<5; i++) {
            m_DriveTrain.drive(0.5, -0.5);

            if (m_DriveTrain.getHeading() > (initialHeading + 180)) {
                break;

            }
        } 
        // turns robot by 180 degrees

        // alternate turn using yaw instead of absolute compass
        /*
        initialYaw = m_DriveTrain.getYaw();

        for (int i = 0; i<5; i++) {
            m_DriveTrain.drive(0.5, -0.5);

            if (m_DriveTrain.getYaw() > (initialYaw + 180)) {
                break;

            }
        } 
         */
        
        for (int i = 0; i<5; i++) {
            m_DriveTrain.drive(-0.7, -0.7);

            if (m_DriveTrain.getNavXRoll() < m_DriveTrain.getLevel()) {
                try {
                    Thread.sleep(500); // gives robot extra time to get fully get up charge station before switching to auto balance
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;

            }
        }
        // travels backwards until navx sees robot going up charge station

        for (int i = 0; i<5; i++) {

            if (m_DriveTrain.getNavXRoll() == m_DriveTrain.getLevel() || (m_DriveTrain.getNavXRoll() >= (m_DriveTrain.getLevel() - 2) && m_DriveTrain.getNavXRoll() <= (m_DriveTrain.getLevel() + 2))) {
                break;

            }
            else if (m_DriveTrain.getNavXRoll() < m_DriveTrain.getLevel()){
                m_DriveTrain.drive(-0.2, -0.2);

            }
            else if (m_DriveTrain.getNavXRoll() > m_DriveTrain.getLevel()){
                m_DriveTrain.drive(0.2, 0.2);

            }
        }
        // auto balances robot +/- 2 degrees
        // drive train direction will likely need to be adjusted
        // low speed is used for final adjustment

    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
         
/*         if (sleepCounter > Constants.sleepCounterConstant)
        {
            m_DriveTrain.drive(0, 0);
            return true;
        }
        else 
            return false; */

        return true;
    }

    @Override
    public boolean runsWhenDisabled() {

        return false;


    }
}
