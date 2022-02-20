
package frc.robot.subsystems;


import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.cameraserver.CameraServer;

public class CameraSubsystem extends SubsystemBase {
    public CameraSubsystem() {
        CameraServer.startAutomaticCapture();
    }
}