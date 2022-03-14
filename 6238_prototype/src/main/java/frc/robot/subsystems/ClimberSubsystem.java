package frc.robot.subsystems;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import frc.robot.Constants;

public class ClimberSubsystem extends SubsystemBase {
    private final CANSparkMax translationalControllerLeft;
    private final CANSparkMax translationalControllerRight;
    private final DigitalInput translationTopLimit;
    private final DigitalInput translationBottomLimit;
    private final DoubleSolenoid doubleSolenoid;
    private double translationalSpeed;
    SlewRateLimiter filter;

    double rate;
    
    public ClimberSubsystem() {
        translationalSpeed = 0.0;
        translationTopLimit = new DigitalInput(1);
        translationBottomLimit = new DigitalInput(0);
        doubleSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 3, 2);
        translationalControllerLeft = new CANSparkMax(Constants.CLIMBER_TRANSLATION_ID_LEFT, MotorType.kBrushless);
        translationalControllerRight = new CANSparkMax(Constants.CLIMBER_TRANSLATION_ID_RIGHT, MotorType.kBrushless);
        rate = 8;
        filter = new SlewRateLimiter(rate);
    }

    public void rotateForward() {
        doubleSolenoid.set(DoubleSolenoid.Value.kReverse);
    }

    public void rotateBackward () {
          doubleSolenoid.set(DoubleSolenoid.Value.kForward);
    }

    public void setTranslation(double speed) {
        translationalSpeed = speed;
    }

   public void setSlewRate(double rate) {
        double limitedRate = Math.max(rate, 2);
        if (limitedRate != rate) {
            System.out.println("ClimberSubsystem slew rate limited actual:" + limitedRate + " requested:" +rate);
        }
        if (this.rate != limitedRate) {
            this.rate = limitedRate;
            filter = new SlewRateLimiter(this.rate);
        }
    }

    @Override
    public void periodic() {
        double speed = translationalSpeed;
        speed = filter.calculate(speed);
        if (translationTopLimit.get()) {
            speed = Math.min(speed, 0);
        } else if (translationBottomLimit.get()) {
            speed = Math.max(speed, 0);
        }
        translationalControllerLeft.set(-speed);
        translationalControllerRight.set(speed);
    }

	public boolean getTopLimitSwitch() {
        return translationTopLimit.get();
	}
}
