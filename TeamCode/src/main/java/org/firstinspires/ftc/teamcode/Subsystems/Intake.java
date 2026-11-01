package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;

public class Intake {
    private static DcMotorEx m_Intake;
    public Intake(DcMotorEx intakeMotor)
    {
        m_Intake = intakeMotor;
    }
    public static void runIntake()
    {
        m_Intake.setPower(1);    }
    public static void stopIntake()
    {
        m_Intake.setPower(0);
    }
}
