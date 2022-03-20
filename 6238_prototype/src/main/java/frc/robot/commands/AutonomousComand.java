package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.BallSubsystem;
import frc.robot.subsystems.DriveSubsystem;

public class AutonomousComand extends CommandBase {
    private final long timeLimit;
    private final double speed;
    private final DriveSubsystem drive;
    private final BallSubsystem ball;
    long startTime;

    public AutonomousComand(DriveSubsystem drive, BallSubsystem ball) {
        this.drive = drive;
        this.ball = ball;
        timeLimit = 10000;
        speed = 0.5;
        drive.setDrive(0,0);
        ball.setSpeed (0,0);
        addRequirements(drive, ball);
    }
    @Override
    public void initialize() {
        startTime = System.currentTimeMillis();
    }
    @Override
    public void execute() {
        long timeElapsed = System.currentTimeMillis() - startTime;
        if (2000 < timeElapsed && timeElapsed < 7000 ){
            drive.setDrive(0,0);
            double bottomMotorSpeed = 0;
            double upperMotorSpeed = .88;
            double upperMotorSpeedCurrent = ball.getSpeedUpperMotor();

            if (upperMotorSpeedCurrent > 4200) {//upperShooterSpeedThreshold.get())
                bottomMotorSpeed = 1;
            }        
            ball.setSpeed(bottomMotorSpeed, upperMotorSpeed);
        } else {
            drive.setDrive(speed,0);
            ball.setSpeed(0,0);

        }
        
    }
    
    @Override
    public boolean isFinished() {
        long timeElapsed = System.currentTimeMillis() - startTime;
        return timeElapsed > timeLimit;
    }

    @Override
    public void end(boolean interrupted) {
        drive.setDrive(0,0);
    }
}