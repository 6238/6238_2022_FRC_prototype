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
        speed = 0.4;
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
        if (500 < timeElapsed && timeElapsed < 6000 ){
            drive.setDrive(0,0);
            double upperMotorRPM = BallTunedConstants.AUTONOMOUS_MODE_UPPER_MOTOR_RPM;
            ball.setSpeed(0, upperMotorRPM);

            if (3000 < timeElapsed && timeElapsed < 6000) {
                ball.activateLeftKicker(true);
                ball.activateRightKicker(true);
            } else {
                ball.activateLeftKicker(false);
                ball.activateRightKicker(false);
            }

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