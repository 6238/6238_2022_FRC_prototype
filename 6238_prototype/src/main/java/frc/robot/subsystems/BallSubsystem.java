package frc.robot.subsystems;


import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.Constants;

public class BallSubsystem extends SubsystemBase {
    private final CANSparkMax upperMotor;
    private final CANSparkMax lowerMotor;

    private final DoubleSolenoid doubleSolenoid;

    private double upperSpeed;
    private double lowerSpeed;
    private boolean isExtended;
    private RelativeEncoder upperMotorEncoder;

    public BallSubsystem() {
        upperSpeed = 0.0;
        lowerSpeed = 0.0;
        isExtended = false;
        upperMotor = new CANSparkMax(Constants.BALL_UPPER_ID, MotorType.kBrushless);
        upperMotorEncoder = upperMotor.getEncoder();

        lowerMotor = new CANSparkMax(Constants.BALL_LOWER_ID, MotorType.kBrushless);

        doubleSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 0, 1);
    }

    public void setSpeed(double lowerSpeed, double upperSpeed) {
        this.upperSpeed = upperSpeed;
        this.lowerSpeed = lowerSpeed;
    }
    
    public void extend() {
        isExtended = true;
    }

    public void retract() {
        isExtended = false;
    }

    public boolean getIsExtended() {
        return isExtended;
    }

    @Override
    public void periodic() {
        if (isExtended) {
            doubleSolenoid.set(DoubleSolenoid.Value.kReverse);
        } else {
            doubleSolenoid.set(DoubleSolenoid.Value.kForward);
        }
        upperMotor.set(-upperSpeed);
        lowerMotor.set(lowerSpeed);     
    }

    public double getSpeedUpperMotor() {
        return -upperMotorEncoder.getVelocity();
    }
}
