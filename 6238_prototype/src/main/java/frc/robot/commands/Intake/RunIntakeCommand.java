package frc.robot.commands.Intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.BallSubsystem;

public class RunIntakeCommand extends CommandBase{
    private final BallSubsystem ball;
    public RunIntakeCommand(BallSubsystem ball) {
        this.ball = ball;
        addRequirements(ball);
    }

    public void execute() {
        ball.extend();
        double bottomMotorSpeed = 0.8;
        double upperMotorSpeed = -0.1;
        ball.setSpeed(bottomMotorSpeed, upperMotorSpeed);
    }
}
