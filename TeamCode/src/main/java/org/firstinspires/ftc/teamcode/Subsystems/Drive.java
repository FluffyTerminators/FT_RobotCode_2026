package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Util.Constants;

public class Drive
{
    private static DcMotor m_FLDrive;
    private static DcMotor m_FRDrive;
    private static DcMotor m_BLDrive;
    private static DcMotor m_BRDrive;
    private static double forward = 0;
    private static double strafe = 0;
    private static double turn = 0;
    private static double heading = 0;
    private static double driveModeTimer = 0;
    private static boolean fieldCentricMode = true;
    private static boolean brakeMode = false;

    public Drive(DcMotor frontLeft, DcMotor frontRight, DcMotor backLeft, DcMotor backRight)
    {
        m_FLDrive = frontLeft;
        m_FRDrive = frontRight;
        m_BLDrive = backLeft;
        m_BRDrive = backRight;

        m_FLDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        m_FRDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        m_BLDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        m_BRDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        m_FLDrive.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public static void setHeading(double newHeading)
    {
        heading = newHeading;
    }

    public static boolean isFieldCentricMode(double time)
        {
            if (driveModeTimer == 0)
            {
                driveModeTimer = time;
            } else
            {
                if ((driveModeTimer > 0) && (time - driveModeTimer) > 0.5)
                {
                    fieldCentricMode = !fieldCentricMode;
                    driveModeTimer = -1;
                }
            }

            if (driveModeTimer == -1)
            {
                driveModeTimer = 0;
            }

            return fieldCentricMode;
        }

        public static void setBrakeMode(boolean mode)
        {
            brakeMode = mode;
        }

        public static void driveControl(float forwardInput, float strafeInput, float turnInput)
        {

            double rawForward = forwardInput;
            double rawStrafe = strafeInput;
            turn = turnInput;

            if (fieldCentricMode)
            {
                double sinHeading = Math.sin(heading);
                double cosHeading = Math.cos(heading);

                strafe = rawStrafe * cosHeading + rawForward * sinHeading;
                forward = rawForward * cosHeading - rawStrafe * sinHeading;
            } else {
                forward = -rawForward;
                strafe = rawStrafe;
            }

            if (brakeMode) {
                forward *= Constants.Drive.brake;
                strafe *= Constants.Drive.brake;
                turn *= Constants.Drive.brake;
            }

            double denominator = Math.max(Math.abs(forward) + Math.abs(strafe) + Math.abs(turn), 1);
            double frontLeftPower = (forward + strafe + turn) / denominator;
            double backLeftPower = (forward - strafe + turn) / denominator;
            double frontRightPower = (forward - strafe - turn) / denominator;
            double backRightPower = (forward + strafe - turn) / denominator;

            m_FLDrive.setPower(frontLeftPower);
            m_FRDrive.setPower(frontRightPower);
            m_BLDrive.setPower(backLeftPower);
            m_BRDrive.setPower(backRightPower);
        }
}



