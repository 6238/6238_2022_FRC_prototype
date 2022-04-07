package frc.robot.commands.Intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.commands.BallTunedConstants;
import frc.robot.subsystems.BallSubsystem;

public class ExtendIntakeCommand extends CommandBase{
    private final BallSubsystem ball;
    private long timerStart;
    private long timeLimit;

    public ExtendIntakeCommand(BallSubsystem ball) {
        this.ball = ball;
        timeLimit = 500;
        addRequirements(ball);
    }

    @Override 
    public void initialize(){
        timerStart = System.currentTimeMillis();
    }

    @Override
    public void execute() {
        ball.setSpeed(BallTunedConstants.INTAKE_MODE_LOWER_MOTOR_SPEED,
            BallTunedConstants.INTAKE_MODE_UPPER_MOTOR_RPM);

        }
    @Override
    public boolean isFinished() {

        return (System.currentTimeMillis() - timerStart > timeLimit);
            
    }

    @Override
    public void end(boolean interrupted) {
        ball.extend();
    }
}
