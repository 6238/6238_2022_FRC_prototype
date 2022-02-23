// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.commands.ClimberCommand;
import frc.robot.subsystems.ClimberSubsystem;
import frc.robot.commands.DriveCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.commands.BallManualCommand;
import frc.robot.subsystems.BallSubsystem;
import frc.robot.subsystems.CameraSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class RobotContainer {
  private ClimberSubsystem climberSubsystem;
  private ClimberCommand climberCommand;
  private DriveSubsystem driveSubsystem;
  private DriveCommand driveCommand;
  private BallSubsystem ballSubsystem;
  private BallManualCommand ballManualCommand;
  private Joystick joystick;
  private CameraSubsystem cameraSubsystem;

  private void addClimber() {
    joystick = new Joystick(IOConstants.JOYSTICK_PORT);
    climberSubsystem = new ClimberSubsystem();
    climberCommand = new ClimberCommand(climberSubsystem);
    climberSubsystem.setDefaultCommand(climberCommand);

    new JoystickButton(joystick, IOConstants.TRANSLATE_UP)
      .whenPressed(() -> climberCommand.setTranslationSpeed(0.2))
      .whenReleased(() -> climberCommand.setTranslationSpeed(0.0));

    new JoystickButton(joystick, IOConstants.TRANSLATE_DOWN)
      .whenPressed(() -> climberCommand.setTranslationSpeed(-0.2))
      .whenReleased(() -> climberCommand.setTranslationSpeed(0.0));

    new JoystickButton(joystick, IOConstants.ROTATE_UP)
      .whenPressed(() -> climberCommand.setRotationalSpeed(0.2))
      .whenReleased(() -> climberCommand.setRotationalSpeed(0.0));

    new JoystickButton(joystick, IOConstants.ROTATE_DOWN)
      .whenPressed(() -> climberCommand.setRotationalSpeed(-0.2))
      .whenReleased(() -> climberCommand.setRotationalSpeed(0.0));

    new JoystickButton(joystick, IOConstants.TRANSLATE_TOGGLE)
      .whenPressed(() -> climberCommand.toggleTranslationalSpeed());

  }

  private void addDrive() {
    driveSubsystem = new DriveSubsystem();
    driveCommand = new DriveCommand(driveSubsystem, joystick);
    driveSubsystem.setDefaultCommand(driveCommand);

  }

  private void addBall() {
    ballSubsystem = new BallSubsystem();
    ballManualCommand = new BallManualCommand(ballSubsystem);
    ballSubsystem.setDefaultCommand(ballManualCommand);

    new JoystickButton(joystick, IOConstants.EXTEND_INTAKE)
      .whenPressed(() -> ballManualCommand.setExtendSpeed(1.0));

    new JoystickButton(joystick, IOConstants.RETRACT_INTAKE)
      .whenPressed(() -> ballManualCommand.setExtendSpeed(-1.0));

    new JoystickButton(joystick, IOConstants.START_INTAKE)
      .whenPressed(() -> ballManualCommand.startIntake())
      .whenReleased(() -> ballManualCommand.stopIntake());
  }

  private void addCamera() {  
    cameraSubsystem = new CameraSubsystem();
  }

  public RobotContainer() {
    addClimber();
    addDrive();
   // addBall();
    addCamera();

  }

  public Command getAutonomousCommand() {
    return null;
  }
}
