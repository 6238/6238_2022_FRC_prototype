package frc.robot.commands;

import java.nio.file.DirectoryStream;

import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.Intake.ExtendIntakeCommand;
import frc.robot.commands.Intake.RetractIntakeCommand;
import frc.robot.commands.Intake.RunIntakeCommand;
import frc.robot.commands.ShooterCommand.PneumaticKickers;
import frc.robot.subsystems.BallSubsystem;
import frc.robot.subsystems.DriveSubsystem;

// 1. Drives Backwards
// 2. Shoots
// 3. Stops Shooter
// 4. rotates 180 degrees
// 5. Start intake
// 6. Extend Intake, Drives forward
// 7. Retract Intake
// 8. rotates 180 degrees
// 9. starts shooter
// 10. stops shooter

public class PrototypeAutonomousCommand extends SequentialCommandGroup {
    DriveSubsystem driveSubsystem;    
    public PrototypeAutonomousCommand(BallSubsystem ballSubsystem, DriveSubsystem driveSubsystem) {
        this.driveSubsystem = driveSubsystem;
        addCommands(
            // Drive Backwards
            new DriveDistanceCommand(-0.6, driveSubsystem),
            
            // Shoot first ball
            new ShooterCommand(ballSubsystem, PneumaticKickers.BOTH, 4600),
            new StopShooterCommand(ballSubsystem),

            // Rotate 180 degrees, extend intake
            new RotateCommand(180, driveSubsystem),
            new ExtendIntakeCommand(ballSubsystem),

            // drive forward to intake ball
            new ParallelRaceGroup(
                new RunIntakeCommand(ballSubsystem),
                new DriveDistanceCommand(2.0, driveSubsystem)
                ),
            new RetractIntakeCommand(ballSubsystem),

            // Rotate towards hub, drive to original position
            new RotateCommand(0, driveSubsystem),
            new DriveDistanceCommand(2.0, driveSubsystem),

            // Shoot both balls
            new ShooterCommand(ballSubsystem, PneumaticKickers.BOTH, 4600),
            new StopShooterCommand(ballSubsystem)


        );
    }

    @Override
    public void initialize() {
        driveSubsystem.zeroGyroAngle();
    }


}
