package frc.robot.commands;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClimberSubsystem;

public class ClimberCommand extends CommandBase {
    private final ClimberSubsystem climberSubsystem;

    private final DigitalInput translationTopLimit = new DigitalInput(2);
    private final DigitalInput translationBottomLimit = new DigitalInput(0);
    private final DigitalInput rotationalTopLimit = new DigitalInput(3);
    private final DigitalInput rotationalBottomLimit = new DigitalInput(1);

    private double rotationMaxSpeed;
    private double translationMaxSpeed;
    private double translationSpeed;
    private double rotationalSpeed;


    public ClimberCommand(ClimberSubsystem climber) {
        rotationMaxSpeed = 1;
        translationMaxSpeed = 1;
        translationSpeed = 1.0;
        rotationalSpeed = 0;
        this.climberSubsystem = climber;
        addRequirements(climberSubsystem);
    }

    public void execute() {
        

        if (translationSpeed <0 && translationTopLimit.get()) {
            climberSubsystem.setTranslation(0);
        } else if (translationSpeed >0 && translationBottomLimit.get()) {
            climberSubsystem.setTranslation (0);
        } else {
            climberSubsystem.setTranslation(translationSpeed * translationMaxSpeed);
        }  


        if (rotationalSpeed <0 && rotationalTopLimit.get()) {
           climberSubsystem.setRotation(0);
       } else if (rotationalSpeed >0 && rotationalBottomLimit.get()) {
            climberSubsystem.setRotation (0);
        } else {
            climberSubsystem.setRotation(rotationalSpeed * rotationMaxSpeed);
        }  
    }   

    public void setTranslationSpeed(double speed) {
        translationSpeed = speed;
    }

    public void toggleTranslationalSpeed() {
        if (translationSpeed == 0) {
            translationSpeed = 1;
        }
        translationSpeed = - translationSpeed;
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




