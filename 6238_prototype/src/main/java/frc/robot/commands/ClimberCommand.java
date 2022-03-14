package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.SmartDashboardParam;
import frc.robot.subsystems.ClimberSubsystem;

public class ClimberCommand extends CommandBase {
    private final ClimberSubsystem climberSubsystem;
    private double translationSpeed;
    private boolean autoExtendEnabled;
    private final SmartDashboardParam slewRate;

    public ClimberCommand(ClimberSubsystem climber) {
        translationSpeed = 0.0;
        autoExtendEnabled = false;
        this.climberSubsystem = climber;
        addRequirements(climberSubsystem);

        slewRate = new SmartDashboardParam("slewRate");
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
        climberSubsystem.setSlewRate(slewRate.get());
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




