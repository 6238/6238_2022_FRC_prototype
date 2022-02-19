
package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.BallSubsystem;

public class BallManualCommand extends CommandBase {
    private final BallSubsystem ball;
    private final Joystick joystick;
    private double lowerSpeedUser;
    private double upperSpeedUser;
    private final DigitalInput frontLimitSwitch = new DigitalInput(2);
    private final DigitalInput backLimitSwitch = new DigitalInput(3);

    public BallManualCommand(BallSubsystem ball, Joystick joystick) {
        lowerSpeedUser = SmartDashboard.getNumber("lowerIntakeSpeed", lowerSpeedUser);
        SmartDashboard.putNumber("lowerIntakeSpeed", lowerSpeedUser);

        upperSpeedUser = SmartDashboard.getNumber("upperIntakeSpeed", upperSpeedUser);
        SmartDashboard.putNumber("upperIntakeSpeed", upperSpeedUser);
        
        this.joystick = joystick;
        this.ball = ball;

        addRequirements(ball);
    }

    public void execute() {
        double extendSpeed = 0;
        double upperSpeedMotor = 0;
        double lowerSpeedMotor = 0;

        lowerSpeedUser = SmartDashboard.getNumber("lowerIntakeSpeed", lowerSpeedUser);
        upperSpeedUser = SmartDashboard.getNumber("upperIntakeSpeed", upperSpeedUser);

        if (joystick.getRawButton(6) && !frontLimitSwitch.get()) {
            extendSpeed = 1;
        } else if (joystick.getRawButton(5) && !backLimitSwitch.get()) {
            extendSpeed = -1;
        }
        
        if (joystick.getRawButton(3)) {
            upperSpeedMotor = upperSpeedUser;
        }

        if (joystick.getRawButton(4)) {
            lowerSpeedMotor = lowerSpeedUser;
        }

        ball.setSpeed(extendSpeed, upperSpeedMotor, lowerSpeedMotor);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
