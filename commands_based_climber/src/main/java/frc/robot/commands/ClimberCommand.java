package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClimberSubsystem;

public class ClimberCommand extends CommandBase {
    private final ClimberSubsystem climberSubsystem;
    private final Joystick joystick;
    
    private double rotationMaxSpeed;
    private double translationSpeed;

    public ClimberCommand(ClimberSubsystem climber, Joystick joystick) {
        rotationMaxSpeed = 0.0;
        translationSpeed = 0.0;
        this.joystick = joystick;
        this.climberSubsystem = climber;

        addRequirements(climberSubsystem);
    }

    public void execute() {
        climberSubsystem.setRotation(joystick.getY() * rotationMaxSpeed);
        climberSubsystem.setTranslation(translationSpeed);
    }

    public void setTranslationSpeed(double speed) {
        translationSpeed = speed;
    }

    public void setMaxRotationalSpeed(double speed) {
        rotationMaxSpeed = speed;
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
