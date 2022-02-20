package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class DriveCommand extends CommandBase {
    private final DriveSubsystem drive;
    private double speed;
    private double rotation;

    public DriveCommand(DriveSubsystem drive) {
        speed = 0;
        rotation = 0;
        this.drive = drive;
        addRequirements(drive);
    }

    public void execute() {
        drive.setDrive(speed, rotation);
    }

    public void setDrive(double speed, double rotation) {
        this.speed = speed;
        this.rotation = rotation;
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
