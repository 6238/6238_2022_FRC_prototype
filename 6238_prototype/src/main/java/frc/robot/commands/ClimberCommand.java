package frc.robot.commands;

<<<<<<< Updated upstream:6238_prototype/src/main/java/frc/robot/commands/ClimberCommand.java
=======
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
>>>>>>> Stashed changes:commands_based_climber/src/main/java/frc/robot/commands/ClimberCommand.java
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClimberSubsystem;

public class ClimberCommand extends CommandBase {
    private final ClimberSubsystem climberSubsystem;

<<<<<<< Updated upstream:6238_prototype/src/main/java/frc/robot/commands/ClimberCommand.java
    private double rotationalSpeed;
=======
    private final DigitalInput translationTopLimit = new DigitalInput(2);
    private final DigitalInput translationBottomLimit = new DigitalInput(0);
    private final DigitalInput rotationalTopLimit = new DigitalInput(3);
    private final DigitalInput rotationalBottomLimit = new DigitalInput(1);

    private double rotationMaxSpeed;
    private double translationMaxSpeed;
>>>>>>> Stashed changes:commands_based_climber/src/main/java/frc/robot/commands/ClimberCommand.java
    private double translationSpeed;
    private double rotationalSpeed;

<<<<<<< Updated upstream:6238_prototype/src/main/java/frc/robot/commands/ClimberCommand.java
    public ClimberCommand(ClimberSubsystem climber) {
        rotationalSpeed = 0.0;
        translationSpeed = 0.0;
=======

    public ClimberCommand(ClimberSubsystem climber) {
        rotationMaxSpeed = 1;
        translationMaxSpeed = 1;
        translationSpeed = 1.0;
        rotationalSpeed = 0;
>>>>>>> Stashed changes:commands_based_climber/src/main/java/frc/robot/commands/ClimberCommand.java
        this.climberSubsystem = climber;
        addRequirements(climberSubsystem);
    }

    public void execute() {
<<<<<<< Updated upstream:6238_prototype/src/main/java/frc/robot/commands/ClimberCommand.java
        climberSubsystem.setRotation(rotationalSpeed);
        climberSubsystem.setTranslation(translationSpeed);
    }
=======
        

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
>>>>>>> Stashed changes:commands_based_climber/src/main/java/frc/robot/commands/ClimberCommand.java

    public void setTranslationSpeed(double speed) {
        translationSpeed = speed;
    }
<<<<<<< Updated upstream:6238_prototype/src/main/java/frc/robot/commands/ClimberCommand.java

    public void setRotationalSpeed(double speed) {
        rotationalSpeed = speed;
=======
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
>>>>>>> Stashed changes:commands_based_climber/src/main/java/frc/robot/commands/ClimberCommand.java
    }

    public void setRotationalSpeed(double speed) {
        rotationalSpeed = speed;
    }

    @Override
    public boolean isFinished() {
        return false;
        
    }


    

}




