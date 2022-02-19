package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import frc.robot.Constants;

public class DriveSubsystem extends SubsystemBase {
    private final WPI_TalonSRX talonLeftLeader = new WPI_TalonSRX(Constants.LEFT_LEADER_ID);
    private final WPI_TalonSRX talonLeftFollower = new WPI_TalonSRX(Constants.LEFT_FOLLOWER_ID);
    private final WPI_TalonSRX talonRightLeader = new WPI_TalonSRX(Constants.RIGHT_LEADER_ID);
    private final WPI_TalonSRX talonRightFollower = new WPI_TalonSRX(Constants.RIGHT_FOLLOWER_ID);
    private final DifferentialDrive robotDrive = new DifferentialDrive(talonLeftLeader, talonRightLeader);

    private double speed;
    private double rotation;

    public DriveSubsystem() {
        talonLeftFollower.follow(talonLeftLeader);
        talonRightFollower.follow(talonRightLeader);
        talonRightLeader.setInverted(true);
    }

    public void setDrive(double speed, double rotation) {
        this.speed = speed;
        this.rotation = rotation;
    }

    @Override
    public void periodic() {
        robotDrive.arcadeDrive(speed, rotation);
    }
}
