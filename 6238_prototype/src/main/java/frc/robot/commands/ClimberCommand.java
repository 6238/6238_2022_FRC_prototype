package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClimberSubsystem;

public class ClimberCommand extends CommandBase {
    private final ClimberSubsystem climberSubsystem;
    private double translationSpeed;
    private boolean autoExtendEnabled;

    public ClimberCommand(ClimberSubsystem climber) {
        translationSpeed = 0.0;
        autoExtendEnabled = false;
        this.climberSubsystem = climber;
        addRequirements(climberSubsystem);

    }

    public void execute() {
        if (autoExtendEnabled){
            climberSubsystem.setTranslation(1);
            if (climberSubsystem.getTopLimitSwitch()) {
                climberSubsystem.rotateForward();
            } else {
                climberSubsystem.rotateBackward();
            }

        } else {
            climberSubsystem.setTranslation(translationSpeed);
        }
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

    public void stopAutoExtend() {
        autoExtendEnabled = false;
    }

    public void startAutoExtend() {
        autoExtendEnabled = true;
    }


}




