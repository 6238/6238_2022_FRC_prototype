package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.math.filter.SlewRateLimiter;
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

    private SlewRateLimiter rotateSlewRateLimiter;
    private SlewRateLimiter forwardSlewRateLimiter; 

    private double rotateSlewRate;
    private double forwardSlewRate;

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

        rotation = 0.0;
        speed = 0.0;
        rotateSlewRate = 0.0;
        forwardSlewRate = 0.0;
        rotateSlewRateLimiter = new SlewRateLimiter(rotateSlewRate);
        forwardSlewRateLimiter = new SlewRateLimiter(forwardSlewRate);
    }

    public void setDrive(double speed, double rotation) {
        System.out.println("setDrive speed="+speed);
        this.speed = speed;
        this.rotation = rotation;
    }

    public void setSlewRate(double forwardSlewRate, double rotateSlewRate) {
        System.out.println("forwardSlewRate: " + forwardSlewRate + ", rotateSlewRate: " + rotateSlewRate);
        if (forwardSlewRate != this.forwardSlewRate) {
            this.forwardSlewRate = forwardSlewRate;
            forwardSlewRateLimiter = new SlewRateLimiter(this.forwardSlewRate);
        }
        if (rotateSlewRate != this.rotateSlewRate) {
            this.rotateSlewRate = rotateSlewRate;
            rotateSlewRateLimiter = new SlewRateLimiter(this.rotateSlewRate);
        }
    }

    @Override
    public void periodic() {
//        robotDrive.arcadeDrive(-forwardSlewRateLimiter.calculate(speed),
//            -rotateSlewRateLimiter.calculate(rotation));
        System.out.println("Speed: " + speed + ", Rotation:" + rotation);
        robotDrive.arcadeDrive(-speed, -rotation);
    }
}
