package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.Constants;

public class ClimberSubsystem extends SubsystemBase {
    private final WPI_TalonSRX rotationalController;
    private final CANSparkMax translationalController;
    private double rotationalSpeed;
    private double translationalSpeed;


    public ClimberSubsystem() {
        rotationalSpeed = 0.0;
        translationalSpeed = 0.0;
        rotationalController = new WPI_TalonSRX(Constants.TALON_SRX_ID);
        translationalController = new CANSparkMax(Constants.SPARK_MAX_ID, MotorType.kBrushless);
    }

    public void setRotation(double speed) {
        rotationalSpeed = speed;
    }

    public void setTranslation(double speed) {
        translationalSpeed = speed;
    }

    @Override
    public void periodic() {
        rotationalController.set(rotationalSpeed);
        translationalController.set(translationalSpeed);
    }
}
