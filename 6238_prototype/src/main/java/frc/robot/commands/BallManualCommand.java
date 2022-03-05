
package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.BallSubsystem;

public class BallManualCommand extends CommandBase {
    private final BallSubsystem ball;
    private double lowerUserIntakeSpeed;
    private double upperUserIntakeSpeed;
    private double lowerUserShooterSpeed;
    private double upperUserShooterSpeed;
    private double lowerIntakeSpeed;
    private double upperIntakeSpeed;
    private double lowerShooterSpeed;
    private double upperShooterSpeed;

    private boolean shooterEnabled;

    public BallManualCommand(BallSubsystem ball) {
        shooterEnabled = false;

        lowerIntakeSpeed = 0;
        upperIntakeSpeed = 0;
        lowerShooterSpeed = 0;
        upperShooterSpeed = 0;

        lowerUserIntakeSpeed = SmartDashboard.getNumber("lowerIntakeSpeed", lowerUserIntakeSpeed);
        SmartDashboard.putNumber("lowerIntakeSpeed", lowerUserIntakeSpeed);

        upperUserIntakeSpeed = SmartDashboard.getNumber("upperIntakeSpeed", upperUserIntakeSpeed);
        SmartDashboard.putNumber("upperIntakeSpeed", upperUserIntakeSpeed);

        lowerUserShooterSpeed = SmartDashboard.getNumber("lowerShooterSpeed", lowerUserShooterSpeed);
        SmartDashboard.putNumber("lowerShooterSpeed", lowerUserShooterSpeed);

        upperUserShooterSpeed = SmartDashboard.getNumber("upperShooterSpeed", upperUserShooterSpeed);
        SmartDashboard.putNumber("upperShooterSpeed", upperUserShooterSpeed);

        this.ball = ball;
        addRequirements(ball);
    }

    public void execute() {
        lowerUserIntakeSpeed = SmartDashboard.getNumber("lowerIntakeSpeed", lowerUserIntakeSpeed);
        upperUserIntakeSpeed = SmartDashboard.getNumber("upperIntakeSpeed", upperUserIntakeSpeed);
        lowerUserShooterSpeed = SmartDashboard.getNumber("lowerUserShooterSpeed", lowerUserShooterSpeed);
        upperUserShooterSpeed = SmartDashboard.getNumber("upperUserShooterSpeed", upperUserShooterSpeed);
        if (ball.getIsExtended()) {
            ball.setSpeed(upperIntakeSpeed, lowerIntakeSpeed);
        } else if (shooterEnabled) {
            ball.setSpeed(upperShooterSpeed, lowerShooterSpeed);
        }
    }

    public void startShooter() {
        if (!ball.getIsExtended()) {
            shooterEnabled = true;
        }
    }

    public void stopShooter() {
        shooterEnabled = false;
    }

    public void startIntake() {
        if (!shooterEnabled) {
            ball.extend();
            lowerIntakeSpeed = lowerUserIntakeSpeed;
            upperIntakeSpeed = upperUserIntakeSpeed;
        }
    }

    public void stopIntake() {
        ball.retract();
        lowerIntakeSpeed = 0;
        upperIntakeSpeed = 0;
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
