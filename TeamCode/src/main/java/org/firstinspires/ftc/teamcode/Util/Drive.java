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
}
