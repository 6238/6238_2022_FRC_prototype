package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.BallSubsystem;

public class StopShooterCommand extends CommandBase {
    private final BallSubsystem ball;
    public StopShooterCommand(BallSubsystem ball) {
        this.ball = ball;
        addRequirements(ball);
    }
    
    @Override
    public void initialize() {
        ball.setSpeed(0, 0);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
