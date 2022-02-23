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
    private double rotationalSpeed;
    private boolean enableLimitSwitch;

    public ClimberCommand(ClimberSubsystem climber) {
        rotationMaxSpeed = 0;
        translationMaxSpeed = .5;
        translationUserSpeed = 0;
        rotationalSpeed = 0;
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
       
        climberSubsystem.setRotation(rotationalSpeed * rotationMaxSpeed);
    }  

    public void setTranslationSpeed(double speed) {
        translationUserSpeed = speed;
    }

    public void toggleTranslationalSpeed() {
        if (translationUserSpeed == 0) {
            translationUserSpeed = 1;
        }
        translationUserSpeed = - translationUserSpeed;
    }

    public void toggleRotationalSpeed() {
        rotationalSpeed = - rotationalSpeed;
    }

    public void setMaxRotationalSpeed(double speed) {
        rotationMaxSpeed = speed;
    }

    public void setRotationalSpeed(double speed) {
        rotationalSpeed = speed;
    }

    @Override
    public boolean isFinished() {
        return false;
        
    }

}




