package frc.robot.subsystems;


import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.Constants;
import frc.robot.IOConstants;
import frc.robot.SmartDashboardParam;

public class BallSubsystem extends SubsystemBase {
    private final CANSparkMax upperMotor;
    private final CANSparkMax lowerMotor;
    private final DoubleSolenoid doubleSolenoid;
    private static Solenoid leftKicker; 
    private static Solenoid rightKicker; 
    private double upperMotorRPMTarget;
    private double lowerSpeed;
    private boolean isExtended;
    private RelativeEncoder upperMotorEncoder;
    private RelativeEncoder lowerMotorEncoder;

    SmartDashboardParam shooterPGainSlider = new SmartDashboardParam("shooterPGain", 0.00016);
    SmartDashboardParam shooterIGainSlider = new SmartDashboardParam("shooterIGain",0.000001);
    SmartDashboardParam shooterDGainSlider = new SmartDashboardParam("shooterDGain",0);
    SmartDashboardParam shooterFFGainSlider = new SmartDashboardParam("shooterFFGain",0.000208);

    double kP;
    double kI;
    double kD;
    double kFF;

    private SparkMaxPIDController pidController;

    public BallSubsystem() {
        upperMotorRPMTarget = 0.0;
        lowerSpeed = 0.0;
        isExtended = false;
        
        upperMotor = new CANSparkMax(Constants.BALL_UPPER_ID, MotorType.kBrushless);
        upperMotorEncoder = upperMotor.getEncoder();
        upperMotor.setIdleMode(IdleMode.kBrake);
        upperMotor.enableVoltageCompensation(11);

        lowerMotor = new CANSparkMax(Constants.BALL_LOWER_ID, MotorType.kBrushless);
// yellow zipties are intake
        lowerMotorEncoder = lowerMotor.getEncoder();

        doubleSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 3, 1);
        leftKicker = new Solenoid(PneumaticsModuleType.CTREPCM, 6);
        rightKicker = new Solenoid(PneumaticsModuleType.CTREPCM, 7);

        pidController = upperMotor.getPIDController();

        kP = shooterPGainSlider.get();
        kI = shooterIGainSlider.get();
        kD = shooterDGainSlider.get();
        kFF = shooterFFGainSlider.get();
        pidController.setP(kP); // 0.00018  |  0.0001 | 0.00001 ====> 0.00016
        pidController.setI(kI); // 0.00000001  |  0.0000009 | 0.00000042 ===> 0.
        pidController.setD(kD); // 0 | 0.01 |  0  ===> 0
        pidController.setIZone(100);
        pidController.setFF(kFF); // 0.00018  | .00018 | 0.0000058  ==> 0.000205
        pidController.setOutputRange(-1, 1);

    }

    public void activateLeftKicker(boolean activate){
        leftKicker.set(activate);
    }
    
    public void activateRightKicker(boolean activate){
        rightKicker.set(activate);
    }

    public void setSpeed(double lowerSpeed, double upperMotorRPMTarget) {
        this.upperMotorRPMTarget = upperMotorRPMTarget;
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
        if (kP != shooterPGainSlider.get()) {
            kP = shooterPGainSlider.get();
            pidController.setP(kP);
        }
        if (kI != shooterIGainSlider.get()) {
            kI = shooterIGainSlider.get();
            pidController.setI(kI);
        }
        if (kD != shooterDGainSlider.get()) {
            kD = shooterDGainSlider.get();
            pidController.setD(kD);
        }
        if (kFF != shooterFFGainSlider.get()) {
            kFF = shooterFFGainSlider.get();
            pidController.setFF(kFF);
        }

        

        if (isExtended) {
            doubleSolenoid.set(DoubleSolenoid.Value.kReverse);
        } else {
            doubleSolenoid.set(DoubleSolenoid.Value.kForward);
        }

        if (upperMotorRPMTarget == 0) {
            pidController.setIAccum(0);
            pidController.setReference(0, CANSparkMax.ControlType.kVelocity);
            upperMotor.set(0);
        } else {
            pidController.setReference(-upperMotorRPMTarget, CANSparkMax.ControlType.kVelocity);
        }
        lowerMotor.set(-lowerSpeed);
       
        SmartDashboard.putNumber("upperMotorRPMCurrent", getRPMUpperMotor());
        SmartDashboard.putNumber("upperMotorRPMTarget", upperMotorRPMTarget);
        SmartDashboard.putNumber("upperMotorSpeedError", upperMotorRPMTarget - getRPMUpperMotor());
        SmartDashboard.putNumber("lowerMotorSpeed", lowerSpeed);
    }

    public double getRPMUpperMotor() {
        return -upperMotorEncoder.getVelocity();
    }

    public double getRPMLowerMotor() {
        return -lowerMotorEncoder.getVelocity();
    }
}
