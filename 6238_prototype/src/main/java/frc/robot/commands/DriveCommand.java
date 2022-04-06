package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.SmartDashboardParam;
import frc.robot.subsystems.DriveSubsystem;

public class DriveCommand extends CommandBase {
    private final DriveSubsystem drive;
    private Joystick joystick;
    private double maximumDecceleration;
    // Positive Value
    private double maximumAcceleration;
    // Positive Value

    private final SmartDashboardParam accelerationSlewRate = new SmartDashboardParam("accelerationSlewRate", 0.05);
    private final SmartDashboardParam deccelerationSlewRate = new SmartDashboardParam("deccelerationSlewRate", 1);

    private double previousSpeed;

    public DriveCommand(DriveSubsystem drive, Joystick joystick) {
        this.drive = drive;
        this.joystick = joystick;
        previousSpeed = 0;
        maximumDecceleration = deccelerationSlewRate.get(); // 0.03
        maximumAcceleration = accelerationSlewRate.get(); // 0.04
        addRequirements(drive);
    }

    @Override
    public void execute() {
        // temp
        maximumDecceleration = deccelerationSlewRate.get();
        maximumAcceleration = accelerationSlewRate.get();

        double inputSpeed = joystick.getY();
        SmartDashboard.putNumber("DriveInput", -inputSpeed);

        double speed = Math.abs(inputSpeed) * inputSpeed;
        speed *= Math.abs(speed) < 0.01 ? 0 : 1;
        if (Math.abs(speed) < 2) {
            if (speed > 0) {
                if (speed > previousSpeed && Math.abs(speed - previousSpeed) > maximumAcceleration) {
                    speed = previousSpeed + maximumAcceleration;
                } else if (speed < previousSpeed && Math.abs(previousSpeed - speed) > maximumDecceleration){
                    speed = previousSpeed - maximumDecceleration;
                }
            } else {
                if (speed > previousSpeed && Math.abs(speed - previousSpeed) > maximumDecceleration) {
                    speed = previousSpeed + maximumDecceleration;
                } else if (speed < previousSpeed && Math.abs(previousSpeed - speed) > maximumAcceleration) {
                    speed = previousSpeed - maximumAcceleration;
                }
            }
        }
        previousSpeed = speed;
        if (speed > .05){
            speed = speed * .65 + .35;
        } else if (speed < -.05) {
            speed = speed * .65 - .35;
        } else {
            speed = 0;
        }

        double rotation = joystick.getX();
        //rotation *= Math.abs(rotation) < 0.05 ? 0 : 1; 
        SmartDashboard.putNumber("DriveInputy", -rotation);
        
        double speedRot = Math.abs(rotation) * rotation;
        speedRot *= Math.abs(speedRot) < 0.05 ? 0 : 1;
       
        if (speedRot > .05){
            speedRot = speedRot * .65 + .25;
        } else if (speedRot < -.05) {
            speedRot = speedRot * .65 - .25;
        } else {
            speedRot = 0;
        }
       
       drive.setDrive(speed, rotation);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
