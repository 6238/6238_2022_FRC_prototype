package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class DriveAutonomousCommand extends CommandBase {
    private final long timeLimit;
    private final double speed; 
    private final DriveSubsystem drive;
    long startTime;

    public DriveAutonomousCommand(DriveSubsystem drive) {
        this.drive = drive;
        timeLimit = 2000;
        speed = 0.4;
        drive.setDrive(speed, 0);
        addRequirements(drive);
    }

    @Override
    public void initialize() {
        startTime = System.currentTimeMillis();
        drive.setSlewRate(100, 0);
    }

    @Override
    public void execute() {
        long timeElapsed = System.currentTimeMillis() - startTime;
        System.out.println("Speed: " + speed + ", TimeElapsed: " + timeElapsed);
        drive.setDrive(speed, 0);
    }

    @Override
    public boolean isFinished() {;
        long timeElapsed = System.currentTimeMillis() - startTime;
        System.out.println("Speed: " + speed + ", TimeElapsed: " + timeElapsed);
        return timeElapsed > timeLimit;
    }

    @Override
    public void end(boolean interrupted) {
        drive.setDrive(0, 0);
        System.out.println("Autonomous Mode Finished.");
    }
    
}
