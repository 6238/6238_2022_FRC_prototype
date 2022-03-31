package frc.robot.commands.Intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.commands.BallTunedConstants;
import frc.robot.subsystems.BallSubsystem;

public class RunIntakeCommand extends CommandBase{
    private final BallSubsystem ball;
    public RunIntakeCommand(BallSubsystem ball) {
        this.ball = ball;
        addRequirements(ball);
    }

    @Override
    public void execute() {
        ball.extend();
        ball.setSpeed(BallTunedConstants.INTAKE_MODE_LOWER_MOTOR_SPEED,
            BallTunedConstants.INTAKE_MODE_UPPER_MOTOR_RPM);
    }
}
