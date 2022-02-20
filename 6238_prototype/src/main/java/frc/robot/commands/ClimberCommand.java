package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClimberSubsystem;

public class ClimberCommand extends CommandBase {
    private final ClimberSubsystem climberSubsystem;

    private double rotationalSpeed;
    private double translationSpeed;

    public ClimberCommand(ClimberSubsystem climber) {
        rotationalSpeed = 0.0;
        translationSpeed = 0.0;
        this.climberSubsystem = climber;
        addRequirements(climberSubsystem);
    }

    public void execute() {
        climberSubsystem.setRotation(rotationalSpeed);
        climberSubsystem.setTranslation(translationSpeed);
    }

    public void setTranslationSpeed(double speed) {
        translationSpeed = speed;
    }

    public void setRotationalSpeed(double speed) {
        rotationalSpeed = speed;
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
