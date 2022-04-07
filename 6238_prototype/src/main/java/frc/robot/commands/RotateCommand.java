package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.SmartDashboardParam;
import frc.robot.subsystems.DriveSubsystem;

public class RotateCommand extends PIDCommand{
    private final double target;
    private final DriveSubsystem driveSubsystem;

    SmartDashboardParam kPSlider = new SmartDashboardParam("kPAutonomousDrive", 0);
    SmartDashboardParam kISlider = new SmartDashboardParam("kIAutonomousDrive", 0);
    SmartDashboardParam kDSlider = new SmartDashboardParam("kDAutonomousDrive", 0);

    private double kP;
    private double kI;
    private double kD;

    static private final double kTurnRateToleranceDegPerS = 2.0;
    static private final double kTurnToleranceDeg = 2.0;

    public RotateCommand(double targetAngleDegrees, DriveSubsystem driveSubsystem) {
        super(
            new PIDController(0, 0, 0),
            driveSubsystem::getAngle,
            targetAngleDegrees,
            output -> driveSubsystem.setDrive(0, output > 0.3 ? 0.3 : (output < -0.3 ? -0.3 : output)),
            driveSubsystem
            );
        
        target = targetAngleDegrees;
        this.driveSubsystem = driveSubsystem;

        getController().enableContinuousInput(-180, 180);

        // kTurnRateToleranceDegPerS ensures the robot is stationary at the
        // setpoint before it is considered as having reached the reference
        getController()
            .setTolerance(kTurnToleranceDeg, kTurnRateToleranceDegPerS);
        
        // addRequirements(driveSubsystem) is called in the parent constructor
    }

    @Override
    public void execute() {
        super.execute();
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
        SmartDashboard.putNumber("Rotate Error", driveSubsystem.getAngle() - target);
    }
    
    @Override
    public boolean isFinished() {
        return getController().atSetpoint();
    }
}
