package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.SmartDashboardParam;
import frc.robot.subsystems.DriveSubsystem;

public class DriveDistanceCommand extends PIDCommand{
    private final double target;
    private final DriveSubsystem driveSubsystem;

    SmartDashboardParam kPSlider = new SmartDashboardParam("kPAutonomousDrive");
    SmartDashboardParam kISlider = new SmartDashboardParam("kIAutonomousDrive");
    SmartDashboardParam kDSlider = new SmartDashboardParam("kDAutonomousDrive");

    private double kP;
    private double kI;
    private double kD;

    static private final double kSpeedToleranceMetersPerS = 2.0;
    static private final double kDistanceToleranceMeters = 2.0;

    public DriveDistanceCommand(double targetDistanceMeters, DriveSubsystem driveSubsystem) {
        super(
            new PIDController(0, 0, 0),
            driveSubsystem::getPosition,
            targetDistanceMeters,
            output -> driveSubsystem.setDrive(0, output),
            driveSubsystem
            );
        this.target = targetDistanceMeters;
        this.driveSubsystem = driveSubsystem;
        driveSubsystem.resetEncoders();

        // kSppedToleranceMetersPerS ensures the robot is stationary at the
        // setpoint before it is considered as having reached the reference
        getController()
            .setTolerance(kDistanceToleranceMeters, kSpeedToleranceMetersPerS);
    }

    @Override
    public void execute() {
        if (kPSlider.get() != kP) {
            kP = kPSlider.get();
            getController().setP(kP);
        }
        if (kISlider.get() != kI) {
            kI = kISlider.get();
            getController().setI(kI);
        }
        if (kDSlider.get() != kD) {
            kD = kDSlider.get();
            getController().setD(kD);
        }
        SmartDashboard.putNumber("Distance Error", driveSubsystem.getPosition() - target);
    }

    @Override
    public boolean isFinished() {
        return getController().atSetpoint();
    }
}
