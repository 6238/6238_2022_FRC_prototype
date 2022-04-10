package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class TimedDriveCommand extends CommandBase {
    private long startTime;
    private DriveSubsystem driveSubsystem;
    private double durationSeconds;
    public TimedDriveCommand(DriveSubsystem driveSubsystem, double durationSeconds) {
        this.driveSubsystem = driveSubsystem;
        this.durationSeconds = durationSeconds;
    }

    @Override
    public void initialize() {
        startTime = System.currentTimeMillis();
    }

    @Override
    public void execute() {
        driveSubsystem.setDrive(0, 0.4);
    }

    @Override
    public boolean isFinished() {
        return System.currentTimeMillis() - startTime > durationSeconds * 1000;
    }
}
