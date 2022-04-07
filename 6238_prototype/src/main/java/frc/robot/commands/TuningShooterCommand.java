package frc.robot.commands;

import frc.robot.SmartDashboardParam;
import frc.robot.subsystems.BallSubsystem;

public class TuningShooterCommand extends ShooterCommand {
    SmartDashboardParam upperShooterRPMTargetSlider;
    public TuningShooterCommand(BallSubsystem ball, PneumaticKickers kicker) {
        super(ball, kicker, 0);
        upperShooterRPMTargetSlider = new SmartDashboardParam("upperShooterRPMTarget", 4600);
    }

    @Override
    public void execute() {
        upperShooterRPMTarget = upperShooterRPMTargetSlider.get();
        super.execute();
    }
}
