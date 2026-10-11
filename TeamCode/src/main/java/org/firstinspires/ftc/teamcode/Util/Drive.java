package org.firstinspires.ftc.teamcode.Util;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
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

    public static void driveInit(DcMotor frontLeft, DcMotor frontRight, DcMotor backLeft, DcMotor backRight)
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
        m_BLDrive.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public static void setHeading(double newHeading)
    {
        heading = newHeading;
    }

    public static boolean driveMode(double time)
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
}
