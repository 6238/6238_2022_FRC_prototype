
package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.SmartDashboardParam;
import frc.robot.subsystems.BallSubsystem;

public class BallManualCommand extends CommandBase {
    private final BallSubsystem ball;
    private enum ModeStates {SHOOTING, INTAKING};
    private ModeStates mode;
    private boolean motorOn;
    
    private double intakeRetractedTime;

    private final SmartDashboardParam lowerIntakeSpeed;
    private final SmartDashboardParam upperIntakeSpeed;
    private final SmartDashboardParam lowerShooterSpeed;
    private final SmartDashboardParam upperShooterSpeed;
    private final SmartDashboardParam upperShooterSpeedThreshold;

    private final double bottomMotorIntakeSpeed;
 
    public BallManualCommand(BallSubsystem ball) {
        //swtiches between referencing shooter or intake power value
        mode = ModeStates.SHOOTING;
        motorOn = false;

        //makes the dashboard
        lowerIntakeSpeed = new SmartDashboardParam("lowerIntakeSpeed");

        upperIntakeSpeed = new SmartDashboardParam("upperIntakeSpeed");

        lowerShooterSpeed = new SmartDashboardParam("lowerShooterSpeed");

        upperShooterSpeed = new SmartDashboardParam("upperShooterSpeed");

        upperShooterSpeedThreshold = new SmartDashboardParam("upperShooterSpeedThreshold");

        this.ball = ball;
        addRequirements(ball);
        intakeRetractedTime = 0;
        bottomMotorIntakeSpeed = 0.8;
    }

    public void execute() {
        //pull values from dashboard
        double bottomMotorSpeed = 0;
        double upperMotorSpeed = 0;

        if  (System.currentTimeMillis() - intakeRetractedTime < 2000) {
            bottomMotorSpeed = bottomMotorIntakeSpeed;
        }

        if(motorOn){
            if (mode == ModeStates.INTAKING) {
                //bottomMotorSpeed = lowerIntakeSpeed.get();
                //upperMotorSpeed = upperIntakeSpeed.get();
                bottomMotorSpeed = bottomMotorIntakeSpeed;
                upperMotorSpeed = -0.1;
            
            } else if (mode == ModeStates.SHOOTING) {
                upperMotorSpeed = upperShooterSpeed.get();
            }
        }
        double upperMotorSpeedCurrent = ball.getSpeedUpperMotor();
        SmartDashboard.putNumber("upperMotorSpeedCurrent", upperMotorSpeedCurrent);
        SmartDashboard.putNumber("upperMotorSpeedTarget", upperMotorSpeed);
        SmartDashboard.putNumber("upperMotorSpeedError", upperMotorSpeed - upperMotorSpeedCurrent);
        
        ball.setSpeed(bottomMotorSpeed, upperMotorSpeed);

    }
    // 4300 RPM, 8.5 feet
    public void startShooter() {
        mode = ModeStates.SHOOTING;
        motorOn = true;
    }
    public void startIntake() {
        mode = ModeStates.INTAKING;
        motorOn = true;
        ball.extend();
    }

    //turn off motors
    public void motorOff() {
        motorOn = false;
        ball.retract();
    }

    public void stopIntake() {
        motorOn = false;
        ball.retract();
        intakeRetractedTime = System.currentTimeMillis();
    }


    @Override
    public boolean isFinished() {
        return false;
    }
}
