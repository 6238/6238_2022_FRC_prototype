package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.SmartDashboardParam;
import frc.robot.subsystems.BallSubsystem;

public class ShooterCommand extends CommandBase{
    public static enum PneumaticKickers {LEFT_KICKER, RIGHT_KICKER, BOTH};

    private final PneumaticKickers kicker;
    private final BallSubsystem ball; 
    private final double upperShooterRPMTarget;
    private long timerStart;
    private final long timeLimit;
    private final double rpmDiffTolerance;
    private boolean pneumaticsActivated;
    private long countdownStart;

    public ShooterCommand(BallSubsystem ball, PneumaticKickers kicker, double rpmTarget) {
        this.ball = ball;
        upperShooterRPMTarget = rpmTarget;
        timeLimit = 2000;
        timerStart = System.currentTimeMillis();
        countdownStart = Long.MAX_VALUE;
        rpmDiffTolerance = 50;
        this.kicker = kicker;
        pneumaticsActivated = true;
        addRequirements(ball);
    }

    private void restartTimer() {
        timerStart = System.currentTimeMillis();
    }

    @Override
    public void execute() {
        if (ball.getRPMUpperMotor() < upperShooterRPMTarget - rpmDiffTolerance
            || upperShooterRPMTarget + rpmDiffTolerance < ball.getRPMUpperMotor()) {
            restartTimer();
        }

        if (System.currentTimeMillis() - timerStart > timeLimit && !pneumaticsActivated) {
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
            pneumaticsActivated = true;
            countdownStart = System.currentTimeMillis();
        }
    }

    @Override
    public boolean isFinished() {
        return System.currentTimeMillis() - countdownStart > 2000;
    }

    @Override
    public void end(boolean interrupted) {
        ball.setSpeed(0, 0);
        ball.activateLeftKicker(false);
        ball.activateRightKicker(false);
    }
}
