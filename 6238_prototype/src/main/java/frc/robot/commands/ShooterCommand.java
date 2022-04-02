package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.BallSubsystem;

public class ShooterCommand extends CommandBase{
    public static enum PneumaticKickers {LEFT_KICKER, RIGHT_KICKER, BOTH};

    private final PneumaticKickers kicker;
    private final BallSubsystem ball; 
    protected double upperShooterRPMTarget;
    private long timerStart;
    private final long timeLimit;
    private final double rpmDiffTolerance;
    private long countdownStart;

    public ShooterCommand(BallSubsystem ball, PneumaticKickers kicker, double rpmTarget) {
        this.ball = ball;
        upperShooterRPMTarget = rpmTarget;
        timeLimit = 200;
        rpmDiffTolerance = 50;
        this.kicker = kicker;
        addRequirements(ball);
    }

    private void restartTimer() {
        timerStart = System.currentTimeMillis();

    }
    @Override
    public void initialize(){
        timerStart = System.currentTimeMillis();
        countdownStart = Long.MAX_VALUE;

    } 
    @Override
    public void execute() {
        if (Math.abs(ball.getRPMUpperMotor() - upperShooterRPMTarget) > rpmDiffTolerance) {
            restartTimer();
        }
        if (System.currentTimeMillis() - timerStart > timeLimit) {
            switch (kicker) {
            case LEFT_KICKER:
                ball.activateLeftKicker(true);
                break;
            case RIGHT_KICKER:
                ball.activateRightKicker(true);
                break;
            case BOTH:
                ball.activateLeftKicker(true);
                ball.activateRightKicker(true);
                break;
            }

            if (countdownStart == Long.MAX_VALUE) {
                countdownStart = System.currentTimeMillis();
            }
        }

        ball.setSpeed(0, upperShooterRPMTarget); 
    }

    @Override
    public boolean isFinished() {
        return System.currentTimeMillis() - countdownStart > 200;
    }
}
