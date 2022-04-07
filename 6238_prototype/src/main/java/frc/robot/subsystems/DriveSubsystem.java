package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatorCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.kauailabs.navx.frc.AHRS;

import frc.robot.Constants;
import frc.robot.SmartDashboardParam;

public class DriveSubsystem extends SubsystemBase {
    private final WPI_TalonFX talonLeftLeader = new WPI_TalonFX(Constants.LEFT_LEADER_ID);
    private final WPI_TalonFX talonLeftFollowerOne = new WPI_TalonFX(Constants.LEFT_FOLLOWER_ID_ONE);
    private final WPI_TalonFX talonLeftFollowerTwo = new WPI_TalonFX(Constants.LEFT_FOLLOWER_ID_TWO);
    private final WPI_TalonFX talonRightLeader = new WPI_TalonFX(Constants.RIGHT_LEADER_ID);
    private final WPI_TalonFX talonRightFollowerOne = new WPI_TalonFX(Constants.RIGHT_FOLLOWER_ID_ONE);
    private final WPI_TalonFX talonRightFollowerTwo = new WPI_TalonFX(Constants.RIGHT_FOLLOWER_ID_TWO);
    private final DifferentialDrive robotDrive = new DifferentialDrive(talonLeftLeader, talonRightLeader);

    private final double kCountsPerRev = 2048;
    private final double kGearRatio = 20;
    private final double kWheelRadiusInches = 3;

    private final SmartDashboardParam currentLimit = new SmartDashboardParam("currentLimiter"); // 0.18

    private final AHRS ahrs = new AHRS(SPI.Port.kMXP);

    private double speed;
    private double rotation;
    private final int PID_ID;
    boolean isBraking;

    public DriveSubsystem() {
        PID_ID = 0;
        isBraking = true;
        talonLeftFollowerOne.follow(talonLeftLeader);
        talonLeftFollowerTwo.follow(talonLeftLeader);
        talonRightFollowerOne.follow(talonRightLeader);
        talonRightFollowerTwo.follow(talonRightLeader);
        talonLeftLeader.setInverted(true);
        talonLeftFollowerOne.setInverted(true);
        talonLeftFollowerTwo.setInverted(true);

        talonLeftLeader.setNeutralMode(NeutralMode.Brake);
        talonLeftFollowerOne.setNeutralMode(NeutralMode.Brake);
        talonLeftFollowerTwo.setNeutralMode(NeutralMode.Brake);
        talonRightLeader.setNeutralMode(NeutralMode.Brake);
        talonRightFollowerOne.setNeutralMode(NeutralMode.Brake);
        talonRightFollowerTwo.setNeutralMode(NeutralMode.Brake);

        talonLeftLeader.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, PID_ID, 0);
        talonRightLeader.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, PID_ID, 0);

        talonLeftLeader.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(true, currentLimit.get(), 0, 0));
        talonLeftFollowerOne.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(true, currentLimit.get(), 0, 0));
        talonLeftFollowerTwo.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(true, currentLimit.get(), 0, 0));
        talonRightLeader.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(true, currentLimit.get(), 0, 0));
        talonRightFollowerOne.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(true, currentLimit.get(), 0, 0));
        talonRightFollowerTwo.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(true, currentLimit.get(), 0, 0));
    }

    public void setDrive(double speed, double rotation) {
        this.speed = speed;
        this.rotation = rotation;
    }

    @Override
    public void periodic() {
        boolean isBraking = Math.abs(speed) < 0.01 && Math.abs(rotation) < 0.01;  
        if (isBraking && !this.isBraking) {
            this.isBraking = true;
            talonLeftLeader.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(true, currentLimit.get(), 0, 0));
            talonLeftFollowerOne.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(true, currentLimit.get(), 0, 0));
            talonLeftFollowerTwo.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(true, currentLimit.get(), 0, 0));
            talonRightLeader.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(true, currentLimit.get(), 0, 0));
            talonRightFollowerOne.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(true, currentLimit.get(), 0, 0));
            talonRightFollowerTwo.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(true, currentLimit.get(), 0, 0));
        }  else if (!isBraking && this.isBraking) {
            this.isBraking = false;
            talonLeftLeader.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(false, currentLimit.get(), 0, 0));
            talonLeftFollowerOne.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(false, currentLimit.get(), 0, 0));
            talonLeftFollowerTwo.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(false, currentLimit.get(), 0, 0));
            talonRightLeader.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(false, currentLimit.get(), 0, 0));
            talonRightFollowerOne.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(false, currentLimit.get(), 0, 0));
            talonRightFollowerTwo.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(false, currentLimit.get(), 0, 0));
        }
        robotDrive.arcadeDrive(-speed, -rotation);
        SmartDashboard.putNumber("DriveSpeed", -speed);
        SmartDashboard.putNumber("DriveRotation", rotation);
  
        SmartDashboard.putNumber("leftMotorsEncoderVelocity", talonLeftLeader.getSelectedSensorVelocity(PID_ID) * 0.1);
        SmartDashboard.putNumber("rightMotorsEncoderVelocity", talonRightLeader.getSelectedSensorVelocity(PID_ID) * 0.1);
    }

    private double nativeUnitsToDistanceMeters(double sensorCounts){
        double motorRotations = sensorCounts / kCountsPerRev;
        double wheelRotations = motorRotations / kGearRatio;
        double positionMeters = wheelRotations * (2 * Math.PI * Units.inchesToMeters(kWheelRadiusInches));
        return positionMeters;
    }

    public void resetEncoders() {
        talonLeftLeader.setSelectedSensorPosition(0);
        talonRightLeader.setSelectedSensorPosition(0);
    }

    public double getPosition() {
        return nativeUnitsToDistanceMeters(
            talonLeftLeader.getSelectedSensorPosition() / 2
            + talonRightLeader.getSelectedSensorPosition() / 2);
    }

    public double getAngle() {
        return Math.IEEEremainder(ahrs.getAngle(), 360);
    }
}
