package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.SmartDashboardParam;
import frc.robot.subsystems.DriveSubsystem;

public class DriveDistanceCommand extends PIDCommand{
    private final double target;
    private final DriveSubsystem driveSubsystem;

    SmartDashboardParam kPSlider = new SmartDashboardParam("kPAutonomousDrive", 0.3);
    SmartDashboardParam kISlider = new SmartDashboardParam("kIAutonomousDrive", 0);
    SmartDashboardParam kDSlider = new SmartDashboardParam("kDAutonomousDrive", 0);

    private double kP;
    private double kI;
    private double kD;

    private long startTime;

    static private final double kSpeedToleranceMetersPerS = 0.01;
    static private final double kDistanceToleranceMeters = 0.01;

    public DriveDistanceCommand(double targetDistanceMeters, DriveSubsystem driveSubsystem) {
        super(
            new PIDController(0, 0, 0),
            driveSubsystem::getPosition,
            targetDistanceMeters,
            driveSubsystem::setPIDDriveOutput,
            driveSubsystem
            );
        this.target = targetDistanceMeters;
        this.driveSubsystem = driveSubsystem;

        // kSppedToleranceMetersPerS ensures the robot is stationary at the
        // setpoint before it is considered as having reached the reference
        getController()
            .setTolerance(kDistanceToleranceMeters, kSpeedToleranceMetersPerS);

        addRequirements(driveSubsystem);
    }

    @Override
    public void initialize() {
        driveSubsystem.resetEncoders();
        startTime = System.currentTimeMillis();
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
        SmartDashboard.putNumber("Distance Error", driveSubsystem.getPosition() - target);
        System.out.println("Distance Error: " + m_controller.getPositionError());
    }

    @Override
    public boolean isFinished() {
        return getController().atSetpoint(); // || (System.currentTimeMillis() - startTime > 3000);
    }
}
