
package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.BallSubsystem;

public class BallManualCommand extends CommandBase {
    private final BallSubsystem ball;
    private double lowerUserSpeed;
    private double upperUserSpeed;
    private double lowerSpeed;
    private double upperSpeed;
    private double extendSpeed;

    public BallManualCommand(BallSubsystem ball) {
        lowerSpeed = 0;
        upperSpeed = 0;
        extendSpeed = 0;

        SmartDashboard.putNumber("lowerIntakeSpeed", lowerUserSpeed);
        lowerUserSpeed = SmartDashboard.getNumber("lowerIntakeSpeed", lowerUserSpeed);

        SmartDashboard.putNumber("upperIntakeSpeed", upperUserSpeed);
        upperUserSpeed = SmartDashboard.getNumber("upperIntakeSpeed", upperUserSpeed);

        this.ball = ball;
        addRequirements(ball);
    }

    public void execute() {
        lowerUserSpeed = SmartDashboard.getNumber("lowerIntakeSpeed", lowerUserSpeed);
        upperUserSpeed = SmartDashboard.getNumber("upperIntakeSpeed", upperUserSpeed);
        ball.setSpeed(extendSpeed, upperSpeed, lowerSpeed);
    }

    public void startIntake() {
        lowerSpeed = lowerUserSpeed;
        upperSpeed = upperUserSpeed;
    }

    public void stopIntake() {
        lowerSpeed = 0;
        upperSpeed = 0;
    }

    public void setExtendSpeed(double speed) {

    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
