
package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.BallSubsystem;

public class BallManualCommand extends CommandBase {
    private final BallSubsystem ball;
    private final Joystick joystick;

    private final DigitalInput frontLimitSwitch = new DigitalInput(2);
    private final DigitalInput backLimitSwitch = new DigitalInput(3);

    public BallManualCommand(BallSubsystem ball, Joystick joystick) {

        this.joystick = joystick;
        this.ball = ball;

        addRequirements(ball);
    }

    public void execute() {
        double extendSpeed = 0;
        double upperSpeed = 0;
        double lowerSpeed = 0;

        if (joystick.getRawButton(5) && !frontLimitSwitch.get()) {
            extendSpeed = 1;
        } else if (joystick.getRawButton(6) && !backLimitSwitch.get()) {
            extendSpeed = -1;
        }
        
        if (joystick.getRawButton(3)) {
            upperSpeed = 0.5;
        }

        if (joystick.getRawButton(4)) {
            lowerSpeed = 0.3;
        }

        ball.setSpeed(extendSpeed, upperSpeed, lowerSpeed);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
