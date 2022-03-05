
package frc.robot.commands;

import java.util.Enumeration;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.BallSubsystem;

public class BallManualCommand extends CommandBase {
    private final BallSubsystem ball;
    private enum ModeStates {SHOOTING, INTAKING};
    private ModeStates mode;
    private boolean motorOn;
 
    public BallManualCommand(BallSubsystem ball) {
        //swtiches between referencing shooter or intake power value
        mode = ModeStates.SHOOTING;
        motorOn = false;

        //makes the dashboard
        double lowerUI = SmartDashboard.getNumber("lowerIntakeSpeed", 0);
        SmartDashboard.putNumber("lowerIntakeSpeed", lowerUI);

        double upperUI = SmartDashboard.getNumber("upperIntakeSpeed", 0);
        SmartDashboard.putNumber("upperIntakeSpeed", upperUI);

        double lowerUI2 = SmartDashboard.getNumber("lowerShooterSpeed", 0);
        SmartDashboard.putNumber("lowerShooterSpeed", lowerUI2);

        double upperUI2 = SmartDashboard.getNumber("upperShooterSpeed", 0);
        SmartDashboard.putNumber("upperShooterSpeed", upperUI2);

        this.ball = ball;
        addRequirements(ball);
    }

    public void execute() {
        //pull values from dashboard
        double bottomMotorSpeed = 0;
        double upperMotorSpeed = 0;
        if(motorOn){
            if (mode == ModeStates.INTAKING) {
                bottomMotorSpeed = SmartDashboard.getNumber("lowerIntakeSpeed", 0);
                upperMotorSpeed = SmartDashboard.getNumber("upperIntakeSpeed", 0);
            } else if (mode == ModeStates.SHOOTING) {
                bottomMotorSpeed = SmartDashboard.getNumber("lowerUserShooterSpeed", 0);
                upperMotorSpeed = SmartDashboard.getNumber("upperUserShooterSpeed", 0);
            }
        }
        ball.setSpeed(bottomMotorSpeed, upperMotorSpeed);
    }

    public void startShooter() {
        mode = ModeStates.SHOOTING;
        motorOn = true;
    }
    public void startIntake() {
        mode = ModeStates.INTAKING;
        motorOn = true;
    }

    //turn off motors
    public void motorOff() {
        motorOn = false;
    }


    @Override
    public boolean isFinished() {
        return false;
    }
}
