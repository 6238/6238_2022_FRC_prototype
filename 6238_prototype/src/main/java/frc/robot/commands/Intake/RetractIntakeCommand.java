package frc.robot.commands.Intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.commands.BallTunedConstants;
import frc.robot.subsystems.BallSubsystem;

public class RetractIntakeCommand extends CommandBase{
    private final long timeLimit;
    private final BallSubsystem ball;
    private long startTime;
    public RetractIntakeCommand(BallSubsystem ball) {
        this.ball = ball;
        timeLimit = 2000;
        
        addRequirements(ball);
    }
    @Override
    public void initialize(){
        startTime = System.currentTimeMillis();
    }
    @Override
    public void execute() {
        ball.retract();
        ball.setSpeed(BallTunedConstants.INTAKE_MODE_LOWER_MOTOR_SPEED,
            BallTunedConstants.INTAKE_MODE_UPPER_MOTOR_RPM);
    }

    @Override
    public void end(boolean interrupted) {
        ball.setSpeed(0, 0);
    }

    @Override
    public boolean isFinished() {
        long currentTime = System.currentTimeMillis();
        long timeElapsed = currentTime - startTime;
        return timeElapsed > timeLimit;
    }
}
