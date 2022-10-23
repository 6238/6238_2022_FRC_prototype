package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.SmartDashboardParam;
import frc.robot.subsystems.DriveSubsystem;

public class RotateCommand extends PIDCommand {
    private final double target;
    private final DriveSubsystem driveSubsystem;
    private final int waitTime = 5000;
    private long startTime;

    SmartDashboardParam kPSlider = new SmartDashboardParam("kPAutonomousDrive", 0.03);
    SmartDashboardParam kISlider = new SmartDashboardParam("kIAutonomousDrive", 0);
    SmartDashboardParam kDSlider = new SmartDashboardParam("kDAutonomousDrive", 0.0015);

    static private final double kTurnRateToleranceDegPerS = 2.0;
    static private final double kTurnToleranceDeg = 2;

    public RotateCommand(double targetAngleDegrees, DriveSubsystem driveSubsystem) {
        super(
            new PIDController(0, 0, 0),
            driveSubsystem::getAngle,
            targetAngleDegrees,
            driveSubsystem::setPIDRotateOutput
            ); 

        target = targetAngleDegrees;
        this.driveSubsystem = driveSubsystem;
        getController().enableContinuousInput(-180, 180);

        // kTurnRateToleranceDegPerS ensures the robot is stationary at the
        // setpoint before it is considered as having reached the reference
        getController()
            .setTolerance(kTurnToleranceDeg, kTurnRateToleranceDegPerS);
        
        addRequirements(driveSubsystem);
    }

    @Override
    public void initialize() {
        startTime = System.currentTimeMillis();
        driveSubsystem.zeroGyroAngle();
    }

    
    @Override
    public void execute() {
        //runs the pid commnand
        super.execute();


        //change different pid if smart dashboard changes
        if (kPSlider.get() != getController().getP()) {
            getController().setP(kPSlider.get());
        }
        if (kISlider.get() != getController().getI()) {
            getController().setI(kISlider.get());
        }
        if (kDSlider.get() != getController().getD()) {
            getController().setD(kDSlider.get());
        }
        //log of how far we are
        SmartDashboard.putNumber("PID Controller Rotation Error: ", m_controller.getPositionError());
        System.out.println("PID Controller Rotation Error: " + m_controller.getPositionError());
        SmartDashboard.putNumber("Gyro Rotation Error: ", driveSubsystem.getAngle() - target);
        System.out.println("Gyro Rotation Error: " + (driveSubsystem.getAngle() - target));  
    }
    
    @Override
    public boolean isFinished() {
        //debugging
        if(getController().atSetpoint()) System.out.println("At Setpoint");
        // if( System.currentTimeMillis() - startTime > waitTime) System.out.print("Time Reached");
        //actually returning
        return getController().atSetpoint();  // || System.currentTimeMillis() - startTime > waitTime;
    }
}
