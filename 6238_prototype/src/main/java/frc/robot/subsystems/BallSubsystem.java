package frc.robot.subsystems;


import edu.wpi.first.wpilibj.DoubleSolenoid;
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

    SmartDashboardParam shooterPGainSlider = new SmartDashboardParam("shooterPGain");
    SmartDashboardParam shooterIGainSlider = new SmartDashboardParam("shooterIGain");
    SmartDashboardParam shooterDGainSlider = new SmartDashboardParam("shooterDGain");
    SmartDashboardParam shooterFFGainSlider = new SmartDashboardParam("shooterFFGain");

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
        upperMotor.setIdleMode(IdleMode.kCoast);

        lowerMotor = new CANSparkMax(Constants.BALL_LOWER_ID, MotorType.kBrushless);
// yellow zipties are intake
        doubleSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 3, 1);
        leftKicker = new Solenoid(PneumaticsModuleType.CTREPCM, 6);
        rightKicker = new Solenoid(PneumaticsModuleType.CTREPCM, 7);

        pidController = upperMotor.getPIDController();

        kP = shooterPGainSlider.get();
        kI = shooterIGainSlider.get();
        kD = shooterDGainSlider.get();
        kFF = shooterFFGainSlider.get();
        pidController.setP(kP); // 0.00018  |  0.0001 | 0.00001
        pidController.setI(kI); // 0.00000001  |  0.0000009 | 0.00000042
        pidController.setD(kD); // 0 | 0.01 |  0
        pidController.setIZone(0);
        pidController.setFF(kFF); // 0.00018  | .00018 | 0.0000058
        pidController.setOutputRange(-1, 1);

        SmartDashboard.putNumber("upperMotorRPMCurrent", getRPMUpperMotor());
        SmartDashboard.putNumber("upperMotorRPMTarget", upperMotorRPMTarget);
        SmartDashboard.putNumber("upperMotorSpeedError", upperMotorRPMTarget - getRPMUpperMotor());
    }

    public static void activateLeftKicker(boolean activate){
        leftKicker.set (activate);
    }
    
    public static void activateRightKicker(boolean activate){
        rightKicker.set (activate);
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
        pidController.setReference(-upperMotorRPMTarget, CANSparkMax.ControlType.kVelocity);
        lowerMotor.set(-lowerSpeed);
    }

    public double getRPMUpperMotor() {
        return -upperMotorEncoder.getVelocity();
    }
}
