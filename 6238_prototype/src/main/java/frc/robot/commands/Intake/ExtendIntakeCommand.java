package frc.robot.commands.Intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.commands.BallTunedConstants;
import frc.robot.subsystems.BallSubsystem;

public class ExtendIntakeCommand extends CommandBase{
    BallSubsystem ball;
    public ExtendIntakeCommand(BallSubsystem ball) {
        this.ball = ball;
    }

    @Override
    public void execute() {
        ball.setSpeed(BallTunedConstants.INTAKE_MODE_LOWER_MOTOR_SPEED,
            BallTunedConstants.INTAKE_MODE_UPPER_MOTOR_RPM);
    }

    @Override
    public boolean isFinished() {
        return ball.getRPMLowerMotor() > 3000;
    }

    @Override
    public void end(boolean interrupted) {
        ball.extend();
    }
}
