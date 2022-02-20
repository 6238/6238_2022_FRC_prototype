package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.Constants;

public class ClimberSubsystem extends SubsystemBase {
    private final WPI_TalonSRX rotationalController;
    private final CANSparkMax translationalControllerLeft;
    private final CANSparkMax translationalControllerRight;
    private double rotationalSpeed;
    private double translationalSpeed;

    private final DigitalInput bottomLimitSwitch;
    private final DigitalInput topLimitSwitch;


    public ClimberSubsystem() {
        rotationalSpeed = 0.0;
        translationalSpeed = 0.0;
        rotationalController = new WPI_TalonSRX(Constants.CLIMBER_ROTATION_ID);
        translationalControllerLeft = new CANSparkMax(Constants.CLIMBER_TRANSLATION_ID_LEFT, MotorType.kBrushless);
        translationalControllerRight = new CANSparkMax(Constants.CLIMBER_TRANSLATION_ID_RIGHT, MotorType.kBrushless);
        bottomLimitSwitch = new DigitalInput(Constants.CLIMBER_BOTTOM_LIMIT_SWITCH);
        topLimitSwitch = new DigitalInput(Constants.CLIMBER_TOP_LIMIT_SWITCH);
    }

    public void setRotation(double speed) {
        rotationalSpeed = speed;
    }

    public void setTranslation(double speed) {
        if (bottomLimitSwitch.get()) {
            translationalSpeed = Math.max(0, speed);
        } else if (topLimitSwitch.get()) {
            translationalSpeed = Math.min(speed, 0);
        }
    }

    @Override
    public void periodic() {
        rotationalController.set(rotationalSpeed);
        translationalControllerLeft.set(translationalSpeed);
        translationalControllerRight.set(-translationalSpeed);
    }
}
