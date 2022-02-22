// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.commands.ClimberCommand;
import frc.robot.subsystems.ClimberSubsystem;
import frc.robot.commands.DriveCommand;
import frc.robot.subsystems.DriveSubsystem;
<<<<<<< Updated upstream:6238_prototype/src/main/java/frc/robot/RobotContainer.java
import frc.robot.commands.BallManualCommand;
import frc.robot.subsystems.BallSubsystem;
import frc.robot.subsystems.CameraSubsystem;
=======
//import frc.robot.commands.BallManualCommand;
//import frc.robot.subsystems.BallSubsystem;
>>>>>>> Stashed changes:commands_based_climber/src/main/java/frc/robot/RobotContainer.java
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class RobotContainer {
<<<<<<< Updated upstream:6238_prototype/src/main/java/frc/robot/RobotContainer.java
  private ClimberSubsystem climberSubsystem;
  private ClimberCommand climberCommand;
  private DriveSubsystem driveSubsystem;
  private DriveCommand driveCommand;
  private BallSubsystem ballSubsystem;
  private BallManualCommand ballManualCommand;
  private Joystick joystick;
=======
  private final ClimberSubsystem climberSubsystem;
  private final ClimberCommand climberCommand;
  private final DriveSubsystem driveSubsystem;
  private final DriveCommand driveCommand;
  //private final BallSubsystem ballSubsystem;
  //private final BallManualCommand ballManualCommand;
  private final Joystick joystick;
>>>>>>> Stashed changes:commands_based_climber/src/main/java/frc/robot/RobotContainer.java

  private void addClimber() {
    joystick = new Joystick(IOConstants.JOYSTICK_PORT);
    climberSubsystem = new ClimberSubsystem();
    climberCommand = new ClimberCommand(climberSubsystem);
    climberSubsystem.setDefaultCommand(climberCommand);

    new JoystickButton(joystick, IOConstants.TRANSLATE_UP)
      .whenPressed(() -> climberCommand.setTranslationSpeed(1.0))
      .whenReleased(() -> climberCommand.setTranslationSpeed(0.0));

    new JoystickButton(joystick, IOConstants.TRANSLATE_DOWN)
      .whenPressed(() -> climberCommand.setTranslationSpeed(-1.0))
      .whenReleased(() -> climberCommand.setTranslationSpeed(0.0)); 

    climberCommand.setRotationalSpeed(joystick.getY());
  }

  private void addDrive() {
    driveSubsystem = new DriveSubsystem();
    driveCommand = new DriveCommand(driveSubsystem);
    driveSubsystem.setDefaultCommand(driveCommand);

<<<<<<< Updated upstream:6238_prototype/src/main/java/frc/robot/RobotContainer.java
    driveCommand.setDrive(-joystick.getY(), joystick.getX());
  }

  private void addBall() {
    ballSubsystem = new BallSubsystem();
    ballManualCommand = new BallManualCommand(ballSubsystem);
    ballSubsystem.setDefaultCommand(ballManualCommand);
=======
    //ballSubsystem = new BallSubsystem();
    //ballManualCommand = new BallManualCommand(ballSubsystem, joystick);
    //ballSubsystem.setDefaultCommand(ballManualCommand);
>>>>>>> Stashed changes:commands_based_climber/src/main/java/frc/robot/RobotContainer.java

    new JoystickButton(joystick, IOConstants.EXTEND_INTAKE)
      .whenPressed(() -> ballManualCommand.setExtendSpeed(1.0));

<<<<<<< Updated upstream:6238_prototype/src/main/java/frc/robot/RobotContainer.java
    new JoystickButton(joystick, IOConstants.RETRACT_INTAKE)
      .whenPressed(() -> ballManualCommand.setExtendSpeed(-1.0));
=======
  private void configureButtonBindings() {
    new JoystickButton(joystick, IOConstants.TRANSLATE_UP)
      .whenPressed(() -> climberCommand.setTranslationSpeed(1.0))
      .whenReleased(() -> climberCommand.setTranslationSpeed(0.0));
    
      new JoystickButton(joystick, IOConstants.ROTATE_UP)
      .whenPressed(() -> climberCommand.setRotationalSpeed(1.0))
      .whenReleased(() -> climberCommand.setRotationalSpeed(0.0));

      
     new JoystickButton(joystick, IOConstants.ROTATE_DOWN)
      .whenPressed(() -> climberCommand.setRotationalSpeed(-1.0))
      .whenReleased(() -> climberCommand.setRotationalSpeed(0.0));
>>>>>>> Stashed changes:commands_based_climber/src/main/java/frc/robot/RobotContainer.java

    new JoystickButton(joystick, IOConstants.START_INTAKE)
      .whenPressed(() -> ballManualCommand.startIntake())
      .whenReleased(() -> ballManualCommand.stopIntake());
  }

<<<<<<< Updated upstream:6238_prototype/src/main/java/frc/robot/RobotContainer.java
  private void addCamera() {
    new CameraSubsystem();
  }

  public RobotContainer() {
    addClimber();
    addDrive();
    addBall();
    addCamera();
=======
      new JoystickButton(joystick, IOConstants.TRANSLATE_TOGGLE)
      .whenPressed(() -> climberCommand.toggleTranslationalSpeed());

      new JoystickButton(joystick, IOConstants.ROTATIONAL_TOGGLE)
      .whenPressed(() -> climberCommand.toggleTranslationalSpeed());

>>>>>>> Stashed changes:commands_based_climber/src/main/java/frc/robot/RobotContainer.java
  }

  public Command getAutonomousCommand() {
    return null;
  }
}
