package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.SmartDashboardParam;
import frc.robot.subsystems.BallSubsystem;

public class ShooterCommand extends CommandBase{
    private final BallSubsystem ball; 
    private final SmartDashboardParam upperShooterSpeedTarget;
    public ShooterCommand(BallSubsystem ball) {
        this.ball = ball;
        addRequirements(ball);
        upperShooterSpeedTarget = new SmartDashboardParam("upperMotorSpeedTarget");
    }

    public void execute() {
        ball.extend();
        double upperMotorSpeedTarget = upperShooterSpeedTarget.get();
        double upperMotorSpeedCurrent = ball.getSpeedUpperMotor();
        SmartDashboard.putNumber("upperMotorSpeedCurrent", upperMotorSpeedCurrent);
        SmartDashboard.putNumber("upperMotorSpeedTarget", upperMotorSpeedTarget);
        SmartDashboard.putNumber("upperMotorSpeedError",
            upperMotorSpeedTarget - upperMotorSpeedCurrent);
    }

    public void end() {
        ball.retract();
    }
}
