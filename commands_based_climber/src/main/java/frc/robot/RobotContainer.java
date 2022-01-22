// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.commands.ClimberCommand;
import frc.robot.subsystems.ClimberSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class RobotContainer {
  private final ClimberSubsystem climberSubsystem;
  private final ClimberCommand climberCommand;
  private final Joystick joystick;

  public RobotContainer() {
    joystick = new Joystick(IOConstants.JOYSTICK_PORT);
    climberSubsystem = new ClimberSubsystem();
    climberCommand = new ClimberCommand(climberSubsystem, joystick);
    climberSubsystem.setDefaultCommand(climberCommand);
    configureButtonBindings();
  }

  private void configureButtonBindings() {
    new JoystickButton(joystick, IOConstants.TRANSLATE_UP)
      .whenPressed(() -> climberCommand.setTranslationSpeed(1.0))
      .whenReleased(() -> climberCommand.setTranslationSpeed(0.0));

    new JoystickButton(joystick, IOConstants.TRANSLATE_DOWN)
      .whenPressed(() -> climberCommand.setTranslationSpeed(-1.0))
      .whenReleased(() -> climberCommand.setTranslationSpeed(0.0)); 

  }

  public Command getAutonomousCommand() {
    return null;
  }
}
