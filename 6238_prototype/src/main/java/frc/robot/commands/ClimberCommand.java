package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClimberSubsystem;

public class ClimberCommand extends CommandBase {
    private final ClimberSubsystem climberSubsystem;
    private double translationSpeed;

    public ClimberCommand(ClimberSubsystem climber) {
        translationSpeed = 0.0;
        this.climberSubsystem = climber;
        addRequirements(climberSubsystem);
    }

    public void execute() {
        climberSubsystem.setTranslation(translationSpeed);
    }

    public void setTranslationSpeed(double speed) {
        translationSpeed = speed;
    }

    public void rotateForward() {
        climberSubsystem.rotateForward();
    }

    public void rotateBackward() {
        climberSubsystem.rotateBackward();
    }
    
    @Override
    public boolean isFinished() {
        return false;
    }

}




