package frc.robot.commands;

import frc.robot.subsystems.BallSubsystem;

public class DeadlinedShooterCommand extends ShooterCommand {
    private long timerStart;
    private final long timeLimit;
    private final double rpmDiffTolerance;

    public DeadlinedShooterCommand(BallSubsystem ball) {
        super(ball);
        timeLimit = 2000;
        timerStart = System.currentTimeMillis();
        rpmDiffTolerance = 50;
    }

    @Override
    public void execute() {
        if (getUpperShooterRPMActual() < getUpperShooterRPMTarget() - rpmDiffTolerance
            || getUpperShooterRPMTarget() + rpmDiffTolerance < getUpperShooterRPMActual()) {
            restartTimer();
        }
    }

    @Override
    public boolean isFinished() {
        return System.currentTimeMillis() - timerStart > timeLimit;
    }

    private void restartTimer() {
        timerStart = System.currentTimeMillis();
    }
}
