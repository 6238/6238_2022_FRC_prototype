package frc.robot.commands;


import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.IOConstants;
import frc.robot.subsystems.BallSubsystem;

public class ShooterCommand extends CommandBase{
    public static enum PneumaticKickers {LEFT_KICKER, RIGHT_KICKER, BOTH};

    private final PneumaticKickers kicker;
    private final BallSubsystem ball; 
    protected double upperShooterRPMTarget;
    private long timerStart;
    private final long timeLimit;
    private final double rpmDiffTolerance;
    private long countdownStart;


    private Joystick joystick;
    enum OverridePreviousKickerEnum {LEFT, RIGHT};
    OverridePreviousKickerEnum overridePreviousKicker;


    private void printStuff(String location){
  /* dont run debugging in comp
       String s = "";
        s += "Location: " + location + ", ";
        s += "TimerStart: " + timerStart + ", ";
        s += "CuurentTime: " + System.currentTimeMillis() + ", ";
        s += "CuurentTimeDiff: " + (timerStart - System.currentTimeMillis()) + ", ";
        s += "rpmDiff: " + (ball.getRPMUpperMotor() - upperShooterRPMTarget) + ", ";
        s += "case: " + kicker + ", ";
        s += "CountStart: " + countdownStart + ", ";
        s += "Upper: " + ball.getRPMUpperMotor() + ", ";
        System.out.println(s);
        //print s
        */
    }


    public ShooterCommand(BallSubsystem ball, PneumaticKickers kicker, double rpmTarget) {
        overridePreviousKicker = OverridePreviousKickerEnum.RIGHT;
        this.ball = ball;
        upperShooterRPMTarget = rpmTarget;
        timeLimit = 300;
        rpmDiffTolerance = 100;
        this.kicker = kicker;
        this.joystick = new Joystick(IOConstants.JOYSTICK_PORT);
        addRequirements(ball);
    }

    private void restartTimer() {
        timerStart = System.currentTimeMillis();

    }
    @Override
    public void initialize(){
        timerStart = System.currentTimeMillis();
        countdownStart = Long.MAX_VALUE;
        printStuff("Init");
    } 
    @Override
    public void execute() {
        printStuff("Execute");
        if (Math.abs(ball.getRPMUpperMotor() - upperShooterRPMTarget) > rpmDiffTolerance && !joystick.getRawButton(IOConstants.OVERRIDE_SHOOTER_SENSOR)) {

            restartTimer();
        }

        if (System.currentTimeMillis() - timerStart > timeLimit) {

            printStuff("Shoot");

            switch (kicker) {
            case LEFT_KICKER:
                ball.activateLeftKicker(true);
                break;
            case RIGHT_KICKER:
                ball.activateRightKicker(true);
                break;
            case BOTH:
                ball.activateLeftKicker(true);
                ball.activateRightKicker(true);
                break;
            }

            if (countdownStart == Long.MAX_VALUE) {
                countdownStart = System.currentTimeMillis();
            }
        }

        ball.setSpeed(0, upperShooterRPMTarget); 
    }

    @Override
    public boolean isFinished() {
        // printStuff("Finished");
        return System.currentTimeMillis() - countdownStart > 350;
    }
}
