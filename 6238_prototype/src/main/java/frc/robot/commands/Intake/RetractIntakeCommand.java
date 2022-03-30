package frc.robot.commands.Intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.BallSubsystem;

public class RetractIntakeCommand extends CommandBase{
    private final long timeLimit;
    BallSubsystem ball;
    long startTime;
    public RetractIntakeCommand(BallSubsystem ball) {
        this.ball = ball;
        addRequirements(ball);
        timeLimit = 2000;
        startTime = System.currentTimeMillis();
    }

    @Override
    public void execute() {
        ball.retract();
        double bottomMotorSpeed = 0.8;
        double upperMotorSpeed = -0.1;
        ball.setSpeed(bottomMotorSpeed, upperMotorSpeed);
    }

    @Override
    public boolean isFinished() {
        long currentTime = System.currentTimeMillis();
        long timeElapsed = currentTime - startTime;
        return timeElapsed > timeLimit;
    }
}
