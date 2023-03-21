// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Grabber;
import frc.robot.subsystems.PIDDriveTrain;
import frc.robot.subsystems.Winch;

public class AutonomousCommand3_Sketch extends CommandBase {
  private Grabber m_Grabber;
    private Winch m_Winch;
    private PIDDriveTrain m_DriveTrain;
    private int sleepCounter;


    public AutonomousCommand3_Sketch(Grabber Grabber_sub, Winch Winch_sub, PIDDriveTrain DriveTrain_sub) {
        m_Grabber = Grabber_sub;
        m_Winch = Winch_sub;
        m_DriveTrain = DriveTrain_sub;
        sleepCounter = 0;

    }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    sleepCounter = sleepCounter + 1;
    // From auto 2
    if(sleepCounter == 1){
      m_DriveTrain.setBrakeMode();
      System.out.println("STARTING PHASE 1");
      m_Winch.moveservo();
  }
  else if(sleepCounter == 30){
      System.out.println("STARTING PHASE 2");
      m_Grabber.open();
  }
  else if(sleepCounter == 31){
      m_Grabber.stop();
      System.out.println("STARTING PHASE 3");
      while(Constants.RevPerFoot*(-17) < m_DriveTrain.rightBackEncoder.getPosition()) {
          m_DriveTrain.drive(-0.65,0.65);
      }
      m_DriveTrain.drive(0, 0);
      m_DriveTrain.setCoastMode();
      System.out.println("STARTING PHASE 3 END");
    }

    // And now go back to balance
    else if(sleepCounter == 32){
        while(Constants.RevPerFoot*(-8) > m_DriveTrain.rightBackEncoder.getPosition()) {
          m_DriveTrain.drive(0.65,-0.65);
      }
      m_DriveTrain.drive(0, 0);
      m_DriveTrain.setCoastMode();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(sleepCounter > Constants.sleepCounterConstant){
      // It's done
      sleepCounter = 0;
      return true;
    }
    return false;
  }
}
