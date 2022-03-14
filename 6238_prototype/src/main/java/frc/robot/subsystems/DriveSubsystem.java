package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import frc.robot.Constants;

public class DriveSubsystem extends SubsystemBase {
    private final WPI_TalonSRX talonLeftLeader = new WPI_TalonSRX(Constants.LEFT_LEADER_ID);
    private final WPI_TalonSRX talonLeftFollowerOne = new WPI_TalonSRX(Constants.LEFT_FOLLOWER_ID_ONE);
    private final WPI_TalonSRX talonLeftFollowerTwo = new WPI_TalonSRX(Constants.LEFT_FOLLOWER_ID_TWO);
    private final WPI_TalonSRX talonRightLeader = new WPI_TalonSRX(Constants.RIGHT_LEADER_ID);
    private final WPI_TalonSRX talonRightFollowerOne = new WPI_TalonSRX(Constants.RIGHT_FOLLOWER_ID_ONE);
    private final WPI_TalonSRX talonRightFollowerTwo = new WPI_TalonSRX(Constants.RIGHT_FOLLOWER_ID_TWO);
    private final DifferentialDrive robotDrive = new DifferentialDrive(talonLeftLeader, talonRightLeader);

    private double speed;
    private double rotation;

    public DriveSubsystem() {
        talonLeftFollowerOne.follow(talonLeftLeader);
        talonLeftFollowerTwo.follow(talonLeftLeader);
        talonRightFollowerOne.follow(talonRightLeader);
        talonRightFollowerTwo.follow(talonRightLeader);
        talonLeftLeader.setInverted(true);
        talonLeftFollowerOne.setInverted(true);
        talonLeftFollowerTwo.setInverted(true);
    }

    public void setDrive(double speed, double rotation) {
        this.speed = 3 * speed / 4;
        /*
        if (speed < 0) {
            this.speed = Math.max(-0.5, speed);
        } else if (speed > 0) {
            this.speed = Math.min(speed, 0.5);
        }
        */
        this.rotation = 2 * rotation / 4;
    }

    @Override
    public void periodic() {
        robotDrive.arcadeDrive(-speed, -rotation);
    }
}
