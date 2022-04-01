package frc.robot.commands;

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

    protected double getUpperShooterRPMTarget() {
        return upperShooterSpeedTarget.get();
    }

    protected double getUpperShooterRPMActual() {
        return ball.getRPMUpperMotor();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void execute() {
        ball.retract();
        ball.setSpeed(0.0, upperShooterSpeedTarget.get());
    }

    @Override
    public void end(boolean interrupted) {
        ball.setSpeed(0, 0);
    }
}
