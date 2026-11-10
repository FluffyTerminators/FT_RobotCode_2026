package org.firstinspires.ftc.teamcode.Subsystems;

import org.firstinspires.ftc.teamcode.Util.UtilFunctions;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Util.Constants;

public class Drive
{
    private DcMotor m_FLDrive;
    private DcMotor m_FRDrive;
    private DcMotor m_BLDrive;
    private DcMotor m_BRDrive;
    private double forward = 0;
    private double strafe = 0;
    private double turn = 0;
    private double heading = 0;
    private double driveModeTimer = 0;
    private boolean fieldCentricMode = true;
    private boolean brakeMode = false;
    private double FLAdjust = 0;
    private double BLAdjust = 0;
    private double FRAdjust = 0;
    private double BRAdjust = 0;
    private int FLEncoderLast = 0;
    private int BLEncoderLast = 0;
    private int FREncoderLast = 0;
    private int BREncoderLast = 0;
    private boolean activeBrake = true;

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

    public void setHeading(double newHeading)
    {
        heading = newHeading;
    }

    public boolean isFieldCentricMode(double time)
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

    public void setBrakeMode(boolean mode)
        {
            brakeMode = mode;
        }

    public void driveControl(float forwardInput, float strafeInput, float turnInput)
    {

        double frontLeftPower = 0;
        double backLeftPower = 0;
        double frontRightPower = 0;
        double backRightPower = 0;
        double rawForward = forwardInput;
        double rawStrafe = strafeInput;
        turn = turnInput;

        rawForward = UtilFunctions.deadZone(rawForward);
        rawStrafe = UtilFunctions.deadZone(rawStrafe);
        turn = UtilFunctions.deadZone(turn);

        if ((rawForward == 0) && (rawStrafe == 0) && (turn == 0) && activeBrake)
        {
            int BLEncoder = m_BLDrive.getCurrentPosition();
            int FLEncoder = m_FLDrive.getCurrentPosition();
            int BREncoder = m_BRDrive.getCurrentPosition();
            int FREncoder = m_FRDrive.getCurrentPosition();

            if (BLEncoderLast == 0){
                BLAdjust = 0;
            }
            else {
                if (BLEncoder < BLEncoderLast) {
                    BLAdjust += 0.005;
                }
                else if (BLEncoder > BLEncoderLast) {
                    BLAdjust -= 0.005;
                }
                backLeftPower = BLAdjust;
            }

            BLEncoderLast = BLEncoder;
            FLEncoderLast = FLEncoder;
            BREncoderLast = BREncoder;
            FREncoderLast = FREncoder;
        }
        else
        {
            BLEncoderLast = 0;
            FLEncoderLast = 0;
            BREncoderLast = 0;
            FREncoderLast = 0;
            if (fieldCentricMode) {
                double sinHeading = Math.sin(heading);
                double cosHeading = Math.cos(heading);

                strafe = rawStrafe * cosHeading + rawForward * sinHeading;
                forward = rawForward * cosHeading - rawStrafe * sinHeading;
            } else {
                forward = rawForward;
                strafe = rawStrafe;
            }

            if (brakeMode) {
                forward *= Constants.Drive.brake;
                strafe *= Constants.Drive.brake;
                turn *= Constants.Drive.brake;
            }

            double denominator = Math.max(Math.abs(forward) + Math.abs(strafe) + Math.abs(turn), 1);
            frontLeftPower = (forward + strafe + turn) / denominator;
            backLeftPower = (forward - strafe + turn) / denominator;
            frontRightPower = (forward - strafe - turn) / denominator;
            backRightPower = (forward + strafe - turn) / denominator;

        }

        m_FLDrive.setPower(frontLeftPower);
        m_FRDrive.setPower(frontRightPower);
        m_BLDrive.setPower(backLeftPower);
        m_BRDrive.setPower(backRightPower);
    }
}



