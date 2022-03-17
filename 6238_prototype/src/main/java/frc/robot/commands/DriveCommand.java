package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.SmartDashboardParam;
import frc.robot.subsystems.DriveSubsystem;

public class DriveCommand extends CommandBase {
    private final DriveSubsystem drive;
    private Joystick joystick;
    private SmartDashboardParam forwardSlewRate;
    private SmartDashboardParam rotateSlewRate;

    public DriveCommand(DriveSubsystem drive, Joystick joystick) {
        this.drive = drive;
        this.joystick = joystick;
        addRequirements(drive);
        forwardSlewRate = new SmartDashboardParam("forwardSlewRate");
        rotateSlewRate = new SmartDashboardParam("rotateSlewRate");
    }

    public void execute() {
        double speed = Math.abs(joystick.getY()) * joystick.getY();
        speed *= Math.abs(speed) < 0.01 ? 0 : 1;
        double rotation = joystick.getX();
        rotation *= Math.abs(rotation) < 0.05 ? 0 : 1;
        drive.setSlewRate(forwardSlewRate.get(), rotateSlewRate.get());
        drive.setDrive(speed, rotation);
        System.out.println("(Drive Command) Speed: " + speed + ", Rotation: " + rotation);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
