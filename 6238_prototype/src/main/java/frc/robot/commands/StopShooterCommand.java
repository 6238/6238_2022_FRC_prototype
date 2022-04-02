package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.BallSubsystem;

public class StopShooterCommand extends CommandBase {
    BallSubsystem ball;
    public StopShooterCommand(BallSubsystem ball) {
        this.ball = ball;
    }

    @Override
    public void initialize() {
        ball.setSpeed(0, 0);
        ball.activateLeftKicker(false);
        ball.activateRightKicker(false);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
