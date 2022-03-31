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
        upperShooterSpeedTarget = new SmartDashboardParam("upperMotorSpeedTarget");
        addRequirements(ball);
    }

    public void execute() {
        ball.retract();
        ball.setSpeed(0.0, upperShooterSpeedTarget.get());
    }

    public void end() {
        ball.setSpeed(0, 0);
    }
}
