package frc.robot.commands;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClimberSubsystem;

public class ClimberCommand extends CommandBase {
    private final ClimberSubsystem climberSubsystem;

    private final DigitalInput translationTopLimit = new DigitalInput(0);
    private final DigitalInput translationBottomLimit = new DigitalInput(1);
    private final DigitalInput rotationLimit = new DigitalInput(2);

    private double rotationMaxSpeed;
    private double translationMaxSpeed;
    private double translationUserSpeed;
    private double rotationUserSpeed;
    private boolean enableLimitSwitch;

    public ClimberCommand(ClimberSubsystem climber) {
        rotationMaxSpeed = 0.75;
        translationMaxSpeed = .75;
        translationUserSpeed = 0;
        rotationUserSpeed = 0;
        enableLimitSwitch = true;
        this.climberSubsystem = climber;
        addRequirements(climberSubsystem);
    }

    public void execute() {
        
        if (enableLimitSwitch && translationUserSpeed <0 && translationTopLimit.get()) {
            climberSubsystem.setTranslation(0);
        } else if (enableLimitSwitch && translationUserSpeed >0 && translationBottomLimit.get()) {
            climberSubsystem.setTranslation (0);
        } else { 
            climberSubsystem.setTranslation(translationUserSpeed * translationMaxSpeed);
        }  

        if (enableLimitSwitch && rotationUserSpeed <0 && rotationLimit.get()) {
            climberSubsystem.setRotation(0);
        } else if (enableLimitSwitch && rotationUserSpeed >0 && rotationLimit.get()) {
            climberSubsystem.setRotation(0);
        } else { 
            climberSubsystem.setRotation(rotationUserSpeed * rotationMaxSpeed);
        }
    }  

    public void setTranslationSpeed(double speed) {
        translationUserSpeed = speed;
    }
    public void setRotation(double speed) {
        rotationUserSpeed = speed;
    }
    public void toggleTranslationalSpeed() {
        if (translationUserSpeed == 0) {
            translationUserSpeed = 1;
        }
        translationUserSpeed = - translationUserSpeed;
    }

    public void toggleRotationalSpeed() {
        rotationUserSpeed = - rotationUserSpeed;
    }

    public void setMaxRotationalSpeed(double speed) {
        rotationMaxSpeed = speed;
    }

    public void setRotationalSpeed(double speed) {
        rotationUserSpeed = speed;
    }

    @Override
    public boolean isFinished() {
        return false;
        
    }

}




