package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class DriveCommand extends CommandBase {
    private final DriveSubsystem drive;
    private final Joystick joystick;

    public DriveCommand(DriveSubsystem drive, Joystick joystick) {

        this.joystick = joystick;
        this.drive = drive;

        addRequirements(drive);
    }

    public void execute() {
        double x = joystick.getX();
        double y = joystick.getY();
        if (Math.abs(x) < 0.3) {
            x = 0;
        }
        if (Math.abs(y) < 0.3) {
            y = 0;
        }

        drive.setDrive(-y, x);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
