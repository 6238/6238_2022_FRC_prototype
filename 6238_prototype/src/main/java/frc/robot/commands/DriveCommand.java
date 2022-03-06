package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class DriveCommand extends CommandBase {
    private final DriveSubsystem drive;
    private Joystick joystick;

    public DriveCommand(DriveSubsystem drive, Joystick joystick) {
        this.drive = drive;
        this.joystick = joystick;
        addRequirements(drive);
    }

    public void execute() {
        double speed = Math.abs(joystick.getY()) * joystick.getY();
        speed *= Math.abs(speed) < 0.01 ? 0 : 1;
        double rotation = joystick.getX();
        rotation *= Math.abs(rotation) < 0.05 ? 0 : 1; 
        drive.setDrive(speed, rotation);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
