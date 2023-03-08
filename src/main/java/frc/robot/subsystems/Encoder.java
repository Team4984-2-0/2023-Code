package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxRelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxAlternateEncoder.Type;

import frc.robot.Constants;


public class Encoder {
    // What CONSTANTS do we need?
    private double desired = 0.0;

    // Motors
    private CANSparkMax fLMotor;
    private CANSparkMax rLMotor;
    private CANSparkMax fRMotor;
    private CANSparkMax rRMotor;
    //
    private RelativeEncoder frontLeft;
    private RelativeEncoder rearLeft;
    //
    private RelativeEncoder frontRight;
    private RelativeEncoder rearRight;

    public Encoder(){
        fLMotor = new CANSparkMax(Constants.CANFrontLeft, MotorType.kBrushless);
        rLMotor = new CANSparkMax(Constants.CANFrontLeft, MotorType.kBrushless);
        fRMotor = new CANSparkMax(Constants.CANFrontLeft, MotorType.kBrushless);
        rRMotor = new CANSparkMax(Constants.CANFrontLeft, MotorType.kBrushless);

        //frontLeft = CANSparkMax.getEncoder(SparkMaxRelativeEncoder.Type.kQuadrature,4096);
        frontLeft = fLMotor.getEncoder(SparkMaxRelativeEncoder.Type.kQuadrature,Constants.PIDCheck);
        rearLeft = rLMotor.getEncoder(SparkMaxRelativeEncoder.Type.kQuadrature,Constants.PIDCheck);
        frontRight = fRMotor.getEncoder(SparkMaxRelativeEncoder.Type.kQuadrature,Constants.PIDCheck);
        rearRight = rRMotor.getEncoder(SparkMaxRelativeEncoder.Type.kQuadrature,Constants.PIDCheck);
    }

    // Sets the brake
    public void setBrake(){
        frontLeft.setVelocityConversionFactor(Constants.PIDStop);
        rearLeft.setVelocityConversionFactor(Constants.PIDStop);
        frontRight.setVelocityConversionFactor(Constants.PIDStop);
        rearRight.setVelocityConversionFactor(Constants.PIDStop);
    }

    // Releases the brake
    public void releaseBrake(){
        
        frontLeft.setVelocityConversionFactor(Constants.PIDSlow);
        rearLeft.setVelocityConversionFactor(Constants.PIDSlow);
        frontRight.setVelocityConversionFactor(Constants.PIDSlow);
        rearRight.setVelocityConversionFactor(Constants.PIDSlow);
    }

    // Goes forward
    public void forward(double speed){
        frontLeft.getPosition();
        //
        frontLeft.setVelocityConversionFactor(speed);
        rearLeft.setVelocityConversionFactor(speed);
        frontRight.setVelocityConversionFactor(speed);
        rearRight.setVelocityConversionFactor(speed);
        //
        frontLeft.setInverted(true);
        rearLeft.setInverted(true);
        //
        frontRight.setInverted(false);
        rearRight.setInverted(false);
    }

    // Goes reverse
    public void reverse(double speed){
        frontLeft.getPosition();
        //
        frontLeft.setVelocityConversionFactor(speed);
        rearLeft.setVelocityConversionFactor(speed);
        frontRight.setVelocityConversionFactor(speed);
        rearRight.setVelocityConversionFactor(speed);
        //
        frontLeft.setInverted(false);
        rearLeft.setInverted(false);
        //
        frontRight.setInverted(true);
        rearRight.setInverted(true);
    }

    // Call release brake, forward, and set brake
    public void forwardMove(double desiredPosition){
        desired = desiredPosition;
        frontLeft.setPosition(0);
        releaseBrake();
        forward(Constants.PIDSlow);
        
        
        //setBrake();

    }

    // Call release brake, reverse, and set brake
    public void reverseMove(double desiredPosition){
        desired = desiredPosition;
        frontLeft.setPosition(0);
        releaseBrake();
        reverse(Constants.PIDSlow);
        
        
        //setBrake();
    }

    // Returns true if we have reached our desired position
    public boolean checkStop(){
        if(Math.abs(frontLeft.getPosition()) >= desired){
            return true;
        }

        return false;
    }
}
